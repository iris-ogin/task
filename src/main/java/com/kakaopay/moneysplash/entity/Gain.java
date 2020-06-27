package com.kakaopay.moneysplash.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Gain {
    public Gain() {
    }

    public Gain(long gainAmount) {
        this.gainAmount = gainAmount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GAIN")
    @SequenceGenerator(name = "SEQ_GAIN", sequenceName = "gain_id_seq", allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name = "splash_id")
    private Splash splash;

    @ManyToOne
    @JoinColumn(name = "gainer")
    private User gainer;

    @Column(updatable = false)
    private long gainAmount;

    @Column(name = "gain_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gainAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Splash getSplash() {
        return splash;
    }

    public void setSplash(Splash splash) {
        this.splash = splash;
    }

    public User getGainer() {
        return gainer;
    }

    public void setGainer(User gainer) {
        this.gainer = gainer;
    }

    public long getGainAmount() {
        return gainAmount;
    }

    public void setGainAmount(long gainAmount) {
        this.gainAmount = gainAmount;
    }

    public Date getGainAt() {
        return gainAt;
    }

    public void setGainAt(Date gainAt) {
        this.gainAt = gainAt;
    }
}
