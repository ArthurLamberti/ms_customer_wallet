package com.arthurlamberti.customerwallet.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error error);
    ValidationHandler append(ValidationHandler handler);

    <T> T validate(Validation<T> aValidation);
    List<Error> getErrors();
    default boolean hasError() {
        return getErrors() != null && !getErrors().isEmpty();
    }

}
