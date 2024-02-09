package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.model.User;

import java.util.List;

public class UserRepository {

    private final EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    public User findByEmail(String email) {
        return entityManager.createQuery(
                        "SELECT u from User u WHERE u.email = :email", User.class)
                .setParameter("email", email).getSingleResult();
    }

    public void save(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    public List<User> getAllUsers() {
        TypedQuery<User> query = entityManager.createQuery("FROM User", User.class);
        return query.getResultList();

    }

    public void deleteAllUsers() {
        entityManager.getTransaction().begin();

        List<User> allUsers = getAllUsers();
        for (User user : allUsers) {
            entityManager.remove(user);
        }

        entityManager.getTransaction().commit();
    }
}