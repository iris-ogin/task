package com.kakaopay.moneysplash.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Splash {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SPLASH")
    @SequenceGenerator(name = "SEQ_SPLASH", sequenceName = "splash_id_seq", allocationSize = 1)
    private long id;

    @Column(updatable = false)
    private String token;

    @ManyToOne
    @JoinColumn(name = "splasher")
    private RoomTalker splasher;

    @Column
    private long amount;

    @Column(name = "gainer_count")
    private long gainerCount;

    @OneToMany(mappedBy = "splash", cascade = CascadeType.ALL)
    private List<Gain> gainList;

    @Transient
    private List<Gain> gainedList;

    @Column(name = "create_at", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RoomTalker getSplasher() {
        return splasher;
    }

    public void setSplasher(RoomTalker splasher) {
        this.splasher = splasher;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getGainerCount() {
        return gainerCount;
    }

    public void setGainerCount(long gainerCount) {
        this.gainerCount = gainerCount;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public List<Gain> getGainList() {
        return gainList;
    }

    public void setGainList(List<Gain> gainList) {
        this.gainList = gainList;
    }

    public List<Gain> getGainedList() {
        return gainedList;
    }

    public void setGainedList(List<Gain> gainedList) {
        this.gainedList = gainedList;
    }
}
