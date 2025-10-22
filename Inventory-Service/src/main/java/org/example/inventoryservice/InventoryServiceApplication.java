package org.example.inventoryservice;

import org.example.inventoryservice.Entities.Product;
import org.example.inventoryservice.Repositories.ProductRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(ProductRepo productRepo) {
        return args -> {
            productRepo.saveAll(
                    java.util.List.of(
                            new Product(null, "Laptop",6000, 50),
                            new Product(null, "Smartphone",4000, 200),
                            new Product(null, "Tablet",3000, 150),
                            new Product(null, "Monitor",1000, 75),
                            new Product(null, "Keyboard",300, 300)
                    )

            );

        };

    }

}
