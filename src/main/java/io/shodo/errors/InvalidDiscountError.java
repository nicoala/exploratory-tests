package io.shodo.errors;

public class InvalidDiscountError extends RuntimeException {

    public InvalidDiscountError() {
        super("A discount code or application period are mandatory to create a discount.");
    }
}
