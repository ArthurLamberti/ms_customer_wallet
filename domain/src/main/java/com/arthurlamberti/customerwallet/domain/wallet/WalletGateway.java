package com.arthurlamberti.customerwallet.domain.wallet;

import java.util.List;
import java.util.Optional;

public interface WalletGateway {

    Wallet create(final Wallet aWallet);

    List<Wallet> findAll();

    Optional<Wallet> findByCustomerId(String customerId);

}
