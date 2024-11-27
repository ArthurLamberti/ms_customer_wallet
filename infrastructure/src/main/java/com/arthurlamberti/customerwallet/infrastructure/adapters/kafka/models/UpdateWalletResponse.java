package com.arthurlamberti.customerwallet.infrastructure.adapters.kafka.models;

import com.arthurlamberti.customerwallet.domain.enums.StatusTransaction;
import com.arthurlamberti.customerwallet.domain.enums.TransactionType;

public record UpdateWalletResponse(
        StatusTransaction status,
        String transactionId,
        TransactionType type
) {
    public static UpdateWalletResponse from(StatusTransaction status, String transactionId, TransactionType type) {
        return new UpdateWalletResponse(status, transactionId, type);
    }
}
