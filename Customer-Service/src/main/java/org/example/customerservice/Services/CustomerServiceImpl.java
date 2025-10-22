package org.example.customerservice.Services;

import lombok.AllArgsConstructor;
import org.example.customerservice.Entities.Customer;
import org.example.customerservice.Repositories.CustomerRepo;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepo customerRepo;
    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    @Override
    public Customer searchCustomerById(String id) {
        return customerRepo.findById(id).orElse(null);
    }

    @Override
    public Customer updateCustomer(String Id, Customer customer) {
        Customer existingCustomer = searchCustomerById(Id);
        if (existingCustomer != null) {
            existingCustomer.setName(customer.getName());
            existingCustomer.setEmail(customer.getEmail());
            return createCustomer(existingCustomer);
        }
        return null;
    }

    @Override
    public void deleteCustomerById(String Id) {
        customerRepo.deleteById(Id);

    }
}
