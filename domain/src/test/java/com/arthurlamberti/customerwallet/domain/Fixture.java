package com.arthurlamberti.customerwallet.domain;

import com.arthurlamberti.customerwallet.domain.utils.IdUtils;
import com.arthurlamberti.customerwallet.domain.wallet.Wallet;
import com.github.javafaker.Faker;

public final class Fixture {

    private static final Faker FAKER = new Faker();
    public static class WalletFixture {

        public static Wallet validWallet() {
            return Wallet.newWallet(
                    0.0,
                    uuid()
            );
        }
    }

    public static String uuid() {
        return IdUtils.uuid();
    }

    public static double positiveNumber() {
        return FAKER.number().randomDouble(2, 1, Integer.MAX_VALUE);
    }

    public static Double negativeNumber() {
        return FAKER.number().randomDouble(2, Integer.MIN_VALUE, -1);
    }
}
