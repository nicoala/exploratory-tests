package io.shodo.domain;

public class FlatDiscount extends Discount {

    private final double value;

    public FlatDiscount(DiscountCode discountCode, ApplicationPeriod applicationPeriod, double value) {
        super(discountCode, applicationPeriod);
        this.value = value;
    }

    @Override
    public PricedCart apply(Cart cart) {
        if (isApplicable(cart)) {
            double discountedPrice = cart.getPrice() - value;
            return new PricedCart(discountedPrice);
        }
        return new PricedCart(cart.getPrice());
    }
}
