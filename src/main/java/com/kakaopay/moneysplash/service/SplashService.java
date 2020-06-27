package com.kakaopay.moneysplash.service;

import com.google.common.collect.Lists;
import com.kakaopay.moneysplash.dao.SplashDao;
import com.kakaopay.moneysplash.dao.UserDao;
import com.kakaopay.moneysplash.dto.SplashResultDto;
import com.kakaopay.moneysplash.entity.Gain;
import com.kakaopay.moneysplash.entity.RoomTalker;
import com.kakaopay.moneysplash.entity.Splash;
import com.kakaopay.moneysplash.support.Result;
import com.kakaopay.moneysplash.support.SplashLogics;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class SplashService {
    @Autowired
    private SplashDao splashDao;

    @Autowired
    private UserDao userDao;

    @Transactional
    public Splash findOneByToken(String token) {
        return findOneByToken(token, false);
    }

    @Transactional
    public Splash findOneByToken(String token, boolean info) {
        Splash splash = splashDao.selectOneByToken(token);
        if (splash != null && info) {
            splash.setGainedList(splashDao.findGainedList(splash));
        }
        return splash;
    }

    @Transactional
    public Splash createSplash(Splash splash) {
        List<Long> gainAmountList = SplashLogics.distributeMoney(splash.getAmount(), splash.getGainerCount());

        List<Gain> gainList = Lists.newArrayList();
        for (Long gainAmount : gainAmountList) {
            Gain gain = new Gain();
            gain.setSplash(splash);
            gain.setGainAmount(gainAmount);
            gain.setOccupied(false);
            gainList.add(gain);
        }
        splash.setGainList(gainList);

        return splashDao.persist(splash);
    }

    @Transactional
    public Gain findMyGain(Splash splash, RoomTalker gainer) {
        return splashDao.selectGainByTalker(splash, gainer);
    }

    @Transactional
    public Gain findGainPicked(Splash splash, RoomTalker gainer) {
        // 한개를 찾는다.
        List<Gain> gainList = splashDao.findOneGain(splash);
        if (gainList == null || gainList.size() < 1) {
            return null;
        }

        Gain gain = gainList.get(0);

        int aff = splashDao.updateLock(gain);
        if (aff != 1) {
            return null;
        }

        return gain;
    }

    @Transactional
    public Gain gain(Gain gain) {
        return splashDao.updateGain(gain);
    }
}
