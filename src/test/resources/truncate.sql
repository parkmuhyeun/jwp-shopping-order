set FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE product;
TRUNCATE TABLE member;
TRUNCATE TABLE cart_item;
TRUNCATE TABLE orders;
TRUNCATE TABLE order_item;
TRUNCATE TABLE coupon;

set FOREIGN_KEY_CHECKS = 1;