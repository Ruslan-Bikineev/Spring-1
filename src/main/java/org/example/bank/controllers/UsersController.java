package org.example.bank.controllers;


import org.apache.catalina.connector.Response;
import org.example.bank.models.User;
import org.example.bank.services.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller()
@RequestMapping("v1/user")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/list")
    @ResponseBody
    public Iterable<User> getAllUsers() {
        return usersService.getAllUsers();
    }

    @PostMapping()
    @ResponseBody
    public ResponseEntity<?> saveUser(@RequestParam(value = "name") String name) {
        if (name.isEmpty()) {
            return ResponseEntity
                    .status(Response.SC_BAD_REQUEST)
                    .body("{message:name is required}");
        } else {
            if (usersService.saveUser(new User(name))) {
                return ResponseEntity
                        .status(Response.SC_CREATED)
                        .body(usersService.findByName(name));
            } else {
                return ResponseEntity
                        .status(Response.SC_CONFLICT)
                        .body("{message:user " + name + " already exists}");
            }
        }
    }
}
