package org.example.bank.controllers;


import org.apache.catalina.connector.Response;
import org.example.bank.exceptions.UserAlreadyExistsException;
import org.example.bank.models.User;
import org.example.bank.repositories.RoleRepository;
import org.example.bank.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;

@Controller
@RequestMapping("v1/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/list")
    @ResponseBody
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping()
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> saveUser(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "password") String password) {
        if (name.isEmpty()) {
            return ResponseEntity
                    .status(Response.SC_BAD_REQUEST)
                    .body(Map.of("message", "name is required"));
        }
        try {
            return ResponseEntity
                    .status(Response.SC_CREATED)
                    .body(userService.saveUser(new User(name,
                            passwordEncoder.encode(password),
                            Arrays.asList(roleRepository.findByName("ROLE_USER").get()))));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity
                    .status(Response.SC_CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
