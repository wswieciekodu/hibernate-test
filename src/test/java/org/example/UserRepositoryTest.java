package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserRepositoryTest {

    private EntityManager entityManager;
    private TypedQuery<User> query;
    private UserRepository userRepository;
    private EntityTransaction entityTransaction;

    @BeforeEach
    void setUp() {
        entityManager = mock(EntityManager.class);
        query = mock(TypedQuery.class);
        entityTransaction = mock(EntityTransaction.class);

        userRepository = new UserRepository(entityManager);
    }

    @Test
    void shouldReturnUserReturnedFromDb() {
        // given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setFirstName("First Name");
        user.setLastName("Last name");

        when(entityManager.find(User.class, userId)).thenReturn(user);

        // when
        User result = userRepository.findById(userId);

        // then
        assertEquals(user, result);
    }

    @Test
    void shouldInvokeFindMethodOfEntityManagerWithUserIDPassedToRepository() {
        // given
        Long userId = 1L;

        // when
        userRepository.findById(userId);

        // then
        verify(entityManager, times(1)).find(User.class, userId);
    }

    @Test
    void shouldPersistUserToDbInvokingPersistMethodOfEntityManagerWithGivenUser() {
        // given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setFirstName("First Name");
        user.setLastName("Last name");

        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        // when
        userRepository.save(user);
        // then
        verify(entityManager, times(1)).persist(user);
    }

    @Test
    void shouldReturnListOfUsersReturnedFromDb() {
        // given
        User user1 = new User(
                "User",
                "Name",
                "test@test.com",
                12,
                "m",
                Timestamp.valueOf(LocalDateTime.of(1970, Month.JANUARY, 01, 5, 59)));

        User user2 = new User(
                "User 2",
                "Name 2",
                "test2@test.com",
                21,
                "f",
                Timestamp.valueOf(LocalDateTime.of(1990, Month.APRIL, 05, 20, 59)));

        List<User> users = List.of(user1, user2);
        when(entityManager.createQuery("FROM User", User.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(users);

        // when
        List<User> result = userRepository.getAllUsers();
        // then
        assertEquals(users, result);
    }

    @Test
    void shouldDeleteAllUsersFromDbByGetAllUsersAndRemoveOneByOne() {
        // given
        User user1 = new User(
                "User",
                "Name",
                "test@test.com",
                12,
                "m",
                Timestamp.valueOf(LocalDateTime.of(1970, Month.JANUARY, 01, 5, 59)));

        User user2 = new User(
                "User 2",
                "Name 2",
                "test2@test.com",
                21,
                "f",
                Timestamp.valueOf(LocalDateTime.of(1990, Month.APRIL, 05, 20, 59)));

        List<User> users = List.of(user1, user2);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.createQuery("FROM User", User.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(users);

        // when
        userRepository.deleteAllUsers();
        // then
        verify(entityManager, times(1)).createQuery("FROM User", User.class);
        verify(entityManager, times(2)).remove(any(User.class));
    }
}
