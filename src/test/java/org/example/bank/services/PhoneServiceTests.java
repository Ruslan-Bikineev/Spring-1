package org.example.bank.services;


import org.example.bank.BankApplicationTests;
import org.example.bank.models.Phone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PhoneServiceTests extends BankApplicationTests {
    private Phone phone;
    private String expectedExceptionMessage = "Phone number: %s is invalid";
    private PhoneService phoneService = new PhoneService();

    @Test
    void eightCodePhoneNumberTest() {
        phone = new Phone("89172018526");
        Assertions.assertTrue(
                phoneService.parserPhone(phone.getNumber()).getNumber()
                        .equals(phone.getNumber()));
    }

    @Test
    void plusSevenCodePhoneNumberTest() {
        phone = new Phone("+79172018526");
        Assertions.assertTrue(
                phoneService.parserPhone(phone.getNumber()).getNumber()
                        .equals(phone.getNumber()));
    }

    @Test
    void eightCodeAndTwelveLengthPhoneNumberTest() {
        phone = new Phone("891720185212");
        Exception exception = Assertions.assertThrows(
                RuntimeException.class, () -> phoneService.parserPhone(phone.getNumber()));
        Assertions.assertEquals(expectedExceptionMessage
                .formatted(phone.getNumber()), exception.getMessage());
    }

    @Test
    void plusSevenCodeAndTenLengthPhoneNumberTest() {
        phone = new Phone("+7917201852");
        Exception exception = Assertions.assertThrows(
                RuntimeException.class, () -> phoneService.parserPhone(phone.getNumber()));
        Assertions.assertEquals(expectedExceptionMessage
                .formatted(phone.getNumber()), exception.getMessage());
    }
}
