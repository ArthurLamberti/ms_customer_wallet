package com.arthurlamberti.customerwallet.application.wallet.create;

import com.arthurlamberti.customerwallet.IntegrationTest;
import com.arthurlamberti.customerwallet.domain.Fixture;
import com.arthurlamberti.customerwallet.domain.exceptions.NotificationException;
import com.arthurlamberti.customerwallet.domain.wallet.WalletGateway;
import com.arthurlamberti.customerwallet.infrastructure.wallet.persistence.WalletRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@IntegrationTest
public class CreateWalletUseCaseIT {

    @Autowired
    private CreateWalletUseCase useCase;

    @Autowired
    private WalletRepository walletRepository;

    @SpyBean
    private WalletGateway walletGateway;

    @Test
    public void givenValidCommand_whenCallsCreateWallet_thenShouldReturnIt() {
        final var expectedBalance = 0.0;
        final var expectedCustomerId = Fixture.uuid();

        assertEquals(0, walletRepository.count());

        final var aCommand = CreateWalletCommand.with(expectedBalance, expectedCustomerId);
        final var actualResult = useCase.execute(aCommand);

        assertNotNull(actualResult);
        assertNotNull(actualResult.id());
        assertEquals(1, walletRepository.count());

        final var actualWallet = walletRepository.findById(actualResult.id()).get();
        assertEquals(expectedBalance, actualWallet.getBalance());
        assertEquals(expectedCustomerId, actualWallet.getCustomerId());
        verify(walletGateway, times(1)).create(any());
    }

    @Test
    public void givenAnInvalidNegativeBalance_whenCallsCreateWallet_thenShouldReturnAnException() {
        final var expectedBalance = Fixture.negativeNumber();
        final var expectedCustomerId = Fixture.uuid();
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'balance' should be greater than or equals 0";

        assertEquals(0, walletRepository.count());

        final var aCommand = CreateWalletCommand.with(expectedBalance, expectedCustomerId);
        final var actualException = assertThrows(NotificationException.class, () -> useCase.execute(aCommand));
        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getFirstError().get().message());
    }

    @Test
    public void givenAnInvalidNullCustomerId_whenCallsCreateWallet_thenShouldReturnAnException() {
        final var expectedBalance = 0.0;
        final String expectedCustomerId = null;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'customerId' should not be null";

        assertEquals(0, walletRepository.count());

        final var aCommand = CreateWalletCommand.with(expectedBalance, expectedCustomerId);
        final var actualException = assertThrows(NotificationException.class, () -> useCase.execute(aCommand));
        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getFirstError().get().message());
    }

    @Test
    public void givenAnInvalidEmptyCustomerId_whenCallsCreateWallet_thenShouldReturnAnException() {
        final var expectedBalance = 0.0;
        final var expectedCustomerId = " ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'customerId' should not be empty";

        assertEquals(0, walletRepository.count());

        final var aCommand = CreateWalletCommand.with(expectedBalance, expectedCustomerId);
        final var actualException = assertThrows(NotificationException.class, () -> useCase.execute(aCommand));
        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getFirstError().get().message());
    }
}
