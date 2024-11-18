package com.arthurlamberti.customerwallet.domain.wallet;

import com.arthurlamberti.customerwallet.domain.validation.Error;
import com.arthurlamberti.customerwallet.domain.validation.ValidationHandler;
import com.arthurlamberti.customerwallet.domain.validation.Validator;

import static java.util.Objects.isNull;

public class WalletValidator extends Validator {

    private Wallet wallet;

    protected WalletValidator(final ValidationHandler aHandler, final Wallet wallet) {
        super(aHandler);
        this.wallet = wallet;
    }

    @Override
    public void validate() {
        checkBalance();
        checkClientId();
    }

    public void checkBalance() {
        final var balance = wallet.getBalance();
        if (balance != 0) {
            this.validationHandler().append(new Error("'balance' should be 0"));
        }
    }

    public void checkClientId() {
        final var customerId = wallet.getCustomerId();
        if (isNull(customerId)) {
            this.validationHandler().append(new Error("'customerId' should not be null"));
            return;
        }
        if (customerId.isBlank()) {
            this.validationHandler().append(new Error("'customerId' should not be empty"));
        }
    }
}
