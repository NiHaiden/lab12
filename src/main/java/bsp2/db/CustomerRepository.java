package bsp2.db;

import bsp2.model.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class CustomerRepository implements AutoCloseable{
    public final static CustomerRepository INSTANCE = new CustomerRepository();

    public static CustomerRepository getInstance() {
        return INSTANCE;
    }

    private CustomerRepository() {
    }

    public Optional<Customer> getCustomerById(Integer id) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit-bsp2").createEntityManager();
        try {
            return Optional.ofNullable(em.find(Customer.class, id));
        } finally {
            em.close();
        }
    }

    public List<Customer> findAll() {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit-bsp2").createEntityManager();

        try {
            return em.createQuery("select c from Customer c").getResultList();
        } finally {
            em.close();
        }
    }

    public boolean insertCustomer(Customer customer) {
        EntityManager em = JPAUtil.getEMF("jpa-test-unit-bsp2").createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(customer);
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
