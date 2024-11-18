package com.arthurlamberti.customerwallet.infrastructure.wallet.models;

public record ListWalletResponse (
        String id,
        Double balance,
        String customerId
){
}
