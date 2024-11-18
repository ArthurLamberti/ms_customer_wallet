package com.arthurlamberti.customerwallet.domain.wallet;

import com.arthurlamberti.customerwallet.domain.AggregateRoot;
import com.arthurlamberti.customerwallet.domain.exceptions.NotificationException;
import com.arthurlamberti.customerwallet.domain.validation.ValidationHandler;
import com.arthurlamberti.customerwallet.domain.validation.handler.Notification;
import lombok.Getter;

@Getter
public class Wallet extends AggregateRoot<WalletID> {

    private Double balance;
    private String customerId;

    protected Wallet(

            final WalletID anId,
            final Double aBalance,
            final String aCustomerId
    ) {
        super(anId);
        this.balance = aBalance;
        this.customerId = aCustomerId;

        selfValidate();
    }

    public static Wallet newWallet(
            final Double aBalance,
            final String aCustomerId
    ) {
        final var anId = WalletID.unique();
        return new Wallet(anId, aBalance, aCustomerId);
    }

    public static Wallet with(
            final WalletID id,
            final Double balance,
            final String customerId) {
        return new Wallet(
                id,
                balance,
                customerId
        );
    }

    @Override
    public void validate(ValidationHandler handler) {
        new WalletValidator(handler, this).validate();
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);
        if (notification.hasError()) {
            throw new NotificationException("Failed to create a Wallet to customer " + customerId, notification);
        }
    }
}
