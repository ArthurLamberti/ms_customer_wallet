package com.arthurlamberti.customerwallet.domain;

import com.arthurlamberti.customerwallet.domain.validation.ValidationHandler;

import java.util.Objects;


public abstract class Entity <ID extends Identifier> {
    protected final ID id;

    protected Entity(final ID id) {
        Objects.requireNonNull(id, "'ID' should not be null");
        this.id = id;
    }

    public abstract void validate(ValidationHandler handler);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(getId(), entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public ID getId() {
        return id;
    }
}
