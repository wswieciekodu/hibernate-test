package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.model.User;

import java.sql.Timestamp;
import java.time.Instant;

public class UserManager {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        UserRepository userRepository = new UserRepository(entityManager);
        UserService userService = new UserService(userRepository);

        userService.deleteUsers();

        User teacher = new User(
                "Magda",
                "W Swiecie Kodu",
                "wswieciekodu@gmail.com",
                34,
                "f",
                Timestamp.from(Instant.now()));

        userService.createUser(teacher);

        User student = new User(
                "Jan",
                "Kowalski",
                "jkowalki@gmail.com",
                30,
                "m",
                Timestamp.from(Instant.now()));

        userService.createUser(student);

        userService.showAllUsers();

        entityManager.close();
        entityManagerFactory.close();
    }
}