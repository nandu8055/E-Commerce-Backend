package org.vinchenzo.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vinchenzo.ecommerce.model.Address;
import org.vinchenzo.ecommerce.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
