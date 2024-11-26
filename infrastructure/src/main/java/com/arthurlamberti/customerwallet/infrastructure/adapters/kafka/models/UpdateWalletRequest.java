package com.arthurlamberti.customerwallet.infrastructure.adapters.kafka.models;

import com.arthurlamberti.customerwallet.domain.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateWalletRequest(
        @JsonProperty("customer_id") String customerId,
        @JsonProperty("balance") Double balance,
        @JsonProperty("type") TransactionType type,
        @JsonProperty("transaction_id") String transactionId
) {
}
