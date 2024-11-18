package com.arthurlamberti.customerwallet.application.wallet.retrieve.list;

import com.arthurlamberti.customerwallet.domain.wallet.WalletGateway;

import java.util.List;

public class DefaultListWalletUseCase extends ListWalletUseCase{

    private final WalletGateway walletGateway;

    public DefaultListWalletUseCase(
            final WalletGateway walletGateway
    ) {
        this.walletGateway = walletGateway;
    }

    @Override
    public List<ListWalletOutput> execute() {
        return this.walletGateway.findAll()
                .stream()
                .map(ListWalletOutput::from)
                .toList();
    }
}
