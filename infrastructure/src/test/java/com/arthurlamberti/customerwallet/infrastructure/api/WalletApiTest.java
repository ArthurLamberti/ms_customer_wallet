package com.arthurlamberti.customerwallet.infrastructure.api;

import com.arthurlamberti.customerwallet.ControllerTest;
import com.arthurlamberti.customerwallet.application.wallet.create.CreateWalletOutput;
import com.arthurlamberti.customerwallet.application.wallet.create.CreateWalletUseCase;
import com.arthurlamberti.customerwallet.application.wallet.retrieve.get.GetWalletOutput;
import com.arthurlamberti.customerwallet.application.wallet.retrieve.get.GetWalletUseCase;
import com.arthurlamberti.customerwallet.application.wallet.retrieve.list.ListWalletOutput;
import com.arthurlamberti.customerwallet.application.wallet.retrieve.list.ListWalletUseCase;
import com.arthurlamberti.customerwallet.domain.Fixture;
import com.arthurlamberti.customerwallet.domain.exceptions.NotFoundException;
import com.arthurlamberti.customerwallet.domain.exceptions.NotificationException;
import com.arthurlamberti.customerwallet.domain.validation.Error;
import com.arthurlamberti.customerwallet.infrastructure.wallet.models.CreateWalletRequest;
import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest
public class WalletApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateWalletUseCase createWalletUseCase;

    @MockBean
    private ListWalletUseCase listWalletUseCase;

    @MockBean
    private GetWalletUseCase getWalletUseCase;

    @Test
    public void givenAValidCommand_whenCallsCreateWallet_shouldReturnWalletId() throws Exception {
        final var expectedBalance = 0.0;
        final var expectedCustomerID = Fixture.uuid();
        final var expectedId = Fixture.uuid();

        final var anInput = new CreateWalletRequest(expectedBalance, expectedCustomerID);
        when(createWalletUseCase.execute(any())).thenReturn(CreateWalletOutput.from(expectedId));

        final var aRequest = post("/wallets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(anInput));
        final var response = this.mockMvc.perform(aRequest).andDo(print());

        response.andExpect(status().isCreated())
                .andExpect(header().string("Location", "/wallets/" + expectedId))
                .andExpect(jsonPath("$.id", equalTo(expectedId)));
    }

    @Test
    public void givenInvalidCommand_whenCallsCreateWallet_shouldReturnAnError() throws Exception {
        final var expectedBalance = 1.0;
        final var expectedCustomerID = Fixture.uuid();
        final var expectedErrorMessage = "Customer not found";

        final var anInput = new CreateWalletRequest(expectedBalance, expectedCustomerID);
        when(createWalletUseCase.execute(any())).thenThrow(NotFoundException.with(new Error(expectedErrorMessage)));

        final var aRequest = post("/wallets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(anInput));
        final var response = this.mockMvc.perform(aRequest).andDo(print());

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)))
                .andExpect(jsonPath("$.errors.size()", equalTo(1)));
    }

    @Test
    public void givenAValidId_whenCallsGetWallet_shouldReturnWallet() throws Exception {
        final var expectedWallet = Fixture.WalletFixture.validWallet();
        when(getWalletUseCase.execute(expectedWallet.getCustomerId())).thenReturn(GetWalletOutput.from(expectedWallet));

        final var aRequest = get("/wallets/" + expectedWallet.getCustomerId());
        final var response = this.mockMvc.perform(aRequest).andDo(print());

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.balance", equalTo(expectedWallet.getBalance())))
                .andExpect(jsonPath("$.customer_id", equalTo(expectedWallet.getCustomerId())));
    }

    @Test
    public void givenAValidId_whenCallsListWallet_shouldReturnWallet() throws Exception {
        final var expectedWallet = Fixture.WalletFixture.validWallet();
        when(listWalletUseCase.execute()).thenReturn(List.of(ListWalletOutput.from(expectedWallet)));

        final var aRequest = get("/wallets/");
        final var response = this.mockMvc.perform(aRequest).andDo(print());

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].balance", equalTo(expectedWallet.getBalance())))
                .andExpect(jsonPath("$[0].customer_id", equalTo(expectedWallet.getCustomerId())));
    }
}
