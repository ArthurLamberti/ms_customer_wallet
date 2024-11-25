package com.arthurlamberti.customerwallet.infrastructure.adapters.kafka.consumers;

import com.arthurlamberti.customerwallet.domain.exceptions.NotFoundException;
import com.arthurlamberti.customerwallet.domain.validation.Error;
import com.arthurlamberti.customerwallet.domain.wallet.Wallet;
import com.arthurlamberti.customerwallet.domain.wallet.WalletID;
import com.arthurlamberti.customerwallet.infrastructure.adapters.kafka.models.UpdateWallet;
import com.arthurlamberti.customerwallet.infrastructure.config.json.Json;
import com.arthurlamberti.customerwallet.infrastructure.wallet.persistence.WalletJpaEntity;
import com.arthurlamberti.customerwallet.infrastructure.wallet.persistence.WalletRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class WalletConsumer {

    private final WalletRepository walletRepository;

    public WalletConsumer(final WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @KafkaListener(topics = "cgr.wallet.update")
    public void listen(String message) {
        final var updateBalance = Json.readValue(message, UpdateWallet.class);
        final var walletOpt = walletRepository.findByCustomerId(updateBalance.customerId());

        walletOpt.orElseThrow(() -> NotFoundException.with(new Error("Wallet not found to customer %s".formatted(updateBalance.customerId()))));
        final var wallet = walletOpt.get();
        final var updatedWallet = Wallet.with(WalletID.from(wallet.getId()), wallet.getBalance() + updateBalance.balance(), wallet.getCustomerId());
        walletRepository.save(WalletJpaEntity.from(updatedWallet));
    }

}
