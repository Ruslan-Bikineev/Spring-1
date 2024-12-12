package org.example.bank.controllers;

import org.assertj.core.api.SoftAssertions;
import org.example.bank.BankApplicationTests;
import org.example.bank.models.User;
import org.example.bank.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class UserControllerTests extends BankApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;
    private SoftAssertions softAssertions;

    @BeforeEach
    void setUp() {
        softAssertions = new SoftAssertions();
    }

    @AfterEach
    void tearDown() {
        softAssertions.assertAll();
    }

    @Test
    void getV1UserListEndpointTest_200() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(
                "/v1/user/list",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        softAssertions.assertThat(responseEntity.getStatusCodeValue() == HttpStatus.OK.value());
        List<User> users = responseEntity.getBody();
        List<User> dbFindAll = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        IntStream.range(0, dbFindAll.size()).forEach(i -> {
            softAssertions.assertThat(dbFindAll.get(i).equals(users.get(i)));
        });
    }

    @Test
    void postV1UserEndpointTest_201() {
        var uri = UriComponentsBuilder.fromUriString("/v1/user")
                .queryParam("name", "testName");
        ResponseEntity<User> responseEntity = restTemplate.exchange(
                uri.buildAndExpand().toUri(),
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<>() {
                });
        softAssertions.assertThat(responseEntity.getStatusCodeValue() == HttpStatus.CREATED.value());
        Optional<User> byName = userRepository.findByName(responseEntity.getBody().getName());
        Assertions.assertTrue(byName.isPresent());
        Assertions.assertTrue(byName.get().equals(responseEntity.getBody()));
    }
}
