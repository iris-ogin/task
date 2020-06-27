package com.kakaopay.moneysplash.dao;

import com.kakaopay.moneysplash.entity.Gain;
import com.kakaopay.moneysplash.entity.RoomTalker;
import com.kakaopay.moneysplash.entity.Splash;
import org.apache.commons.lang3.RandomUtils;
import org.hibernate.Session;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class SplashDao {
    @PersistenceContext
    private EntityManager entityManager;

    private Session session() {
        return entityManager.unwrap(Session.class);
    }


    public Splash persist(Splash splash) {
        entityManager.persist(splash);
        return splash;
    }

    public Splash selectOneByToken(String token) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Splash> q = builder.createQuery(Splash.class);
        Root<Splash> r = q.from(Splash.class);

        q.select(r);
        q.where(builder.equal(r.get("token"), token));

        return DataAccessUtils.singleResult(entityManager.createQuery(q).getResultList());

    }

    public Gain selectGainByTalker(Splash splash, RoomTalker gainer) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Gain> q = builder.createQuery(Gain.class);
        Root<Gain> r = q.from(Gain.class);

        q.select(r);
        q.where(builder.equal(r.get("splash").get("id"), splash.getId()),
                builder.equal(r.get("gainer").get("talker").get("id"), gainer.getId())
        );

        return DataAccessUtils.singleResult(entityManager.createQuery(q).getResultList());
    }

    public List<Gain> findOneGain(Splash splash) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Gain> q = builder.createQuery(Gain.class);
        Root<Gain> r = q.from(Gain.class);

        q.select(r);
        q.where(builder.equal(r.get("splash").get("id"), splash.getId()),
                builder.equal(r.get("occupied"), false),
                builder.isNull(r.get("gainer")));


        return entityManager.createQuery(q)
                .setMaxResults(1)
                .setFirstResult(0)
                .getResultList();
    }

    public List<Gain> findGainedList(Splash splash) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Gain> q = builder.createQuery(Gain.class);
        Root<Gain> r = q.from(Gain.class);

        q.select(r);
        q.where(builder.equal(r.get("splash").get("id"), splash.getId()),
                builder.equal(r.get("occupied"), true),
                builder.isNotNull(r.get("gainer")));
        
        return entityManager.createQuery(q)
                .getResultList();
    }


    public int updateLock(Gain gain) {
        return session().createQuery("update Gain set occupied = true where id = :id and occupied = false")
                .setParameter("id", gain.getId()).executeUpdate();
    }

    public Gain updateGain(Gain gain) {
        return entityManager.merge(gain);
    }


}
