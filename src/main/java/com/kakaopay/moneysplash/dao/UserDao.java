package com.kakaopay.moneysplash.dao;

import com.kakaopay.moneysplash.entity.RoomTalker;
import org.hibernate.Session;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    private Session session() {
        return entityManager.unwrap(Session.class);
    }

    public RoomTalker findOneRoomTalker(long userId, String roomUuid) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RoomTalker> q = builder.createQuery(RoomTalker.class);
        Root<RoomTalker> r = q.from(RoomTalker.class);

        q.select(r);
        q.where(builder.equal(r.get("talker").get("id"), userId),
                builder.equal(r.get("room").get("uuid"), roomUuid),
                builder.equal(r.get("active"), true));

        return DataAccessUtils.singleResult(entityManager.createQuery(q).getResultList());
    }
}
