package org.example.bank.services;

import org.example.bank.exceptions.UserAlreadyExistsException;
import org.example.bank.models.User;
import org.example.bank.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        if (userRepository.findByName(user.getName()).isPresent()) {
            throw new UserAlreadyExistsException("user: " + user.getName() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }
}
