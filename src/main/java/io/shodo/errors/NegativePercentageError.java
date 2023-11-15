package io.shodo.errors;

public class NegativePercentageError extends RuntimeException {

    public NegativePercentageError() {
        super("Impossible to create a negative discount percentage");
    }
}
