package com.arthurlamberti.customerwallet.infrastructure.adapters.kafka.models;

import com.arthurlamberti.customerwallet.domain.enums.TransactionType;

public record UpdateWalletResponse(
        String status,
        String transactionId,
        TransactionType type
) {
    public static UpdateWalletResponse from(String status, String transactionId, TransactionType type) {
        return new UpdateWalletResponse(status, transactionId, type);
    }
}
