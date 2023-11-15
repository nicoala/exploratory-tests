package io.shodo.domain;

import io.shodo.errors.InvalidDiscountError;

public abstract class Discount {

    protected DiscountCode discountCode;
    protected ApplicationPeriod applicationPeriod;

    public Discount(DiscountCode discountCode, ApplicationPeriod applicationPeriod) {
        if (discountCode == null || applicationPeriod == null) {
            throw new InvalidDiscountError();
        }
        this.discountCode = discountCode;
        this.applicationPeriod = applicationPeriod;
    }

    public abstract PricedCart apply(Cart cart);

    boolean isApplicable(Cart cart) {
        return applicationPeriod.isOngoing(cart.getOrderDate())
                && discountCode.equals(cart.getCustomerEnteredCode());
    }

}