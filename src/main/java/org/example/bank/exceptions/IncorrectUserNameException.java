package org.example.bank.exceptions;

public class IncorrectUserNameException extends RuntimeException {
    public IncorrectUserNameException(String name) {
        super("User name is incorrect: " + name);
    }
}
