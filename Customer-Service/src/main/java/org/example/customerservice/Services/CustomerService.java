package org.example.customerservice.Services;

import org.example.customerservice.Entities.Customer;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer searchCustomerById(String id);
    Customer updateCustomer(String Id,Customer customer);
    void deleteCustomerById(String Id);
}
