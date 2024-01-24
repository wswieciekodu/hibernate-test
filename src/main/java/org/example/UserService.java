package org.example;

import org.example.model.User;

import java.util.List;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void showAllUsers() {
        List<User> allUsers = userRepository.getAllUsers();

        allUsers.forEach(System.out::println);
    }

    public void deleteUsers() {
        userRepository.deleteAllUsers();
    }
}
