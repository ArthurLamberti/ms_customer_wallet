package com.arthurlamberti.customerwallet.infrastructure.adapters.kafka.consumers;

import com.arthurlamberti.customerwallet.domain.enums.StatusTransaction;
import com.arthurlamberti.customerwallet.domain.exceptions.NotFoundException;
import com.arthurlamberti.customerwallet.domain.validation.Error;
import com.arthurlamberti.customerwallet.domain.wallet.Wallet;
import com.arthurlamberti.customerwallet.domain.wallet.WalletID;
import com.arthurlamberti.customerwallet.infrastructure.adapters.kafka.models.UpdateWalletRequest;
import com.arthurlamberti.customerwallet.infrastructure.adapters.kafka.models.UpdateWalletResponse;
import com.arthurlamberti.customerwallet.infrastructure.adapters.kafka.producer.WalletProducer;
import com.arthurlamberti.customerwallet.infrastructure.config.json.Json;
import com.arthurlamberti.customerwallet.infrastructure.wallet.persistence.WalletJpaEntity;
import com.arthurlamberti.customerwallet.infrastructure.wallet.persistence.WalletRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WalletConsumer {

    private final WalletRepository walletRepository;
    private final WalletProducer walletProducer;

    public WalletConsumer(
            final WalletRepository walletRepository,
            final WalletProducer walletProducer
    ) {
        this.walletRepository = walletRepository;
        this.walletProducer = walletProducer;
    }

    @KafkaListener(topics = "cgr.wallet.update")
    public void listen(String message) {
        try {
            final var updateBalance = Json.readValue(message, UpdateWalletRequest.class);
            final var walletOpt = walletRepository.findByCustomerId(updateBalance.customerId());

            walletOpt.orElseThrow(() -> NotFoundException.with(new Error("Wallet not found to customer %s".formatted(updateBalance.customerId()))));
            final var wallet = walletOpt.get();
            final var updatedWallet = Wallet.with(WalletID.from(wallet.getId()), wallet.getBalance() + updateBalance.balance(), wallet.getCustomerId());
            walletRepository.save(WalletJpaEntity.from(updatedWallet));
            final var response = UpdateWalletResponse.from(StatusTransaction.OK, updateBalance.transactionId(), updateBalance.type());
            this.walletProducer.sendMessage("cgr.wallet.update.response", updatedWallet.getCustomerId(), response);
        } catch (Exception e) {
            final var updateBalance = Json.readValue(message, UpdateWalletRequest.class);
            final var response = UpdateWalletResponse.from(StatusTransaction.NOK, updateBalance.transactionId(), null);
            this.walletProducer.sendMessage("cgr.wallet.update.response", updateBalance.customerId(), response);
        }
    }
}
