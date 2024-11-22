package com.arthurlamberti.customerwallet.wallet.create;

import com.arthurlamberti.customerwallet.UseCaseTest;
import com.arthurlamberti.customerwallet.application.wallet.create.CreateWalletCommand;
import com.arthurlamberti.customerwallet.application.wallet.create.DefaultCreateWalletUseCase;
import com.arthurlamberti.customerwallet.domain.Fixture;
import com.arthurlamberti.customerwallet.domain.exceptions.NotificationException;
import com.arthurlamberti.customerwallet.domain.wallet.WalletGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateWalletUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateWalletUseCase useCase;

    @Mock
    private WalletGateway walletGateway;

    @Test
    public void givenAValidComman_whenCallsCreateWallet_shouldReturnIt() {
        final var expectedWallet = Fixture.WalletFixture.validWallet();
        final var aCommand = CreateWalletCommand.with(expectedWallet.getBalance(), expectedWallet.getCustomerId());

        when(walletGateway.create(any())).thenAnswer(returnsFirstArg());
        final var actualResult = useCase.execute(aCommand);

        assertNotNull(actualResult);
        verify(walletGateway, times(1)).create(
                argThat(wallet ->
                        Objects.nonNull(wallet.getId())
                                && Objects.equals(expectedWallet.getBalance(), wallet.getBalance())
                                && Objects.equals(expectedWallet.getCustomerId(), wallet.getCustomerId())
                )
        );
    }

    @Test
    public void givenAnInvalidCommand_whenCallsCreateWallet_shouldReturnAnError() {
        final var aCommand = CreateWalletCommand.with(Fixture.negativeNumber(), Fixture.uuid());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'balance' should be greater than or equals 0";

        final var actualError = assertThrows(NotificationException.class, () -> useCase.execute(aCommand));
        assertNotNull(actualError);
        assertEquals(expectedErrorCount, actualError.getErrors().size());
        assertEquals(expectedErrorMessage, actualError.getFirstError().get().message());
    }
}