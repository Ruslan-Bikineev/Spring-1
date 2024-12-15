package org.example.bank.controllers;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.Optional;

@Controller
public class LoginController {
    @ResponseBody
    @GetMapping("/login")
    public ResponseEntity<?> successLogin(@RequestParam(required = false) Optional<Boolean> error) {
        if (error.isPresent()) {
            if (error.get()) {
                return ResponseEntity
                        .status(Response.SC_UNAUTHORIZED)
                        .body(Map.of("message", "Name or password is incorrect"));
            } else {
                return ResponseEntity
                        .status(Response.SC_OK)
                        .body(Map.of("message", "You are logged in"));

            }
        }
        return ResponseEntity
                .status(Response.SC_UNAUTHORIZED)
                .body(Map.of("message", "You are not logged in"));
    }
}
