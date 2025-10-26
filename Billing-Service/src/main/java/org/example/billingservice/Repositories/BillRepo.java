package org.example.billingservice.Repositories;

import org.example.billingservice.Entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepo extends JpaRepository<Bill, String> {
}
