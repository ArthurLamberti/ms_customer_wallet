package com.arthurlamberti.customerwallet.infrastructure.adapters.kafka.consumers;

import com.arthurlamberti.customerwallet.IntegrationTest;
import com.arthurlamberti.customerwallet.domain.Fixture;
import com.arthurlamberti.customerwallet.domain.enums.StatusTransaction;
import com.arthurlamberti.customerwallet.domain.enums.TransactionType;
import com.arthurlamberti.customerwallet.domain.exceptions.NotFoundException;
import com.arthurlamberti.customerwallet.domain.validation.Error;
import com.arthurlamberti.customerwallet.infrastructure.adapters.kafka.models.UpdateWalletRequest;
import com.arthurlamberti.customerwallet.infrastructure.adapters.kafka.models.UpdateWalletResponse;
import com.arthurlamberti.customerwallet.infrastructure.adapters.kafka.producer.WalletProducer;
import com.arthurlamberti.customerwallet.infrastructure.config.json.Json;
import com.arthurlamberti.customerwallet.infrastructure.wallet.persistence.WalletJpaEntity;
import com.arthurlamberti.customerwallet.infrastructure.wallet.persistence.WalletRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@IntegrationTest
public class WalletConsumerTest {

    @InjectMocks
    private WalletConsumer walletConsumer;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private WalletProducer walletProducer;

    @Captor
    private ArgumentCaptor<UpdateWalletResponse> responseCaptor;

    @Test
    public void givenAValidBuyMessage_whenCallsListen_shouldPostAMessage() {
        final var expectedWallet = Fixture.WalletFixture.validWallet();
        final var req = new UpdateWalletRequest(expectedWallet.getCustomerId(), Fixture.positiveNumber(), TransactionType.BUY, Fixture.uuid());

        when(walletRepository.findByCustomerId(any())).thenReturn(Optional.of(WalletJpaEntity.from(expectedWallet)));
        doNothing().when(walletProducer).sendMessage(any(),any(), responseCaptor.capture());

        walletConsumer.listen(Json.writeValueAsString(req));

        final var response = responseCaptor.getValue();
        assertNotNull(response);
        assertEquals(StatusTransaction.OK, response.status());
        assertEquals(req.transactionId(), response.transactionId());
        assertEquals(req.type(), response.type());

        verify(walletRepository, times(1)).findByCustomerId(any());
        verify(walletRepository, times(1)).save(any());
        verify(walletProducer, times(1)).sendMessage(any(),any(), any());
    }


    @Test
    public void givenAValidSellMessage_whenCallsListen_shouldPostAMessage() {
        final var expectedWallet = Fixture.WalletFixture.validWallet();
        final var req = new UpdateWalletRequest(expectedWallet.getCustomerId(), Fixture.positiveNumber(), TransactionType.SELL, Fixture.uuid());

        when(walletRepository.findByCustomerId(any())).thenReturn(Optional.of(WalletJpaEntity.from(expectedWallet)));
        doNothing().when(walletProducer).sendMessage(any(),any(), responseCaptor.capture());

        walletConsumer.listen(Json.writeValueAsString(req));

        final var response = responseCaptor.getValue();
        assertNotNull(response);
        assertEquals(StatusTransaction.OK, response.status());
        assertEquals(req.transactionId(), response.transactionId());
        assertEquals(req.type(), response.type());

        verify(walletRepository, times(1)).findByCustomerId(any());
        verify(walletRepository, times(1)).save(any());
        verify(walletProducer, times(1)).sendMessage(any(),any(), any());
    }


    @Test
    public void givenAInvalidCustomeId_whenCallsListen_shouldReturnAnError() {
        final var expectedWallet = Fixture.WalletFixture.validWallet();
        final var req = new UpdateWalletRequest(expectedWallet.getCustomerId(), Fixture.positiveNumber(), TransactionType.BUY, Fixture.uuid());

        doNothing().when(walletProducer).sendMessage(any(),any(), responseCaptor.capture());
        when(walletRepository.findByCustomerId(any())).thenThrow(NotFoundException.with(new Error("Wallet not found to customer %s".formatted(expectedWallet.getCustomerId()))));

        walletConsumer.listen(Json.writeValueAsString(req));

        final var response = responseCaptor.getValue();
        assertNotNull(response);
        assertEquals(StatusTransaction.NOK, response.status());
        assertEquals(req.transactionId(), response.transactionId());
        assertEquals(null, response.type());

        verify(walletRepository, times(1)).findByCustomerId(any());
        verify(walletRepository, never()).save(any());
        verify(walletProducer, times(1)).sendMessage(any(),any(), any());
    }

}