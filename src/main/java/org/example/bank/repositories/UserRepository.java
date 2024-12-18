package org.example.bank.repositories;

import org.example.bank.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UserRepository extends ListCrudRepository<User, Long> {
    String FIND_BY_NAME_QUERY = "SELECT u FROM User u WHERE u.name = :name";

    @Query(FIND_BY_NAME_QUERY)
    Optional<User> findByName(String name);
}
