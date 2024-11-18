package com.arthurlamberti.customerwallet.application.wallet.retrieve.list;

import com.arthurlamberti.customerwallet.domain.wallet.Wallet;

public record ListWalletOutput (
        String id,
        Double balance,
        String customerId
){
    public static ListWalletOutput from(Wallet wallet) {
        return new ListWalletOutput(
                wallet.getId().getValue(),
                wallet.getBalance(),
                wallet.getCustomerId()
        );
    }
}
