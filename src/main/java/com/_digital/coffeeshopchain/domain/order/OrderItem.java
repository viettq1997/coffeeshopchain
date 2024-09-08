package com._digital.coffeeshopchain.domain.order;

import jakarta.validation.constraints.NotEmpty;
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
public class OrderItem {
    @NotEmpty
    private String itemId;
    @NotEmpty
    private String name;
    private Integer quantity;
}
