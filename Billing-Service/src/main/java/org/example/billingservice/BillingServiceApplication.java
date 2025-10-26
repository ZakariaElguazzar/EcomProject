package org.example.billingservice;

import org.example.billingservice.Entities.Bill;
import org.example.billingservice.Entities.ProductItem;
import org.example.billingservice.Repositories.BillRepo;
import org.example.billingservice.Repositories.ProductItemRepo;
import org.example.billingservice.feign.CustomerRestClient;
import org.example.billingservice.feign.ProductRestClient;
import org.example.billingservice.model.Customer;
import org.example.billingservice.model.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;


import java.util.Collection;
import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(CustomerRestClient customerRestClient, BillRepo billRepo, ProductItemRepo productItemRepo, ProductRestClient productRestClient) {
        return args -> {
            Collection<Customer> customers = customerRestClient.getAllCustomers().getContent();
            Collection<Product> products = productRestClient.getAllProducts().getContent();
            customers.forEach(customer -> {
                Bill bill = Bill.builder().billingDate(new Date())
                        .customerId(customer.getId())
                        .build();
                billRepo.save(bill);
                products.forEach(product -> {
                    ProductItem productItem = ProductItem.builder()
                            .productId(product.getId())
                            .bill(bill)
                            .quantity(1+new Random().nextInt(100))
                            .unitPrice(product.getPrice())
                            .build();
                    productItemRepo.save(productItem);
                });
            });
        };
    }
}
