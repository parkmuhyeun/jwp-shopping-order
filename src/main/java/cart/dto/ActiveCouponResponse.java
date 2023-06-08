package cart.dto;

public class ActiveCouponResponse {

  private final long couponId;
  private final String couponName;
  private final int minAmount;

  public ActiveCouponResponse(long couponId, String couponName, int minAmount) {
    this.couponId = couponId;
    this.couponName = couponName;
    this.minAmount = minAmount;
  }

  public long getCouponId() {
    return couponId;
  }

  public String getCouponName() {
    return couponName;
  }

  public int getMinAmount() {
    return minAmount;
  }
}
