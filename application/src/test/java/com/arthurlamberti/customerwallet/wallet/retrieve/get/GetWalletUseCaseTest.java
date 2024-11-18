package com.arthurlamberti.customerwallet.wallet.retrieve.get;

import com.arthurlamberti.customerwallet.UseCaseTest;
import com.arthurlamberti.customerwallet.application.wallet.retrieve.get.DefaultGetWalletUseCase;
import com.arthurlamberti.customerwallet.domain.Fixture;
import com.arthurlamberti.customerwallet.domain.exceptions.NotFoundException;
import com.arthurlamberti.customerwallet.domain.wallet.WalletGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class GetWalletUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetWalletUseCase useCase;

    @Mock
    private WalletGateway walletGateway;

    @Test
    public void givenAValidCustomerId_whenCallsGetWallet_shouldReturnIt() {
        final var expectedWallet = Fixture.WalletFixture.validWallet();

        when(walletGateway.findByCustomerId(expectedWallet.getCustomerId()))
                .thenReturn(Optional.of(expectedWallet));

        final var actualResult = useCase.execute(expectedWallet.getCustomerId());
        assertEquals(expectedWallet.getId().getValue(), actualResult.id());
        assertEquals(expectedWallet.getCustomerId(), actualResult.customerId());
        assertEquals(expectedWallet.getBalance(), actualResult.balance());
    }

    @Test
    public void givenAValidCustomerId_whenCallsGetWalletAndDoesNotExists_shouldReturnAnException() {
        final var anId = Fixture.uuid();
        final var expectedErrorMessage = "Wallet not found to customerId %s".formatted(anId);

        when(walletGateway.findByCustomerId(any())).thenReturn(Optional.empty());
        final var actualException = assertThrows(NotFoundException.class, () -> useCase.execute(anId));
        assertEquals(expectedErrorMessage, actualException.getFirstError().get().message());
    }
}
