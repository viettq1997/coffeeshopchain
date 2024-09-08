package com._digital.coffeeshopchain.service;

import com._digital.coffeeshopchain.domain.common.ApiResponse;
import com._digital.coffeeshopchain.domain.entity.OrderEntity;
import com._digital.coffeeshopchain.domain.order.OrderCancellationResponse;
import com._digital.coffeeshopchain.exception.CustomResponseException;
import com._digital.coffeeshopchain.domain.order.OrderCreationResponse;
import com._digital.coffeeshopchain.domain.order.OrderCreationRequest;
import com._digital.coffeeshopchain.domain.order.OrderStatus;
import com._digital.coffeeshopchain.domain.order.OrderStatusResponse;
import com._digital.coffeeshopchain.exception.ErrorCode;
import com._digital.coffeeshopchain.repository.CustomerRepository;
import com._digital.coffeeshopchain.repository.OrderRepository;
import com._digital.coffeeshopchain.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ShopRepository shopRepository;
    private final CustomerRepository customerRepository;

    public ApiResponse<OrderCreationResponse> createOrder(OrderCreationRequest request) {
        log.info("Create a new order");
        shopRepository.findById(request.getShopId())
                .orElseThrow(() -> new CustomResponseException(ErrorCode.SHOP_NOT_FOUND));

        customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomResponseException(ErrorCode.CUSTOMER_NOT_FOUND));

        OrderEntity orderEntity = OrderEntity
                .builder()
                .shopId(request.getShopId())
                .customerId(request.getCustomerId())
                .orderItems(request.getOrderItems())
                .orderStatus(String.valueOf(OrderStatus.PLACED))
                .build();

        List<OrderEntity> currentQueue = orderRepository.findByShopIdAndOrderStatus(request.getShopId(), String.valueOf(OrderStatus.PLACED));
        orderEntity.setQueuePosition(currentQueue.size() + 1);
        ApiResponse<OrderCreationResponse> response = new ApiResponse<>();
        response.setMessage("Place order successful!");
        response.setData(new OrderCreationResponse(orderRepository.save(orderEntity).getOrderId()));
        log.info("Create a new order successful at shop: {}", request.getShopId());
        return response;
    }

    public ApiResponse<OrderStatusResponse> getOrderStatus(String orderId) {
        log.info("Get order status for order id: {}", orderId);
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomResponseException(ErrorCode.ORDER_NOT_FOUND));
        ApiResponse<OrderStatusResponse> response = new ApiResponse<>();
        response.setMessage("Get order status successful!");
        response.setData(OrderStatusResponse
                .builder()
                .orderId(orderId)
                .queuePosition(orderEntity.getQueuePosition())
                .estimatedWaitingTime(orderEntity.getEstimateWaitingTime())
                .orderStatus(orderEntity.getOrderStatus())
                .build());
        return response;
    }

    public ApiResponse<OrderCancellationResponse> cancelOrder(String orderId) {
        log.info("Cancel order with id: {}", orderId);
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomResponseException(ErrorCode.ORDER_NOT_FOUND));
        // Cannot cancel in progress or cancelled orders
        if (!String.valueOf(OrderStatus.PLACED).equals(orderEntity.getOrderStatus())) {
            throw new CustomResponseException(ErrorCode.CANCEL_INVALID_ORDER);
        }

        orderEntity.setOrderStatus(String.valueOf(OrderStatus.CANCELLED));
        orderRepository.save(orderEntity);
        ApiResponse<OrderCancellationResponse> response = new ApiResponse<>();
        response.setMessage("Cancel order successful!");
        response.setData(new OrderCancellationResponse(orderId));
        log.info("Cancel order success with id: {}", orderId);
        return response;
    }
}
