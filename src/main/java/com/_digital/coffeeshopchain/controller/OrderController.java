package com._digital.coffeeshopchain.controller;

import com._digital.coffeeshopchain.domain.common.ApiResponse;
import com._digital.coffeeshopchain.domain.order.OrderCancellationResponse;
import com._digital.coffeeshopchain.domain.order.OrderCreationRequest;
import com._digital.coffeeshopchain.domain.order.OrderCreationResponse;
import com._digital.coffeeshopchain.domain.order.OrderStatusResponse;
import com._digital.coffeeshopchain.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderCreationResponse>> placeOrder(@RequestBody @Valid OrderCreationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request));
    }

    @GetMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderStatusResponse>> getOrderStatus(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getOrderStatus(orderId));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderCancellationResponse>> cancelOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }
}
