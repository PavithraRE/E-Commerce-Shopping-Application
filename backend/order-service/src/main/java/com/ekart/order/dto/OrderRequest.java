package com.ekart.order.dto;

import com.ekart.order.model.Order;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Order Request
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "User name is required")
    private String userName;

    @NotNull(message = "Total amount is required")
    @Positive(message = "Total amount must be positive")
    private Double totalAmount;

    private Double shippingAmount;

    private Double taxAmount;

    @NotNull(message = "Payment method is required")
    private Order.PaymentMethod paymentMethod;

    @NotNull(message = "Shipping address is required")
    private Order.Address shippingAddress;

    @NotNull(message = "Order items are required")
    private List<Order.OrderItem> items;
}
