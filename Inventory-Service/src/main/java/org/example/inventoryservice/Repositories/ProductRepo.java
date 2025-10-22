package org.example.inventoryservice.Repositories;

import org.example.inventoryservice.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface ProductRepo extends JpaRepository<Product,String> {
}
