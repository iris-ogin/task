package com.kakaopay.moneysplash.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Gain {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GAIN")
    @SequenceGenerator(name = "SEQ_GAIN", sequenceName = "gain_id_seq", allocationSize = 1)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "splash_id")
    private Splash splash;

    @ManyToOne
    @JoinColumn(name = "gainer")
    private RoomTalker gainer;

    @Column(name = "gain_amount", updatable = false)
    private long gainAmount;

    @Column(name = "gain_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gainAt;

    @Column
    private boolean occupied;

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

    public RoomTalker getGainer() {
        return gainer;
    }

    public void setGainer(RoomTalker gainer) {
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

    public boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
