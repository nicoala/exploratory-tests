package io.shodo.domain;

import io.shodo.errors.InvalidDiscountCodeFormatError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


class DiscountCodeTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "PRO", "P", "SUPER_VERY_LONG_PROMO_CODE"})
    void should_not_create_a_discount_code_when_value_does_not_fit_the_format(String discountCode) {
        Throwable throwable = catchThrowable(() -> new DiscountCode(discountCode));

        assertThat(throwable).isInstanceOf(InvalidDiscountCodeFormatError.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"COOL", "TWENTY_TWO_OFF_EVERYTHING"})
    void valid_discount_code(String value) {
        DiscountCode discountCode = new DiscountCode(value);

        assertThat(discountCode).isNotNull();
    }
}