package com.kakaopay.moneysplash.service;

import com.kakaopay.moneysplash.dao.UserDao;
import com.kakaopay.moneysplash.entity.RoomTalker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public RoomTalker findOne(long userId, String roomUuid) {
        return userDao.findOneRoomTalker(userId, roomUuid);
    }
}
