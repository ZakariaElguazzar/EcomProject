package org.example.inventoryservice.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private String id;
    private String name;
    private double price;
    private int quantity;
}
