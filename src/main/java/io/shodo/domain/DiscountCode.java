package io.shodo.domain;

import io.shodo.errors.InvalidDiscountCodeFormatError;

import java.util.Objects;

public class DiscountCode {

    private final String value;

    public DiscountCode(String value) {
        if (isNullOrEmpty(value) || isTooShort(value) || isTooLong(value)) {
            throw new InvalidDiscountCodeFormatError();
        }
        this.value = value;
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    private boolean isTooShort(String value) {
        return value.length() < 4;
    }

    private boolean isTooLong(String value) {
        return value.length() > 25;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountCode that = (DiscountCode) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
