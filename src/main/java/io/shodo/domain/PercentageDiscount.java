package io.shodo.domain;

import io.shodo.errors.NegativePercentageError;

public class PercentageDiscount extends Discount {

    private final double value;

    public PercentageDiscount(DiscountCode discountCode, ApplicationPeriod applicationPeriod, double value) {
        super(discountCode, applicationPeriod);
        if (value < 0.0) {
            throw new NegativePercentageError();
        }
        this.value = value;
    }

    @Override
    public PricedCart apply(Cart cart) {
        if (isApplicable(cart)) {
            double discountedPrice = cart.getPrice() - cart.getPrice() * value;
            return new PricedCart(discountedPrice);
        }
        return new PricedCart(cart.getPrice());
    }
}
