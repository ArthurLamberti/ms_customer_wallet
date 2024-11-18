package com.arthurlamberti.customerwallet.infrastructure.wallet.presenters;

import com.arthurlamberti.customerwallet.application.wallet.retrieve.get.GetWalletOutput;
import com.arthurlamberti.customerwallet.application.wallet.retrieve.list.ListWalletOutput;
import com.arthurlamberti.customerwallet.infrastructure.wallet.models.GetWalletResponse;
import com.arthurlamberti.customerwallet.infrastructure.wallet.models.ListWalletResponse;

public interface WalletApiPresenter {

    static ListWalletResponse present(final ListWalletOutput output){
        return new ListWalletResponse(
                output.id(),
                output.balance(),
                output.customerId()
        );
    }

    static GetWalletResponse present(GetWalletOutput output) {
        return new GetWalletResponse(
                output.balance(),
                output.customerId()
        );
    }
}
