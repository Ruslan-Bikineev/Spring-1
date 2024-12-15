package org.example.bank.repositories;

import org.example.bank.models.Privilege;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PrivilegeRepository extends CrudRepository<Privilege, Long> {
    @Query("SELECT p FROM Privilege p WHERE p.name = :name")
    Optional<Privilege> findByName(String name);
}
