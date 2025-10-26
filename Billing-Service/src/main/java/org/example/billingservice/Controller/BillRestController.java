package org.example.billingservice.Controller;

import lombok.AllArgsConstructor;
import org.example.billingservice.Entities.Bill;
import org.example.billingservice.Entities.ProductItem;
import org.example.billingservice.Repositories.BillRepo;
import org.example.billingservice.feign.CustomerRestClient;
import org.example.billingservice.feign.ProductRestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class BillRestController {
    private BillRepo billRepo;
    private CustomerRestClient customerRestClient;
    private ProductRestClient productRestClient;

    @GetMapping("bills/{id}")
    public Bill getBillById(@PathVariable String id){
        Bill bill = billRepo.findById(id).get();
        bill.setCustomer(customerRestClient.getCustomerById(bill.getCustomerId()));
        List<ProductItem> productItems = bill.getProductItems();
        productItems.stream().forEach(
                productItem -> {
                    productItem.setProduct(productRestClient.getProductById(productItem.getProductId()));
                });
        return bill;
                }

    }
