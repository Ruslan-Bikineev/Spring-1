package org.example.bank.exceptions;

public class InvalidPhoneNumberException extends RuntimeException {
    public InvalidPhoneNumberException(String phone) {
        super("Phone number: %s is invalid".formatted(phone));
    }
}
