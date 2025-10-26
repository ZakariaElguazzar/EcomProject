package org.example.billingservice.Repositories;

import org.example.billingservice.Entities.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepo extends JpaRepository<ProductItem, String> {
}
