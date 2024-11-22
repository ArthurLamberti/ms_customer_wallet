package com.arthurlamberti.customerwallet.infrastructure.wallet.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetWalletResponse(
        @JsonProperty(value = "balance") Double balance,
        @JsonProperty(value = "customer_id") String customerId
){
}
