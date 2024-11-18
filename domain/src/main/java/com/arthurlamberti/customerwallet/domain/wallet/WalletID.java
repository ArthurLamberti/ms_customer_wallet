package com.arthurlamberti.customerwallet.domain.wallet;

import com.arthurlamberti.customerwallet.domain.Identifier;
import com.arthurlamberti.customerwallet.domain.utils.IdUtils;

import static java.util.Objects.requireNonNull;

public class WalletID extends Identifier {

    private final String uuid;

    public WalletID(final String uuid) {
        requireNonNull(uuid);
        this.uuid = uuid;
    }

    public static WalletID from(final String anId) {
        return new WalletID(anId.toLowerCase());
    }

    public static WalletID unique(){
        return WalletID.from(IdUtils.uuid());
    }

    @Override
    public String getValue() {
        return this.uuid;
    }
}
