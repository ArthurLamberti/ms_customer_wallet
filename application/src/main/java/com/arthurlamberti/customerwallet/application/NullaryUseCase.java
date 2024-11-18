package com.arthurlamberti.customerwallet.application;

public abstract class NullaryUseCase<OUT> {

    public NullaryUseCase() {

    }

    public abstract OUT execute();

}
