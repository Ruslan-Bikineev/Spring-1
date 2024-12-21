package org.example.bank.controllers;

import io.restassured.RestAssured;
import org.assertj.core.api.SoftAssertions;
import org.example.bank.BankApplicationTests;
import org.example.bank.models.User;
import org.example.bank.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.equalTo;

public class UserControllerTests extends BankApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    void getAllUsersTest_200() {
        SoftAssertions softAssertions = new SoftAssertions();
        List<User> usersList = RestAssured.given()
                .port(port)
                .get("/v1/user/list")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList("$", User.class);
        List<User> dbFindAll = userRepository.findAll();
        IntStream.range(0, dbFindAll.size()).forEach(i -> {
            softAssertions.assertThat(dbFindAll.get(i).equals(usersList.get(i)));
        });
        softAssertions.assertAll();
    }

    @Test
    void createUserTest_201() {
        SoftAssertions softAssertions = new SoftAssertions();
        User user = RestAssured.given()
                .port(port)
                .queryParam("name", "testName")
                .post("/v1/user")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .jsonPath()
                .getObject("$", User.class);
        Optional<User> dbUser = userRepository.findByName(user.getName());
        softAssertions.assertThat(dbUser.isPresent());
        softAssertions.assertThat(dbUser.get().equals(user));
        softAssertions.assertAll();
    }

    @Test
    void postAlreadyExistsUserTest_409() {
        RestAssured.given()
                .port(port)
                .queryParam("name", "Egor")
                .post("/v1/user")
                .then()
                .statusCode(HttpStatus.CONFLICT.value())
                .body("url", equalTo("http://localhost:%d/v1/user".formatted(port)),
                        "message", equalTo("user: Egor already exists"));
    }

    @Test
    void postWithoutUserNameTest_400() {
        RestAssured.given()
                .port(port)
                .post("/v1/user")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("url", equalTo("http://localhost:%d/v1/user".formatted(port)),
                        "message", equalTo("Required request parameter 'name' " +
                                "for method parameter type String is not present"));
    }

    @Test
    void postUserNameWithOnlySpaceTest_400() {
        RestAssured.given()
                .queryParam("name", " ")
                .port(port)
                .post("/v1/user")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("url", equalTo("http://localhost:%d/v1/user".formatted(port)),
                        "message", equalTo("User name is incorrect:  "));
    }
}
