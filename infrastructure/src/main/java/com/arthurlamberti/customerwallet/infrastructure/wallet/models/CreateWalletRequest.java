package com.arthurlamberti.customerwallet.infrastructure.wallet.models;

public record CreateWalletRequest(
        Double balance,
        String customerId
) {
}
