package org.example.bank.services;

import org.example.bank.exceptions.IncorrectUserNameException;
import org.example.bank.exceptions.UserAlreadyExistsException;
import org.example.bank.models.User;
import org.example.bank.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user)
            throws UserAlreadyExistsException, IncorrectUserNameException {
        if (user.getName().isBlank()) {
            throw new IncorrectUserNameException(user.getName());
        } else if (userRepository.findByName(user.getName()).isPresent()) {
            throw new UserAlreadyExistsException(user.getName());
        }
        return userRepository.save(user);
    }
}
