package org.example.bank.controllers;


import org.apache.catalina.connector.Response;
import org.example.bank.exceptions.UserAlreadyExistsException;
import org.example.bank.models.User;
import org.example.bank.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller()
@RequestMapping("v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    @ResponseBody
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping()
    @ResponseBody
    public ResponseEntity<?> saveUser(@RequestParam(value = "name") String name) {
        if (name.isEmpty()) {
            return ResponseEntity
                    .status(Response.SC_BAD_REQUEST)
                    .body(Map.of("message", "name is required"));
        }
        try {
            return ResponseEntity
                    .status(Response.SC_CREATED)
                    .body(userService.saveUser(new User(name)));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity
                    .status(Response.SC_CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
