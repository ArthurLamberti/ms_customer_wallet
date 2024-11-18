package com.arthurlamberti.customerwallet.infrastructure.wallet;

import com.arthurlamberti.customerwallet.domain.wallet.Wallet;
import com.arthurlamberti.customerwallet.domain.wallet.WalletGateway;
import com.arthurlamberti.customerwallet.infrastructure.wallet.persistence.WalletJpaEntity;
import com.arthurlamberti.customerwallet.infrastructure.wallet.persistence.WalletRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Component
public class WalletMySQLGateway implements WalletGateway {

    private final WalletRepository walletRepository;

    public WalletMySQLGateway(final WalletRepository walletRepository) {
        this.walletRepository = requireNonNull(walletRepository);
    }


    @Override
    public Wallet create(Wallet aWallet) {
        return this.walletRepository.save(WalletJpaEntity.from(aWallet)).toAggregate();
    }

    @Override
    public List<Wallet> findAll() {
        return this.walletRepository.findAll()
                .stream()
                .map(WalletJpaEntity::toAggregate)
                .toList();
    }

    @Override
    public Optional<Wallet> findByCustomerId(String customerId) {
        return this.walletRepository.findByCustomerId(customerId)
                .map(WalletJpaEntity::toAggregate);
    }
}
