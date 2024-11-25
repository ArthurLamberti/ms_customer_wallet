package com.arthurlamberti.customerwallet.infrastructure.adapters.kafka.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateWallet(
        @JsonProperty("customer_id") String customerId,
        Double balance
) {
}
