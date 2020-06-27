package com.kakaopay.moneysplash.entity;

import javax.persistence.*;

@Entity
@Table(name = "room_talker")
public class RoomTalker {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROOM_TALKER")
    @SequenceGenerator(name = "SEQ_ROOM_TALKER", sequenceName = "room_talker_id_seq", allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "end_user_id")
    private User talker;

    @Column
    private boolean active;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getTalker() {
        return talker;
    }

    public void setTalker(User talker) {
        this.talker = talker;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
