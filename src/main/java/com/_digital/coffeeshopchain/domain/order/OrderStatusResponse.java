package com._digital.coffeeshopchain.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderStatusResponse {
    private String orderId;
    private Integer queuePosition;
    private Integer estimatedWaitingTime;
    private String orderStatus;
}
