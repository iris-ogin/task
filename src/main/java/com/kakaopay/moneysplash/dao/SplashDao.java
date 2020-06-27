package com.kakaopay.moneysplash.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class SplashDao {
    @PersistenceContext
    private EntityManager entityManager;

    private Session session() {
        return entityManager.unwrap(Session.class);
    }



}
