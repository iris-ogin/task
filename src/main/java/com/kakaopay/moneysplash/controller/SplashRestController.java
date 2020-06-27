package com.kakaopay.moneysplash.controller;

import com.kakaopay.moneysplash.dto.SplashViewResultDto;
import com.kakaopay.moneysplash.dto.GainResultDto;
import com.kakaopay.moneysplash.entity.Gain;
import com.kakaopay.moneysplash.entity.RoomTalker;
import com.kakaopay.moneysplash.entity.Splash;
import com.kakaopay.moneysplash.service.UserService;
import com.kakaopay.moneysplash.dto.SplashResultDto;
import com.kakaopay.moneysplash.service.SplashService;
import com.kakaopay.moneysplash.support.SplashLogics;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping(value = "/v1/money-gun", produces = MediaType.APPLICATION_JSON_VALUE)
public class SplashRestController {

    @Autowired
    private SplashService splashService;

    @Autowired
    private UserService userService;

    @PostMapping("/splash")
    public SplashResultDto splash(@RequestHeader(value = "X-USER-ID", required = false) Long userId,
                                  @RequestHeader(value = "X-ROOM-ID", required = false) String roomUuid,
                                  @RequestParam(value = "amount", defaultValue = "0") long amount,
                                  @RequestParam(value = "gainerCount", defaultValue = "0") long gainerCount) {

        if (userId == null || userId < 1 || StringUtils.isBlank(roomUuid) || amount < 1 || gainerCount < 1) {
            return new SplashResultDto(false, "Request의 Header와 Parameter를 확인해주세요.");
        }

        RoomTalker splasher = userService.findOne(userId, roomUuid);

        if (splasher == null) {
            return new SplashResultDto(false, "잘못된 사용자 정보 입니다.");
        }
        Splash splash = new Splash();
        splash.setSplasher(splasher);
        splash.setAmount(amount);
        splash.setToken(SplashLogics.token());
        splash.setGainerCount(gainerCount);

        Splash splashed = splashService.createSplash(splash);
        if (splash.getId() < 1) {
            return new SplashResultDto(false, "시스템 오류 입니다.");
        }
        return new SplashResultDto(splashed.getToken());
    }

    @PostMapping("/gain")
    public GainResultDto gain(@RequestHeader(value = "X-USER-ID", required = false) Long userId,
                              @RequestHeader(value = "X-ROOM-ID", required = false) String roomUuid,
                              @RequestParam(value = "token", defaultValue = "") String token) {
        if (userId == null || userId < 1 || StringUtils.isAnyBlank(roomUuid, token)) {
            return new GainResultDto(false, "Request의 Header와 Parameter를 확인해주세요.");
        }

        RoomTalker gainer = userService.findOne(userId, roomUuid);

        if (gainer == null) {
            return new GainResultDto(false, "잘못된 사용자 정보 입니다.");
        }

        Splash splash = splashService.findOneByToken(token);

        if (splash == null) {
            return new GainResultDto(false, "잘못된 token 입니다.");
        }

        if (SplashLogics.isSplashExpired(splash.getCreateAt())) {
            return new GainResultDto(false, "만료된 뿌리기 입니다.");
        }

        if (splash.getSplasher().getRoom().getId() != gainer.getRoom().getId()) {
            return new GainResultDto(false, "동일한 방에 있는 사용자만 가능한 서비스 입니다.");
        }

        if (splash.getSplasher().getTalker().getId() == gainer.getTalker().getId()) {
            return new GainResultDto(false, "본인의 뿌리기는 받을 수 없습니다.");
        }

        Gain checkForGained = splashService.findMyGain(splash, gainer);
        if (checkForGained != null) {
            return new GainResultDto(false, "하나의 뿌리기에서 한번만 받을 수 있습니다.");
        }

        Gain picked = splashService.findGainPicked(splash, gainer);
        if (picked == null) {
            return new GainResultDto(false, "받을 수 없습니다.");
        }
        picked.setGainer(gainer);
        picked.setGainAt(new Date());
        picked.setOccupied(true);

        Gain gained = splashService.gain(picked);


        return new GainResultDto(gained.getGainAmount());
    }

    @GetMapping("/splash/view")
    public SplashViewResultDto view(@RequestHeader(value = "X-USER-ID", required = false) Long userId,
                                    @RequestHeader(value = "X-ROOM-ID", required = false) String roomUuid,
                                    @RequestParam(value = "token", defaultValue = "") String token) {
        if (userId == null || userId < 1 || StringUtils.isAnyBlank(roomUuid, token)) {
            return new SplashViewResultDto(false, "Request의 Header와 Parameter를 확인해주세요.");
        }

        RoomTalker splasher = userService.findOne(userId, roomUuid);

        if (splasher == null) {
            return new SplashViewResultDto(false, "잘못된 사용자 정보 입니다.");
        }

        Splash splash = splashService.findOneByToken(token, true);

        if (splash == null) {
            return new SplashViewResultDto(false, "잘못된 token 입니다.");
        }

        if (splash.getSplasher().getTalker().getId() != splasher.getTalker().getId()) {
            return new SplashViewResultDto(false, "본인만 조회 가능합니다.");
        }

        if (SplashLogics.isSplashViewExpired(splash.getCreateAt())) {
            return new SplashViewResultDto(false, "조회 기간 만료 되었습니다.");
        }

        return new SplashViewResultDto(splash.getCreateAt(), splash.getAmount(), splash.getGainedList());

    }

}
