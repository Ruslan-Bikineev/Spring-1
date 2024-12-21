package org.example.bank.services;

import org.example.bank.exceptions.InvalidPhoneNumberException;
import org.example.bank.models.Phone;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {
    private static final String RU_VALID_PHONE_REGEX = "^(8|\\+7)[0-9]{10}$";

    public Phone parserPhone(String phone) {
        if (phone.matches(RU_VALID_PHONE_REGEX)) {
            return new Phone(phone);
        } else {
            throw new InvalidPhoneNumberException(phone);
        }
    }
}
