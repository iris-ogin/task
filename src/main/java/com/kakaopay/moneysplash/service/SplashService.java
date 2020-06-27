package com.kakaopay.moneysplash.service;

import com.kakaopay.moneysplash.dao.SplashDao;
import com.kakaopay.moneysplash.dto.SplashResultDto;
import com.kakaopay.moneysplash.support.Result;
import com.kakaopay.moneysplash.support.SplashLogics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SplashService {
    @Autowired
    private SplashDao splashDao;

    @Transactional
    public Result<SplashResultDto> send(long amount, long gainerCount) {
        List<Long> gainAmountList = SplashLogics.distributeMoney(amount, gainerCount);

        return Result.success();
    }
}
