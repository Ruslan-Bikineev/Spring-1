package org.example.bank.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String user) {
        super("user: " + user + " already exists");
    }
}
