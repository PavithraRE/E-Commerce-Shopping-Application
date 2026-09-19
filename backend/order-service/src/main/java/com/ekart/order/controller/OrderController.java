package com.ekart.order.controller;

import com.ekart.order.dto.OrderRequest;
import com.ekart.order.dto.OrderResponse;
import com.ekart.order.model.Order;
import com.ekart.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Order operations
 * Provides endpoints for order management and tracking
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Order management APIs")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Create a new order", description = "Create a new order with items and shipping details")
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/order-id/{orderId}")
    @Operation(summary = "Get order by order ID", description = "Get order details by order ID")
    public ResponseEntity<OrderResponse> getOrderByOrderId(@PathVariable String orderId) {
        OrderResponse response = orderService.getOrderByOrderId(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Get order details by MongoDB ID")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable String id) {
        OrderResponse response = orderService.getOrderById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get orders by user ID", description = "Get all orders for a specific user with pagination")
    public ResponseEntity<Page<OrderResponse>> getOrdersByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<OrderResponse> response = orderService.getOrdersByUserId(userId, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}/all")
    @Operation(summary = "Get all orders by user ID", description = "Get all orders for a specific user without pagination")
    public ResponseEntity<List<OrderResponse>> getAllOrdersByUserId(@PathVariable Long userId) {
        List<OrderResponse> response = orderService.getAllOrdersByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all orders", description = "Get all orders with pagination")
    public ResponseEntity<Page<OrderResponse>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<OrderResponse> response = orderService.getAllOrders(page, size);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{orderId}/status")
    @Operation(summary = "Update order status", description = "Update the status of an order")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable String orderId,
            @RequestParam Order.OrderStatus status) {
        OrderResponse response = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{orderId}/cancel")
    @Operation(summary = "Cancel order", description = "Cancel an order")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable String orderId) {
        OrderResponse response = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{orderId}/return")
    @Operation(summary = "Request return", description = "Request a return for a delivered order")
    public ResponseEntity<OrderResponse> requestReturn(@PathVariable String orderId) {
        OrderResponse response = orderService.requestReturn(orderId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{orderId}/return/process")
    @Operation(summary = "Process return", description = "Process a return request")
    public ResponseEntity<OrderResponse> processReturn(
            @PathVariable String orderId,
            @RequestParam Boolean approved) {
        OrderResponse response = orderService.processReturn(orderId, approved);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{orderId}/payment-status")
    @Operation(summary = "Update payment status", description = "Update the payment status of an order")
    public ResponseEntity<OrderResponse> updatePaymentStatus(
            @PathVariable String orderId,
            @RequestParam Order.PaymentStatus paymentStatus) {
        OrderResponse response = orderService.updatePaymentStatus(orderId, paymentStatus);
        return ResponseEntity.ok(response);
    }
}
