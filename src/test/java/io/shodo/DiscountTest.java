package io.shodo;

import io.shodo.domain.*;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.DoubleRange;
import net.jqwik.time.api.constraints.DateRange;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountTest {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Nested
    class FlatDiscountTest {

        @Test
        void should_not_apply_discount_if_order_is_made_before_application_period() {
            // Arrange
            var flatDiscount = new FlatDiscount(discountCode("PROMO"), applicationPeriodDuring2023(), 10.0);
            Date orderDate = date("2022-12-25");
            Cart cart = new Cart(100.0, discountCode("PROMO"), orderDate);

            // Act
            PricedCart pricedCart = flatDiscount.apply(cart);

            // Assert
            PricedCart expectedCart = new PricedCart(100.0);
            assertThat(pricedCart).isEqualTo(expectedCart);
        }

        @Test
        void should_not_apply_a_flat_discount_if_order_is_made_without_discount_code() {
            // Arrange
            var flatDiscount = new FlatDiscount(discountCode("PROMO"), applicationPeriodDuring2023(), 10.0);
            Date orderDate = date("2023-11-01");
            Cart cart = new Cart(100.0, null, orderDate);

            // Act
            PricedCart pricedCart = flatDiscount.apply(cart);

            // Assert
            PricedCart expectedCart = new PricedCart(100.0);
            assertThat(pricedCart).isEqualTo(expectedCart);
        }

        @Test
        void should_not_apply_a_flat_discount_if_order_is_made_with_an_unknown_discount_code() {
            // Arrange
            var flatDiscount = new FlatDiscount(discountCode("PROMO"), applicationPeriodDuring2023(), 10.0);
            Date orderDate = date("2023-11-01");
            Cart cart = new Cart(40.0, discountCode("UNKNOWN"), orderDate);

            // Act
            PricedCart pricedCart = flatDiscount.apply(cart);

            // Assert
            PricedCart expectedCart = new PricedCart(40.0);
            assertThat(pricedCart).isEqualTo(expectedCart);
        }

        @Test
        void should_apply_a_flat_discount_to_a_cart_when_order_is_made_during_application_period() {
            // Arrange
            var flatDiscount = new FlatDiscount(discountCode("PROMO"), applicationPeriodDuring2023(), 10.0);
            Date orderDate = date("2023-11-01");
            Cart cart = new Cart(100.0, discountCode("PROMO"), orderDate);

            // Act
            PricedCart pricedCart = flatDiscount.apply(cart);

            // Assert
            PricedCart expectedCart = new PricedCart(90.0);
            assertThat(pricedCart).isEqualTo(expectedCart);
        }

    }

    @Nested
    class PercentageDiscountTest {
        @Test
        void should_apply_50_percent_off_cart_with_percentage_discount() {
            // Arrange
            var percentageDiscount = new PercentageDiscount(discountCode("50OFF"), applicationPeriodDuring2023(), 0.5);
            Date orderDate = date("2023-11-01");
            Cart cart = new Cart(100.0, discountCode("50OFF"), orderDate);

            // Act
            PricedCart pricedCart = percentageDiscount.apply(cart);

            // Assert
            PricedCart expectedCart = new PricedCart(50.0);
            assertThat(pricedCart).isEqualTo(expectedCart);
        }
    }

    @Property
    void a_promo_should_work_for_an_entire_month(
            @ForAll @DateRange(min = "2023-11-01", max = "2023-11-30") LocalDate date
    ) {
        // Arrange
        DiscountCode validDiscountCode = discountCode("BLACK_FRIDAY");
        var flatDiscount = new FlatDiscount(
                validDiscountCode,
                applicationPeriodDuringNovember(),
                20.0
        );
        Cart cart = new Cart(100.0, validDiscountCode, asDate(date));

        // Act
        PricedCart pricedCart = flatDiscount.apply(cart);

        // Assert
        PricedCart expectedCart = new PricedCart(80.0);
        assertThat(pricedCart).isEqualTo(expectedCart);
    }

    @Property
    void a_discount_should_not_result_in_a_cart_with_a_negative_price(
            @ForAll @DoubleRange(min = 5.0, max = 200.0) double discountValue
    ) {
        // Arrange
        DiscountCode validDiscountCode = discountCode("BLACK_FRIDAY");
        var flatDiscount = new FlatDiscount(
                validDiscountCode,
                applicationPeriodDuringNovember(),
                discountValue
        );
        Cart cart = new Cart(100.0, validDiscountCode, date("2023-11-23"));

        // Act
        PricedCart pricedCart = flatDiscount.apply(cart);

        // Assert
        assertThat(pricedCart.getPrice()).isGreaterThanOrEqualTo(0.0);
    }

    private static Date asDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private static ApplicationPeriod applicationPeriodDuring2023() {
        return new ApplicationPeriod(date("2023-01-01"), date("2023-12-31"));
    }

    private static ApplicationPeriod applicationPeriodDuringNovember() {
        return new ApplicationPeriod(date("2023-11-01"), date("2023-11-30"));
    }

    private static DiscountCode discountCode(String value) {
        return new DiscountCode(value);
    }

    private static Date date(String date) {
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

}