package com.arthurlamberti.customerwallet.domain.validation.handler;

import com.arthurlamberti.customerwallet.domain.exceptions.DomainException;
import com.arthurlamberti.customerwallet.domain.validation.Error;
import com.arthurlamberti.customerwallet.domain.validation.Validation;
import com.arthurlamberti.customerwallet.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {

    private final List<Error> errors;

    private Notification(List<Error> errors) {
        this.errors = errors;
    }

    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    @Override
    public ValidationHandler append(final Error anError) {
        this.errors.add(anError);
        return this;
    }

    @Override
    public ValidationHandler append(ValidationHandler aHandler) {
        this.errors.addAll(aHandler.getErrors());
        return this;
    }

    @Override
    public <T> T validate(Validation<T> aValidation) {
        try {
            return aValidation.validate();
        } catch (final DomainException e) {
            this.errors.addAll(e.getErrors());
        } catch (final Throwable t) {
            this.errors.add(new Error(t.getMessage()));
        }
        return null;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }
}
