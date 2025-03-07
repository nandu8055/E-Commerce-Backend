package org.vinchenzo.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vinchenzo.ecommerce.model.Order;
import org.vinchenzo.ecommerce.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
