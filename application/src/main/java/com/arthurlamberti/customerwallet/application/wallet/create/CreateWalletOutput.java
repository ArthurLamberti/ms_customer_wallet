package com.arthurlamberti.customerwallet.application.wallet.create;

import com.arthurlamberti.customerwallet.domain.wallet.Wallet;

public record CreateWalletOutput (String id) {
    public static CreateWalletOutput from(final Wallet aWallet) {
        return new CreateWalletOutput(aWallet.getId().getValue());
    }

    public static CreateWalletOutput from(final String anId) {
        return new CreateWalletOutput(anId);
    }
}
