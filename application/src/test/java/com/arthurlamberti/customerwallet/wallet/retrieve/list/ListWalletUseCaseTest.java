package com.arthurlamberti.customerwallet.wallet.retrieve.list;

import com.arthurlamberti.customerwallet.UseCaseTest;
import com.arthurlamberti.customerwallet.application.wallet.retrieve.list.DefaultListWalletUseCase;
import com.arthurlamberti.customerwallet.application.wallet.retrieve.list.ListWalletOutput;
import com.arthurlamberti.customerwallet.domain.Fixture;
import com.arthurlamberti.customerwallet.domain.wallet.Wallet;
import com.arthurlamberti.customerwallet.domain.wallet.WalletGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ListWalletUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultListWalletUseCase useCase;

    @Mock
    private WalletGateway walletGateway;

    @Test
    public void givenAValidCommand_whenCallsListWalletAndWasNotEmpty_shouldReturnIt() {
        final var listWallets = List.of(
                Fixture.WalletFixture.validWallet(),
                Fixture.WalletFixture.validWallet(),
                Fixture.WalletFixture.validWallet()
        );
        final var expectedItems = listWallets.stream()
                .map(ListWalletOutput::from)
                .toList();

        when(walletGateway.findAll()).thenReturn(listWallets);
        final var actualOutput = useCase.execute();
        assertNotNull(actualOutput);
        assertTrue(expectedItems.containsAll(actualOutput));
    }

    @Test
    public void givenAnEmptyList_whenCallsListWallet_shouldReturnEmpty() {
        final var listWallets = List.<Wallet>of();

        when(walletGateway.findAll()).thenReturn(listWallets);

        final var actualOutput = useCase.execute();
        assertNotNull(actualOutput);
        assertTrue(actualOutput.isEmpty());
    }

    @Test
    public void givenGatewayError_whenCallsListWallet_shouldReturnAnError() {
        doThrow(new IllegalStateException("Gateway error")).when(walletGateway).findAll();

        assertThrows(IllegalStateException.class, () -> useCase.execute());
        verify(walletGateway, times(1)).findAll();
    }
}
