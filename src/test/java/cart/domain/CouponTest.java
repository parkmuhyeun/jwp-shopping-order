package cart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.exception.BusinessException;
import cart.factory.CouponFactory;
import org.junit.jupiter.api.Test;

class CouponTest {

  @Test
  void apply() {
    final Coupon coupon = CouponFactory.createCoupon("1000원 할인", 1000, 15000);
    final Amount inputAmount = new Amount(20000);
    final Amount expectedAmount = new Amount(19000);

    final Amount discountedAmount = coupon.apply(inputAmount);

    assertThat(discountedAmount).isEqualTo(expectedAmount);
  }

  @Test
  void applyWhenInputAmountIsLessThanMinAmount() {
    final Coupon coupon = CouponFactory.createCoupon("1000원 할인", 1000, 15000);
    final Amount inputAmount = new Amount(10000);

    assertThatThrownBy(() -> coupon.apply(inputAmount))
        .isInstanceOf(BusinessException.class);
  }
}
