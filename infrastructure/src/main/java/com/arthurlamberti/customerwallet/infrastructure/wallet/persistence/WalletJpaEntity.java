package com.arthurlamberti.customerwallet.infrastructure.wallet.persistence;


import com.arthurlamberti.customerwallet.domain.wallet.Wallet;
import com.arthurlamberti.customerwallet.domain.wallet.WalletID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "wallets")
@Table(name = "wallets")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class WalletJpaEntity {

    @Id
    private String id;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    public static WalletJpaEntity from(final Wallet aWallet){
        return new WalletJpaEntity(
                aWallet.getId().getValue(),
                aWallet.getBalance(),
                aWallet.getCustomerId()
        );
    }

    public Wallet toAggregate(){
        return Wallet.with(
                WalletID.from(this.id),
                this.balance,
                this.customerId
        );
    }
}
