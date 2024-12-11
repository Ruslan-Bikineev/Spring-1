package org.example.bank.services;

import org.example.bank.models.Phone;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {
    public Phone parserPhone(String phone) {
        if (phone.matches("^(8|\\+7)[0-9]{10}$")) {
            return new Phone(phone);
        } else {
            throw new RuntimeException("Phone number: %s is invalid".formatted(phone));
        }
    }
}
