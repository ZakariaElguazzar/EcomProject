package org.example.billingservice.feign;

import org.example.billingservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service")
public interface ProductRestClient {
    @GetMapping("/rest-repos/products/{id}")
    Product getProductById(@PathVariable(name = "id") String id);
    @GetMapping("/rest-repos/products")
    PagedModel<Product> getAllProducts();

}
