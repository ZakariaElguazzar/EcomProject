package org.example.customerservice;

import lombok.AllArgsConstructor;
import org.example.customerservice.Entities.Customer;
import org.example.customerservice.Services.CustomerService;
import org.example.customerservice.config.CustomerConfigParams;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@AllArgsConstructor
@EnableConfigurationProperties(CustomerConfigParams.class)
//@PropertySource("classpath:Customer-Service.properties")
public class CustomerServiceApplication {
    private CustomerService customerService;

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }
    @Bean
    public CommandLineRunner run() {
        return args -> {
            List<Customer> customers = List.of(
                    new Customer(null, "John Doe", "john.doe@example.com"),
                    new Customer(null, "Jane Smith", "jane.smith@example.com"),
                    new Customer(null, "Ali Ben", "ali.ben@example.com"),
                    new Customer(null, "Sara Lopez", "sara.lopez@example.com"),
                    new Customer(null, "Tom Hanks", "tom.hanks@example.com")
            );

            customers.forEach(customerService::createCustomer);
        };
    }

}
