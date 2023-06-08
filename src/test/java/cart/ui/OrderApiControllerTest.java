package cart.ui;

import static org.hamcrest.Matchers.hasSize;

import cart.application.CouponService;
import cart.application.ProductService;
import cart.dto.OrderProductRequest;
import cart.dto.OrderRequest;
import cart.dto.ProductRequest;
import cart.dto.SaveCouponRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderApiControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private ProductService productService;

  @Autowired
  private CouponService couponService;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  @Test
  @DisplayName("주문을 할 수 있다.")
  void order() {
    final Long couponId = couponService.createCoupon(new SaveCouponRequest("1000원 할인", 15000, 1000));
    final Long chickenId = productService.createProduct(new ProductRequest("치킨", 15000, "chicken"));
    final Long pizzaId = productService.createProduct(new ProductRequest("피자", 20000, "pizza"));

    final OrderRequest orderRequest = new OrderRequest(
        List.of(new OrderProductRequest(chickenId, 2),
            new OrderProductRequest(pizzaId, 1)),
        50000, 3000, "address", couponId);

    RestAssured
        .given()
        .contentType(ContentType.JSON)
        .header("Authorization", "Basic " + Base64Coder.encodeString("a@a.com" + ":" + "1234"))
        .body(orderRequest)
        .when()
        .post("/orders")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("products", hasSize(2));
  }
}
