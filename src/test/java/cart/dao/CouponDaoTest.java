package cart.dao;

import cart.domain.Coupon;
import cart.factory.CouponFactory;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class CouponDaoTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private CouponDao couponDao;

  @BeforeEach
  void setUp() {
    couponDao = new CouponDao(jdbcTemplate);
  }

  @Test
  @DisplayName("쿠폰을 생성하고 찾을 수 있다.")
  void createAndFind() {
    //given
    final Long productId = couponDao.createCoupon(CouponFactory.createCoupon("1000원 할인", 1000, 10000));

    //when
    final Optional<Coupon> coupon = couponDao.findById(productId);

    //then
    Assertions.assertThat(coupon).isPresent();
  }
}
