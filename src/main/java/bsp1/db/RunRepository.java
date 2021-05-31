package bsp1.db;

import bsp1.model.Run;
import bsp1.model.Runner;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RunRepository implements AutoCloseable {
    public final static RunRepository INSTANCE = new RunRepository();

    public static RunRepository getInstance() {
        return INSTANCE;
    }

    private RunRepository() {
    }

    public Optional<Run> getRunrById(Integer id) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit").createEntityManager();
        try {
            return Optional.ofNullable(em.find(Run.class, id));
        } finally {
            em.close();
        }
    }

    public List<Run> findAll() {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit").createEntityManager();

        try {
            return em.createQuery("select r from Run r").getResultList();
        } finally {
            em.close();
        }
    }

    public boolean persistRun(Run run) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit").createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(run);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }

            return false;
        } finally {
            em.close();
        }
    }

    public List<Runner> getRunnersWithMinimumDistance(Integer m) {

        if(m < 0) { throw new IllegalArgumentException("Darf nicht 0 sein!"); }

        EntityManager em = JPAUtil.getEMF("jpa-test-unit").createEntityManager();

        List<Runner> runners = new ArrayList<>();
        List<Run> runs = new ArrayList<>();
        try {
            TypedQuery<Run> query = em.createQuery("select r from Run r where r.distance >= :dist", Run.class);
            query.setParameter("dist", m);
            runs = query.getResultList();
        } finally {
            em.close();
        }

        System.out.println(runs);
        runs.forEach(run -> runners.add(run.getRunner()));

        return runners;
    }

    public Integer totalDistanceInInterval(Runner r, LocalDate from, LocalDate to) {
        Integer totalDistance = 0;
        List<Run> runs = new ArrayList<>();

        EntityManager em = JPAUtil.getEMF("jpa-test-unit").createEntityManager();

        try {
            TypedQuery<Run> query = em.createQuery("select r from Run r where r.runner.id = :id and (date >= :from and date <= :to)", Run.class);
            query.setParameter("id", r.getId());
            query.setParameter("from", from);
            query.setParameter("to", to);
            runs = query.getResultList();
        } finally {
            em.close();
        }

        return runs.stream().mapToInt(run -> run.getDistance()).sum();
    }

    @Override
    public void close() throws Exception {
        JPAUtil.close();
    }
}
