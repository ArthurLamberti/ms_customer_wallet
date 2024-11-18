package com.arthurlamberti.customerwallet.domain.wallet;

import com.arthurlamberti.customerwallet.domain.Fixture;
import com.arthurlamberti.customerwallet.domain.exceptions.NotificationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WalletTest {

    @Test
    public void givenAValidWallet_whenCallNewWallet_thenShouldReturnIt() {
        final var expectedBalance = 0.0;
        final var expectedClientId = Fixture.uuid();

        final var actualWallet = Wallet.newWallet(expectedBalance, expectedClientId);
        assertNotNull(actualWallet);
        assertNotNull(actualWallet.getId());
        assertEquals(expectedBalance, actualWallet.getBalance());
        assertEquals(expectedClientId, actualWallet.getCustomerId());
    }

    @Test
    public void givenAnInvalidNegativeBalance_whenCallsNewWallet_shouldReturnAnError() {
        final var expectedBalance = Fixture.negativeNumber();
        final var expectedClientId = Fixture.uuid();
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'balance' should be 0";

        final var actualError = assertThrows(NotificationException.class,
                () -> Wallet.newWallet(expectedBalance, expectedClientId));

        assertNotNull(actualError);
        assertEquals(expectedErrorCount, actualError.getErrors().size());
        assertEquals(expectedErrorMessage, actualError.getFirstError().get().message());
    }

    @Test
    public void givenAnInvalidPositiveBalance_whenCallsNewWallet_shouldReturnAnError() {
        final var expectedBalance = Fixture.positiveNumber();
        final var expectedClientId = Fixture.uuid();
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'balance' should be 0";

        final var actualError = assertThrows(NotificationException.class,
                () -> Wallet.newWallet(expectedBalance, expectedClientId));

        assertNotNull(actualError);
        assertEquals(expectedErrorCount, actualError.getErrors().size());
        assertEquals(expectedErrorMessage, actualError.getFirstError().get().message());
    }

    @Test
    public void givenAnInvalidNullClientId_whenCallsNewWallet_shouldReturnAnError() {
        final var expectedBalance = 0.0;
        final String expectedClientId = null;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'customerId' should not be null";

        final var actualError = assertThrows(NotificationException.class,
                () -> Wallet.newWallet(expectedBalance, expectedClientId));

        assertNotNull(actualError);
        assertEquals(expectedErrorCount, actualError.getErrors().size());
        assertEquals(expectedErrorMessage, actualError.getFirstError().get().message());
    }

    @Test
    public void givenAnInvalidEmptyClientId_whenCallsNewWallet_shouldReturnAnError() {
        final var expectedBalance = 0.0;
        final var expectedClientId = " ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'customerId' should not be empty";

        final var actualError = assertThrows(NotificationException.class,
                () -> Wallet.newWallet(expectedBalance, expectedClientId));

        assertNotNull(actualError);
        assertEquals(expectedErrorCount, actualError.getErrors().size());
        assertEquals(expectedErrorMessage, actualError.getFirstError().get().message());
    }
}