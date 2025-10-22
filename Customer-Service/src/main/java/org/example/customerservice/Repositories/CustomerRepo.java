package org.example.customerservice.Repositories;

import org.example.customerservice.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(
        collectionResourceRel = "customers",
        path = "customers")
public interface CustomerRepo extends JpaRepository<Customer, String> {
}
