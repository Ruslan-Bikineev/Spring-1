package org.example.bank.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.connector.Response;
import org.example.bank.models.User;
import org.example.bank.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller()
@RequestMapping("v1/user")
public class UserController {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public UserController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/list")
    @ResponseBody
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping()
    @ResponseBody
    public ResponseEntity<?> saveUser(@RequestParam(value = "name") String name)
            throws JsonProcessingException {
        if (name.isEmpty()) {
            return ResponseEntity
                    .status(Response.SC_BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(objectMapper.writeValueAsString(
                            Map.of("message", "name is required")));
        } else {
            if (userService.saveUser(new User(name))) {
                return ResponseEntity
                        .status(Response.SC_CREATED)
                        .body(userService.findByName(name));
            } else {
                return ResponseEntity
                        .status(Response.SC_CONFLICT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(
                                Map.of("message", "user: " + name + " already exists")));
            }
        }
    }
}
