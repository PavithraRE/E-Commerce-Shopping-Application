package com.ekart.order.service;

import com.ekart.order.dto.OrderRequest;
import com.ekart.order.dto.OrderResponse;
import com.ekart.order.model.Order;
import com.ekart.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for Order operations
 * Handles order creation, status updates, and tracking
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * Create a new order
     */
    public OrderResponse createOrder(OrderRequest request) {
        Order order = new Order();
        order.setOrderId(generateOrderId());
        order.setUserId(request.getUserId());
        order.setUserName(request.getUserName());
        order.setStatus(Order.OrderStatus.PENDING);
        order.setTotalAmount(request.getTotalAmount());
        order.setShippingAmount(request.getShippingAmount() != null ? request.getShippingAmount() : 0.0);
        order.setTaxAmount(request.getTaxAmount() != null ? request.getTaxAmount() : 0.0);
        order.setPaymentMethod(request.getPaymentMethod());
        order.setPaymentStatus(Order.PaymentStatus.PENDING);
        order.setShippingAddress(request.getShippingAddress());
        order.setItems(request.getItems());
        order.setTransactionId(generateTransactionId());
        order.setTracking(initializeTracking());

        order = orderRepository.save(order);
        return new OrderResponse(order);
    }

    /**
     * Get order by order ID
     */
    public OrderResponse getOrderByOrderId(String orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }
        return new OrderResponse(order);
    }

    /**
     * Get order by MongoDB ID
     */
    public OrderResponse getOrderById(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return new OrderResponse(order);
    }

    /**
     * Get all orders for a user with pagination
     */
    public Page<OrderResponse> getOrdersByUserId(Long userId, int page, int size) {
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Order> orders = orderRepository.findByUserId(userId, pageable);
        return orders.map(OrderResponse::new);
    }

    /**
     * Get all orders for a user
     */
    public List<OrderResponse> getAllOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Get all orders with pagination
     */
    public Page<OrderResponse> getAllOrders(int page, int size) {
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(OrderResponse::new);
    }

    /**
     * Update order status
     */
    public OrderResponse updateOrderStatus(String orderId, Order.OrderStatus status) {
        Order order = orderRepository.findByOrderId(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }

        order.setStatus(status);
        updateTracking(order, status);

        if (status == Order.OrderStatus.DELIVERED) {
            order.setPaymentStatus(Order.PaymentStatus.COMPLETED);
        }

        order = orderRepository.save(order);
        return new OrderResponse(order);
    }

    /**
     * Cancel order
     */
    public OrderResponse cancelOrder(String orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }

        if (order.getStatus() == Order.OrderStatus.DELIVERED || 
            order.getStatus() == Order.OrderStatus.SHIPPED) {
            throw new RuntimeException("Cannot cancel delivered or shipped order");
        }

        order.setStatus(Order.OrderStatus.CANCELLED);
        updateTracking(order, Order.OrderStatus.CANCELLED);

        order = orderRepository.save(order);
        return new OrderResponse(order);
    }

    /**
     * Request return
     */
    public OrderResponse requestReturn(String orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }

        if (order.getStatus() != Order.OrderStatus.DELIVERED) {
            throw new RuntimeException("Only delivered orders can be returned");
        }

        order.setStatus(Order.OrderStatus.RETURN_REQUESTED);
        order = orderRepository.save(order);
        return new OrderResponse(order);
    }

    /**
     * Process return
     */
    public OrderResponse processReturn(String orderId, Boolean approved) {
        Order order = orderRepository.findByOrderId(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }

        if (approved) {
            order.setStatus(Order.OrderStatus.RETURNED);
            order.setPaymentStatus(Order.PaymentStatus.REFUNDED);
        } else {
            order.setStatus(Order.OrderStatus.DELIVERED);
        }

        order = orderRepository.save(order);
        return new OrderResponse(order);
    }

    /**
     * Update payment status
     */
    public OrderResponse updatePaymentStatus(String orderId, Order.PaymentStatus paymentStatus) {
        Order order = orderRepository.findByOrderId(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }

        order.setPaymentStatus(paymentStatus);

        if (paymentStatus == Order.PaymentStatus.COMPLETED) {
            order.setStatus(Order.OrderStatus.CONFIRMED);
            updateTracking(order, Order.OrderStatus.CONFIRMED);
        }

        order = orderRepository.save(order);
        return new OrderResponse(order);
    }

    /**
     * Generate unique order ID
     */
    private String generateOrderId() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String random = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "ORD-" + timestamp + "-" + random;
    }

    /**
     * Generate transaction ID
     */
    private String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();
    }

    /**
     * Initialize tracking information
     */
    private List<Order.TrackingInfo> initializeTracking() {
        List<Order.TrackingInfo> tracking = new ArrayList<>();
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM, hh:mm a"));

        tracking.add(new Order.TrackingInfo("Order Placed", dateTime, true));
        tracking.add(new Order.TrackingInfo("Confirmed", "", false));
        tracking.add(new Order.TrackingInfo("Processing", "", false));
        tracking.add(new Order.TrackingInfo("Shipped", "", false));
        tracking.add(new Order.TrackingInfo("Out for Delivery", "", false));
        tracking.add(new Order.TrackingInfo("Delivered", "", false));

        return tracking;
    }

    /**
     * Update tracking based on status
     */
    private void updateTracking(Order order, Order.OrderStatus status) {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM, hh:mm a"));
        List<Order.TrackingInfo> tracking = order.getTracking();

        for (Order.TrackingInfo info : tracking) {
            switch (status) {
                case CONFIRMED:
                    if (info.getStep().equals("Confirmed")) {
                        info.setDate(dateTime);
                        info.setDone(true);
                    }
                    break;
                case PROCESSING:
                    if (info.getStep().equals("Processing")) {
                        info.setDate(dateTime);
                        info.setDone(true);
                    }
                    break;
                case SHIPPED:
                    if (info.getStep().equals("Shipped")) {
                        info.setDate(dateTime);
                        info.setDone(true);
                    }
                    break;
                case OUT_FOR_DELIVERY:
                    if (info.getStep().equals("Out for Delivery")) {
                        info.setDate(dateTime);
                        info.setDone(true);
                    }
                    break;
                case DELIVERED:
                    if (info.getStep().equals("Delivered")) {
                        info.setDate(dateTime);
                        info.setDone(true);
                    }
                    break;
                case CANCELLED:
                    tracking.add(new Order.TrackingInfo("Cancelled", dateTime, true));
                    break;
                default:
                    break;
            }
        }
    }
}
