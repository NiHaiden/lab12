package bsp1.db;


import bsp1.model.Runner;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class RunnerRepository implements AutoCloseable {
    public final static RunnerRepository INSTANCE = new RunnerRepository();

    public static RunnerRepository getInstance() {
        return INSTANCE;
    }

    private RunnerRepository() {
    }

    public Optional<Runner> getRunnerById(Integer id) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit").createEntityManager();
        try {
            return Optional.ofNullable(em.find(Runner.class, id));
        } finally {
            em.close();
        }
    }

    public List<Runner> findAll() {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit").createEntityManager();

        try {
            return em.createQuery("select r from Runner r").getResultList();
        } finally {
            em.close();
        }
    }

    public boolean persistRunner(Runner runner) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit").createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(runner);
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
