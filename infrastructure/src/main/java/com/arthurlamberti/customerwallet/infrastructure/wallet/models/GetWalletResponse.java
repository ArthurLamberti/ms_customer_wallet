package com.arthurlamberti.customerwallet.infrastructure.wallet.models;

public record GetWalletResponse(
        Double balance,
        String customerId
){
}
