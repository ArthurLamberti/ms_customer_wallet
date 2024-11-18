package com.arthurlamberti.customerwallet.infrastructure.api.controllers;

import com.arthurlamberti.customerwallet.application.wallet.create.CreateWalletCommand;
import com.arthurlamberti.customerwallet.application.wallet.create.CreateWalletUseCase;
import com.arthurlamberti.customerwallet.application.wallet.retrieve.get.GetWalletUseCase;
import com.arthurlamberti.customerwallet.application.wallet.retrieve.list.ListWalletUseCase;
import com.arthurlamberti.customerwallet.infrastructure.api.WalletApi;
import com.arthurlamberti.customerwallet.infrastructure.wallet.models.CreateWalletRequest;
import com.arthurlamberti.customerwallet.infrastructure.wallet.models.GetWalletResponse;
import com.arthurlamberti.customerwallet.infrastructure.wallet.models.ListWalletResponse;
import com.arthurlamberti.customerwallet.infrastructure.wallet.presenters.WalletApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class WalletController implements WalletApi {

    private final CreateWalletUseCase createWalletUseCase;
    private final ListWalletUseCase listWalletUseCase;
    private final GetWalletUseCase getWalletUseCase;

    public WalletController(
            final CreateWalletUseCase createWalletUseCase,
            final ListWalletUseCase listWalletUseCase,
            final GetWalletUseCase getWalletUseCase
    ) {
        this.createWalletUseCase = createWalletUseCase;
        this.listWalletUseCase = listWalletUseCase;
        this.getWalletUseCase = getWalletUseCase;
    }

    @Override
    public ResponseEntity<?> createCustomer(CreateWalletRequest input) {
        final var aCommand = CreateWalletCommand.with(input.balance(), input.customerId());

        final var output = this.createWalletUseCase.execute(aCommand);

        return ResponseEntity.created(URI.create("/wallets/" + output.id())).body(output);
    }

    @Override
    public List<ListWalletResponse> listCustomer() {
        return this.listWalletUseCase.execute()
                .stream()
                .map(WalletApiPresenter::present)
                .toList();
    }

    @Override
    public GetWalletResponse getWalletByCustomerId(String customerId) {
        return WalletApiPresenter.present(this.getWalletUseCase.execute(customerId));
    }
}
