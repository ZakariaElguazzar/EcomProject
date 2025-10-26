package org.example.billingservice.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.billingservice.model.Customer;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bill {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String  id;
    private Date billingDate;
    private String customerId;
    @OneToMany(mappedBy = "bill")
    private List<ProductItem> productItems;
    @Transient private Customer customer;
}
