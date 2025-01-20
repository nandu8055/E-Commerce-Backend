package org.vinchenzo.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vinchenzo.ecommerce.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
