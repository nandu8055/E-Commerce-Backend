package org.vinchenzo.ecommerce.service;

import jakarta.transaction.Transactional;
import org.vinchenzo.ecommerce.Payload.CartDTO;

import java.util.List;

public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity);

    List<CartDTO> getAllCarts();

    CartDTO getCart(String emailId);

    @Transactional
    CartDTO updateProductQuantityInCart(Long productId, Integer update);


    String deleteProductFromCart(Long cartId, Long productId);

    void updateProductInCarts(long cartId, Long productId);
}
