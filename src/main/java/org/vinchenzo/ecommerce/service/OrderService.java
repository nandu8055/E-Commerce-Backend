package org.vinchenzo.ecommerce.service;

import org.vinchenzo.ecommerce.Payload.OrderDTO;

public interface OrderService {
    OrderDTO placeOrder(String emailId, Long addressId, String paymentMethod, String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage);
}
