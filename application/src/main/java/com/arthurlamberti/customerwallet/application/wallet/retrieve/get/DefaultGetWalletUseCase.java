package com.arthurlamberti.customerwallet.application.wallet.retrieve.get;

import com.arthurlamberti.customerwallet.domain.exceptions.NotFoundException;
import com.arthurlamberti.customerwallet.domain.validation.Error;
import com.arthurlamberti.customerwallet.domain.wallet.Wallet;
import com.arthurlamberti.customerwallet.domain.wallet.WalletGateway;
import com.arthurlamberti.customerwallet.domain.wallet.WalletID;

public class DefaultGetWalletUseCase extends GetWalletUseCase {

    private final WalletGateway walletGateway;

    public DefaultGetWalletUseCase(
            final WalletGateway walletGateway
    ) {
        this.walletGateway = walletGateway;
    }

    @Override
    public GetWalletOutput execute(String customerId) {
        return this.walletGateway.findByCustomerId(customerId)
                .map(GetWalletOutput::from)
                .orElseThrow(() -> NotFoundException.with(new Error("Wallet not found to customerId %s".formatted(customerId))));
    }
}
