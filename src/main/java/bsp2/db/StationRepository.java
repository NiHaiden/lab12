package bsp2.db;

import bsp2.model.Rental;
import bsp2.model.Station;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class StationRepository implements AutoCloseable{
    public final static StationRepository INSTANCE = new StationRepository();

    public static StationRepository getInstance() {
        return INSTANCE;
    }

    private StationRepository() {
    }

    public Optional<Station> getStationById(Integer id) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit-bsp2").createEntityManager();
        try {
            return Optional.ofNullable(em.find(Station.class, id));
        } finally {
            em.close();
        }
    }

    public List<Station> findAll() {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit-bsp2").createEntityManager();

        try {
            return em.createQuery("select s from Station s").getResultList();
        } finally {
            em.close();
        }
    }

    public boolean insertStation(Station station) {
        EntityManager em =  JPAUtil.getEMF("jpa-test-unit-bsp2").createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(station);
            tx.commit();
            return true;
        } catch (Exception e) {
            if(tx.isActive()) {
                tx.rollback();
            }

            return false;
        } finally {
            em.close();
        }
    }
    @Override
    public void close() throws Exception {
        JPAUtil.close();
    }
}
