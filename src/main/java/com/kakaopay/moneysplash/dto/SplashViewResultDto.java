package com.kakaopay.moneysplash.dto;

import com.google.common.collect.Lists;
import com.kakaopay.moneysplash.entity.Gain;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SplashViewResultDto {
    private final boolean success;
    private final String message;
    private Date createAt;
    private long amount;
    private long gainSum;
    private List<GainInfo> gainInfoList;

    public SplashViewResultDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public SplashViewResultDto(Date createAt, long amount, List<Gain> gainedList) {
        this.success = true;
        this.message = "";
        this.createAt = createAt;
        this.amount = amount;
        this.gainSum = gainedList.stream().map(Gain::getGainAmount).mapToLong(Long::longValue).sum();
        this.gainInfoList = gainedList.stream().map(x -> new GainInfo(x.getGainer().getTalker().getId(), x.getGainAmount()))
                .collect(Collectors.toList());
    }


    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public long getAmount() {
        return amount;
    }

    public long getGainSum() {
        return gainSum;
    }

    public List<GainInfo> getGainInfoList() {
        return gainInfoList;
    }
}

class GainInfo {
    private final long userId;
    private final long gainAmount;

    public GainInfo(long userId, long gainAmount) {
        this.userId = userId;
        this.gainAmount = gainAmount;
    }

    public long getUserId() {
        return userId;
    }

    public long getGainAmount() {
        return gainAmount;
    }
}
