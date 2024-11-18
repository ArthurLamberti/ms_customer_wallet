package com.arthurlamberti.customerwallet.application.wallet.create;

import com.arthurlamberti.customerwallet.domain.exceptions.NotificationException;
import com.arthurlamberti.customerwallet.domain.validation.handler.Notification;
import com.arthurlamberti.customerwallet.domain.wallet.Wallet;
import com.arthurlamberti.customerwallet.domain.wallet.WalletGateway;

public class DefaultCreateWalletUseCase extends CreateWalletUseCase {

    private final WalletGateway walletGateway;

    public DefaultCreateWalletUseCase(
            final WalletGateway walletGateway
    ) {
        this.walletGateway = walletGateway;
    }

    @Override
    public CreateWalletOutput execute(CreateWalletCommand aCommand) {
        final var notification = Notification.create();
        final var aWallet = notification.validate(
                () -> Wallet.newWallet(aCommand.balance(), aCommand.clientId())
        );

        if (notification.hasError()) {
            throw new NotificationException("Could not create Aggregate Wallet", notification);
        }

        return CreateWalletOutput.from(this.walletGateway.create(aWallet));
    }
}
