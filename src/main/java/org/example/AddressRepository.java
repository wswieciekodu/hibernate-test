package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.example.model.Address;

import java.util.List;

public class AddressRepository {

    private final EntityManager entityManager;

    public AddressRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Address findById(Long id) {
        return entityManager.find(Address.class, id);
    }

    public void save(Address address) {
        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();
    }

    public List<Address> getAll() {
        TypedQuery<Address> query = entityManager.createQuery("FROM Address", Address.class);
        return query.getResultList();

    }
    public void deleteAll() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("DELETE FROM Address");
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }
}