package com.arthurlamberti.customerwallet.application.wallet.retrieve.get;

import com.arthurlamberti.customerwallet.IntegrationTest;
import com.arthurlamberti.customerwallet.domain.Fixture;
import com.arthurlamberti.customerwallet.domain.wallet.WalletGateway;
import com.arthurlamberti.customerwallet.infrastructure.wallet.persistence.WalletJpaEntity;
import com.arthurlamberti.customerwallet.infrastructure.wallet.persistence.WalletRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.Assert.assertEquals;


@IntegrationTest
public class GetWalletUseCaseIT {

    @Autowired
    private GetWalletUseCase useCase;

    @Autowired
    private WalletRepository walletRepository;

    @SpyBean
    private WalletGateway walletGateway;

    @Test
    public void givenAValidId_whenCallsGetWallet_thenShouldReturnIt() {
        final var expectedWallet = Fixture.WalletFixture.validWallet();
        walletRepository.save(WalletJpaEntity.from(expectedWallet));

        assertEquals(1, walletRepository.count());

        final var actualResult = useCase.execute(expectedWallet.getId().getValue());
        assertEquals(expectedWallet.getCustomerId(), actualResult.customerId());
        assertEquals(expectedWallet.getBalance(), actualResult.balance());
    }

}
