package org.example.bank.repositories;

import org.example.bank.models.Phone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;

public interface PhoneRepository extends ListCrudRepository<Phone, Long> {
}
