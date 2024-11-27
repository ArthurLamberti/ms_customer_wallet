package com.arthurlamberti.customerwallet.infrastructure.wallet;

import com.arthurlamberti.customerwallet.MySQLGatewayTest;
import com.arthurlamberti.customerwallet.domain.Fixture;
import com.arthurlamberti.customerwallet.domain.wallet.Wallet;
import com.arthurlamberti.customerwallet.domain.wallet.WalletGateway;
import com.arthurlamberti.customerwallet.infrastructure.wallet.persistence.WalletJpaEntity;
import com.arthurlamberti.customerwallet.infrastructure.wallet.persistence.WalletRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@MySQLGatewayTest
public class WalletMySQLGatewayTest {

    @Autowired
    private WalletGateway walletGateway;

    @Autowired
    private WalletRepository walletRepository;

    @Test
    public void givenAValidWallet_whenCallsCreateWallet_shouldPersistIt() {
        final var expectedBalance = 0.0;
        final var expectedClientId = Fixture.uuid();

        final var aWallet = Wallet.newWallet(expectedBalance, expectedClientId);
        final var expectedId = aWallet.getId();

        assertEquals(0, walletRepository.count());
        final var actualWallet = walletGateway.create(aWallet);

        assertEquals(1, walletRepository.count());

        assertEquals(expectedBalance, actualWallet.getBalance());
        assertEquals(expectedClientId, actualWallet.getCustomerId());

        final var persistedWallet = walletRepository.findById(expectedId.getValue()).get();
        assertEquals(expectedBalance, persistedWallet.getBalance());
        assertEquals(expectedClientId, persistedWallet.getCustomerId());
    }

    @Test
    public void givenAPrePersistedWallet_whenCallsFindAll_shouldRetualAll() {
        final var walletList = List.<Wallet>of(
                Fixture.WalletFixture.validWallet(),
                Fixture.WalletFixture.validWallet(),
                Fixture.WalletFixture.validWallet()
        );
        final var walletJpaList = walletList.stream()
                .map(WalletJpaEntity::from)
                .toList();

        assertEquals(0, walletRepository.count());
        walletRepository.saveAllAndFlush(walletJpaList);
        assertEquals(3, walletRepository.count());

        final var actualResult = walletGateway.findAll();
        actualResult.forEach(res -> {
            var exist = walletList.stream()
                    .filter(w -> w.getId().getValue().equals(res.getId().getValue()))
                    .toList()
                    .get(0);
            assertEquals(res.getId().getValue(), exist.getId().getValue());
            assertEquals(res.getBalance(), exist.getBalance());
            assertEquals(res.getCustomerId(), exist.getCustomerId());
        });

        final var persistedData = walletRepository.findAll();
        persistedData.forEach(res -> {
            var exist = walletJpaList.stream()
                    .filter(w -> w.getId().equals(res.getId()))
                    .toList()
                    .get(0);
            assertEquals(res.getId(), exist.getId());
            assertEquals(res.getBalance(), exist.getBalance());
            assertEquals(res.getCustomerId(), exist.getCustomerId());
        });
    }

    @Test
    public void givenAPrePersistedWallet_whenCallsFindByCusterId_shouldRetualAll() {
        final var walletList = List.<Wallet>of(
                Fixture.WalletFixture.validWallet(),
                Fixture.WalletFixture.validWallet(),
                Fixture.WalletFixture.validWallet()
        );
        final var walletJpaList = walletList.stream()
                .map(WalletJpaEntity::from)
                .toList();

        assertEquals(0, walletRepository.count());
        walletRepository.saveAllAndFlush(walletJpaList);
        assertEquals(3, walletRepository.count());
        final var wallet0 = walletJpaList.get(0);

        final var actualResult = walletGateway.findByCustomerId(walletJpaList.get(0).getCustomerId()).get();
        assertEquals(actualResult.getId().getValue(), wallet0.getId());
        assertEquals(actualResult.getCustomerId(), wallet0.getCustomerId());
        assertEquals(actualResult.getBalance(), wallet0.getBalance());

        final var persistedWallet = walletRepository.findByCustomerId(walletList.get(0).getCustomerId()).get();
        assertEquals(persistedWallet.getId(), wallet0.getId());
        assertEquals(persistedWallet.getCustomerId(), wallet0.getCustomerId());
        assertEquals(persistedWallet.getBalance(), wallet0.getBalance());
    }

}