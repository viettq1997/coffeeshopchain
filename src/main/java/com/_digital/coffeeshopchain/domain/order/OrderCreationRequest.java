package com._digital.coffeeshopchain.domain.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreationRequest {

    @NotEmpty(message = "Customer id is mandatory field")
    private String customerId;
    @NotEmpty(message = "Shop id is mandatory field")
    private String shopId;
    @NotNull(message = "Order items is mandatory field")
    private List<OrderItem> orderItems;
}
