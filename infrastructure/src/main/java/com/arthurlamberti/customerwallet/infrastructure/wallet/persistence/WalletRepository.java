package com.arthurlamberti.customerwallet.infrastructure.wallet.persistence;

import com.arthurlamberti.customerwallet.domain.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<WalletJpaEntity, String> {
    Optional<WalletJpaEntity> findByCustomerId(String customerId);
}
