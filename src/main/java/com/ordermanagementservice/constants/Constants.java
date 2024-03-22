package com.ordermanagementservice.constants;

public class Constants {
    public static final String KAFKA_TOPIC = "order-topic";

    public enum StatusMessages {
        SUBMITTED("Order successfully submitted!"),
        NOT_SUBMITTED("Failed to submit order"),

        EMPTY_TOPIC("Empty kafka topic"),

        EMPTY_ORDER("Empty order");

        private final String value;

        StatusMessages(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
