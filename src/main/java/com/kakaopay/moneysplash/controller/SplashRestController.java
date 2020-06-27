package com.kakaopay.moneysplash.controller;

import com.kakaopay.moneysplash.support.Result;
import com.kakaopay.moneysplash.dto.SplashResultDto;
import com.kakaopay.moneysplash.service.SplashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/splash", produces = MediaType.APPLICATION_JSON_VALUE)
public class SplashRestController {

    @Autowired
    private SplashService splashService;

    @PostMapping("/")
    public SplashResultDto splash(@RequestParam("amount") long amount, @RequestParam("gainer") long gainer) {
        Result<SplashResultDto> result = splashService.send(amount, gainer);
        if (result.isError()) {
            // handle error
            return null;
        }

        return result.payload();
    }
}
