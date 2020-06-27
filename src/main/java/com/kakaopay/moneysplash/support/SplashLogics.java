package com.kakaopay.moneysplash.support;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.List;

public class SplashLogics {
    private SplashLogics() {
    }

    public static String token() {
        return RandomStringUtils.randomAlphabetic(20);
    }

    public static List<Long> distributeMoney(long amount, long gainerCount) {
        long rest = amount;
        long count = 0;
        long sum = 0;

        List<Long> amountList = Lists.newArrayList();
        while (count < gainerCount - 1) {
            long gainAmount = RandomUtils.nextLong(0, rest / 2);
            rest = rest - gainAmount;
            sum = sum + rest;
            amountList.add(gainAmount);
            count++;
        }

        //마지막 금액은 나머지
        amountList.add(rest);


        return amountList;
    }


    public static boolean isSplashExpired(Date createAt) {
        return DateUtils.addMinutes(createAt, 10).getTime() < (new Date()).getTime();
    }

    public static boolean isSplashViewExpired(Date createAt) {
        return DateUtils.addDays(createAt, 7).getTime() < (new Date()).getTime();
    }
}
