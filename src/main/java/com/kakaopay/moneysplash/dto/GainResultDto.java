package com.kakaopay.moneysplash.dto;

import java.io.Serializable;

public class GainResultDto {
    private final boolean success;
    private final String message;
    private long gainAmount;


    public GainResultDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public GainResultDto(long gainAmount) {
        this.success = true;
        this.message = "";
        this.gainAmount = gainAmount;
    }

    public long getGainAmount() {
        return gainAmount;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
