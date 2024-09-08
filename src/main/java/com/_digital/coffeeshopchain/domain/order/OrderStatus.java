package com._digital.coffeeshopchain.domain.order;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PLACED, IN_PROGRESS, CANCELLED
}
