package org.example.bank.services;

import org.example.bank.exceptions.UserAlreadyExistsException;
import org.example.bank.models.User;
import org.example.bank.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        if (userRepository.findByName(user.getName()).isPresent()) {
            throw new UserAlreadyExistsException("user: " + user.getName() + " already exists");
        }
        return userRepository.save(user);
    }

    public User findByName(String name) {
        return userRepository.findByName(name).orElse(null);
    }
}
