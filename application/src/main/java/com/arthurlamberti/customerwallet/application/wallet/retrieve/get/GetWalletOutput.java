package com.arthurlamberti.customerwallet.application.wallet.retrieve.get;

import com.arthurlamberti.customerwallet.domain.wallet.Wallet;

public record GetWalletOutput(
        String id,
        Double balance,
        String customerId
) {
    public static GetWalletOutput from(Wallet wallet) {
        return new GetWalletOutput(
                wallet.getId().getValue(),
                wallet.getBalance(),
                wallet.getCustomerId()
        );
    }
}
