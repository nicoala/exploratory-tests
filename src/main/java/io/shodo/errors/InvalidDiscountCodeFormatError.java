package io.shodo.errors;

public class InvalidDiscountCodeFormatError extends RuntimeException {
    public InvalidDiscountCodeFormatError() {
        super("Provided discount code does not match required format");
    }
}
