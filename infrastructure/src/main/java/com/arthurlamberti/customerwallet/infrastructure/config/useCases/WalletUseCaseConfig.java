package com.arthurlamberti.customerwallet.infrastructure.config.useCases;

import com.arthurlamberti.customerwallet.application.wallet.create.CreateWalletUseCase;
import com.arthurlamberti.customerwallet.application.wallet.create.DefaultCreateWalletUseCase;
import com.arthurlamberti.customerwallet.application.wallet.retrieve.get.DefaultGetWalletUseCase;
import com.arthurlamberti.customerwallet.application.wallet.retrieve.get.GetWalletUseCase;
import com.arthurlamberti.customerwallet.application.wallet.retrieve.list.DefaultListWalletUseCase;
import com.arthurlamberti.customerwallet.application.wallet.retrieve.list.ListWalletOutput;
import com.arthurlamberti.customerwallet.application.wallet.retrieve.list.ListWalletUseCase;
import com.arthurlamberti.customerwallet.domain.wallet.WalletGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.Objects.requireNonNull;

@Configuration
public class WalletUseCaseConfig {

    private final WalletGateway walletGateway;

    public WalletUseCaseConfig(final WalletGateway walletGateway) {
        this.walletGateway = requireNonNull(walletGateway);
    }

    @Bean
    public CreateWalletUseCase createWalletUseCase() {
        return new DefaultCreateWalletUseCase(walletGateway);
    }

    @Bean
    public ListWalletUseCase listWalletUseCase() {
        return new DefaultListWalletUseCase(walletGateway);
    }

    @Bean
    public GetWalletUseCase getWalletUseCase() {
        return new DefaultGetWalletUseCase(walletGateway);
    }

}
