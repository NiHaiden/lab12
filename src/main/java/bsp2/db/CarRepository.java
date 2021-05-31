package bsp2.db;

import bsp1.db.RunnerRepository;
import bsp1.model.Runner;
import bsp2.model.Car;
import bsp2.model.Station;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CarRepository implements AutoCloseable{
    public final static CarRepository INSTANCE = new CarRepository();

    public static CarRepository getInstance() {
        return INSTANCE;
    }

    private CarRepository() {
    }

    public Optional<Car> getCarByRegistrationNr(String registrationNr) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit-bsp2").createEntityManager();
        try {
            return Optional.ofNullable(em.find(Car.class, registrationNr));
        } finally {
            em.close();
        }
    }

    public List<Car> findAll() {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit-bsp2").createEntityManager();

        try {
            return em.createQuery("select c from Car c").getResultList();
        } finally {
            em.close();
        }
    }

    public boolean insertCar(Car car) {
        EntityManager em =  JPAUtil.getEMF("jpa-test-unit-bsp2").createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(car);
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

    public List<Car> findCarsByStation(Station station) {
        return this.findAll().stream()
                .filter(car -> car.getStation().equals(station))
                .collect(Collectors.toList());
    }

    @Override
    public void close() throws Exception {
        JPAUtil.close();
    }
}
