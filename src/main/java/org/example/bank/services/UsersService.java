package org.example.bank.services;

import org.example.bank.models.User;
import org.example.bank.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UserRepository userRepository;

    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        if (userRepository.findByName(user.getName()).isPresent()) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    public User findByName(String name) {
        return userRepository.findByName(name).orElse(null);
    }
}
