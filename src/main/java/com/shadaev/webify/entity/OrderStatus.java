package com.shadaev.webify.entity;

public enum OrderStatus {
    PROCESSING(0),
    IN_DELIVERY(1),
    AWAITING(2),
    DONE(3);

    private final int value;
    OrderStatus(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
