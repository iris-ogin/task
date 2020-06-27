package com.kakaopay.moneysplash.entity;

import javax.persistence.*;

@Entity
@Table(name = "end_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_END_USER")
    @SequenceGenerator(name = "SEQ_END_USER", sequenceName = "end_user_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "room_id")
    private String roomId;

    @Column
    private long balance;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
