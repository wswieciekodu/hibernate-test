package org.example;

import org.example.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private AddressRepository addressRepository;

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        System.setOut(new PrintStream(outContent));

        userService = new UserService(userRepository, addressRepository);
    }

    @AfterEach
    void after() {
        System.setOut(originalOut);
    }

    @Test
    public void shouldSaveUserToDbUsingRepositorySaveMethod() {
        // given
        User user = new User();
        user.setId(1L);
        // when
        userService.createUser(user);
        // then
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void showShowAllUsersToConsole() {
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
        when(userRepository.getAllUsers()).thenReturn(users);

        // when
        userService.showAllUsers();

        // then
        String consoleOutput = outContent.toString();
        String expectedString = "User{id=null, firstName='User', lastName='Name', email='test@test.com', age=12, gender='m', createdAt=1970-01-01 05:59:00.0, addresses=[]}\r\n" +
                "User{id=null, firstName='User 2', lastName='Name 2', email='test2@test.com', age=21, gender='f', createdAt=1990-04-05 20:59:00.0, addresses=[]}";
        assertEquals(expectedString, consoleOutput.trim());

        verify(userRepository, times(1)).getAllUsers();
    }

    @Test
    public void shouldDeleteAllUsersFromDbUsingRepositoryDeleteAllMethod() {
        // given

        // when
        userService.deleteUsers();
        // then
        verify(userRepository, times(1)).deleteAllUsers();
    }
}