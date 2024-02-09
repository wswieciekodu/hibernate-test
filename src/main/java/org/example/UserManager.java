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
        AddressRepository addressRepository = new AddressRepository(entityManager);

        UserService userService = new UserService(userRepository, addressRepository);

        System.out.println("Removing all users from db");
        userService.deleteUsers();

        User teacher = new User(
                "Magda",
                "W Swiecie Kodu",
                "wswieciekodu@gmail.com",
                34,
                "f",
                Timestamp.from(Instant.now()));

        System.out.println("\n Add first user");
        userService.createUser(teacher);

        User student = new User(
                "Jan",
                "Kowalski",
                "jkowalski@gmail.com",
                30,
                "m",
                Timestamp.from(Instant.now()));

        System.out.println("Add second user");
        userService.createUser(student);

        System.out.println("\n Show all users - for now 2, without address");
        userService.showAllUsers();

        System.out.println("\n Add address for user wswieciekodu@gmail.com");
        userService.addAddress("wswieciekodu@gmail.com",
                "Mogilska",
                "32/54",
                "Cracow"
        );

        System.out.println(" \nShow all users - with address");
        userService.showAllUsers();

        entityManager.close();
        entityManagerFactory.close();
    }
}