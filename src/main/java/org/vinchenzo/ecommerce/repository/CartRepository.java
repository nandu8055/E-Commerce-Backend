package org.vinchenzo.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vinchenzo.ecommerce.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
