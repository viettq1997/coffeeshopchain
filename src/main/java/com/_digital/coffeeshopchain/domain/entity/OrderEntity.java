package com._digital.coffeeshopchain.domain.entity;

import com._digital.coffeeshopchain.converter.OrderItemAttributeConverter;
import com._digital.coffeeshopchain.domain.order.OrderItem;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tb_order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderId;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "shop_id")
    private String shopId;

    @Column(name = "order_items", columnDefinition = "jsonb")
    @Convert(converter = OrderItemAttributeConverter.class)
    private List<OrderItem> orderItems;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "queue_position")
    private Integer queuePosition;

    @Column(name = "estimate_waiting_time")
    private Integer estimateWaitingTime;
}
