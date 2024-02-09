package org.example;

import org.example.model.Address;
import org.example.model.User;

import java.util.List;

public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public UserService(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
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

    public void addAddress(String email, String street, String number, String city) {
        User user = userRepository.findByEmail(email);

        Address address = new Address();
        address.setStreet(street);
        address.setNumber(number);
        address.setCity(city);

        user.getAddresses().add(address);

        userRepository.save(user);
    }
}
