package com.ekart.order.dto;

import com.ekart.order.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for Order Response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private String id;
    private String orderId;
    private Long userId;
    private String userName;
    private Order.OrderStatus status;
    private Double totalAmount;
    private Double shippingAmount;
    private Double taxAmount;
    private Order.PaymentMethod paymentMethod;
    private Order.PaymentStatus paymentStatus;
    private Order.Address shippingAddress;
    private List<Order.OrderItem> items;
    private List<Order.TrackingInfo> tracking;
    private String transactionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.orderId = order.getOrderId();
        this.userId = order.getUserId();
        this.userName = order.getUserName();
        this.status = order.getStatus();
        this.totalAmount = order.getTotalAmount();
        this.shippingAmount = order.getShippingAmount();
        this.taxAmount = order.getTaxAmount();
        this.paymentMethod = order.getPaymentMethod();
        this.paymentStatus = order.getPaymentStatus();
        this.shippingAddress = order.getShippingAddress();
        this.items = order.getItems();
        this.tracking = order.getTracking();
        this.transactionId = order.getTransactionId();
        this.createdAt = order.getCreatedAt();
        this.updatedAt = order.getUpdatedAt();
    }
}
