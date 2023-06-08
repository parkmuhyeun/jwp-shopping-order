package cart.factory;

import cart.domain.Amount;
import cart.domain.Coupon;

public class CouponFactory {

  public static Coupon createCoupon(final long id, final String name, final int discountAmount, final int minAmount) {
    return new Coupon(id, name, discountAmount, minAmount);
  }

  public static Coupon createCoupon(final String name, final int discountAmount, final int minAmount) {
    return new Coupon(name, discountAmount, minAmount);
  }
}
