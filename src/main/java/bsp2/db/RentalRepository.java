package bsp2.db;

import bsp2.model.Car;
import bsp2.model.Customer;
import bsp2.model.Rental;
import bsp2.model.Station;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RentalRepository implements AutoCloseable {

    public final static RentalRepository INSTANCE = new RentalRepository();

    public static RentalRepository getInstance() {
        return INSTANCE;
    }

    private RentalRepository() {
    }

    public Optional<Rental> getRentalById(Integer id) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit-bsp2").createEntityManager();
        try {
            return Optional.ofNullable(em.find(Rental.class, id));
        } finally {
            em.close();
        }
    }

    public List<Rental> findAll() {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit-bsp2").createEntityManager();

        try {
            return em.createQuery("select r from Rental r").getResultList();
        } finally {
            em.close();
        }
    }

    public boolean insertRental(Rental rental) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit-bsp2").createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(rental);
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


    public List<Rental> findRentalsByCustomer(Customer c) {
        return this.findAll().stream()
                .filter(rental -> rental.getCustomer().equals(c))
                .collect(Collectors.toList());
    }

    public boolean returnCar(Rental r, Station returnStation, LocalDate returnDate, Integer km) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit-bsp2").createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Car c = r.getCar();
        try {
            tx.begin();
            c.setMilage(c.getMilage() + km);
            c = em.merge(c);
            r.setKm(km);
            r.setReturnStation(returnStation);
            r.setReturnDate(returnDate);
            r = em.merge(r);
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

    @Override
    public void close() throws Exception {
        JPAUtil.close();
    }
}
