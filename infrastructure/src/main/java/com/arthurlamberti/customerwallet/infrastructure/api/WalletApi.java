package com.arthurlamberti.customerwallet.infrastructure.api;

import com.arthurlamberti.customerwallet.infrastructure.wallet.models.CreateWalletRequest;
import com.arthurlamberti.customerwallet.infrastructure.wallet.models.GetWalletResponse;
import com.arthurlamberti.customerwallet.infrastructure.wallet.models.ListWalletResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "wallets")
@Tag(name = "Wallets")
public interface WalletApi {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new Wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was throw"),
            @ApiResponse(responseCode = "500", description = "Internal error server"),
    })
    ResponseEntity<?> createWallet(@RequestBody CreateWalletRequest input);

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "List all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallet list successfully"),
            @ApiResponse(responseCode = "500", description = "Internal error server"),
    })
    List<ListWalletResponse> listCustomer();

    @GetMapping(
            value = "/{customerId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "List all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get wallet successfully"),
            @ApiResponse(responseCode = "500", description = "Internal error server"),
    })
    GetWalletResponse getWalletByCustomerId(@PathVariable String customerId);
}
