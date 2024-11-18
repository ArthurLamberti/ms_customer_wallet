package com.arthurlamberti.customerwallet.application.wallet.create;

public record CreateWalletCommand (
        Double balance,
        String clientId
){
    public static CreateWalletCommand with(
            final Double aBalance,
            final String aClientId
    ){
        return new CreateWalletCommand(aBalance, aClientId);
    }
}
