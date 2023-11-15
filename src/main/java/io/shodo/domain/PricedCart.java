package io.shodo.domain;

import java.util.Date;

public class PricedCart extends Cart {
    public PricedCart(double price) {
        super(price, new Date());
    }
}
