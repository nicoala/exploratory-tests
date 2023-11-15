package io.shodo.domain;

import java.util.Date;
import java.util.Objects;

public class Cart {

    private final double price;
    private final DiscountCode customerEnteredCode;
    private final Date orderDate;

    public Cart(double price, Date orderDate) {
        this.price = price;
        this.orderDate = orderDate;
        this.customerEnteredCode = null;
    }

    public Cart(double price, DiscountCode customerEnteredCode, Date orderDate) {
        this.price = price;
        this.customerEnteredCode = customerEnteredCode;
        this.orderDate = orderDate;
    }

    public double getPrice() {
        return price;
    }

    public DiscountCode getCustomerEnteredCode() {
        return customerEnteredCode;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Double.compare(cart.price, price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
