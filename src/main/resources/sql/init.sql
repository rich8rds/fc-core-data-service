
CREATE DATABASE favourite_collections;
DROP DATABASE favourite_collections;


INSERT INTO t_role(name, description, is_disabled) VALUES('CUSTOMER', 'This is the role for customer', false);
INSERT INTO t_role(name, description, is_disabled) VALUES('ADMIN', 'This is the role for admin', false);

SELECT * FROM t_appuser;
SELECT * FROM t_token;


INSERT INTO t_code (name, is_system_defined, external_use) VALUES('Gender', 0, 1);
INSERT INTO t_code_value(code_value, position, description, code_id, is_active, is_mandatory)
VALUES('Male', 1, 'This is code value for Gender', 1, 1, 0);


INSERT INTO t_cart(id, total) VALUES(1, 5000);


INSERT INTO t_cart_item(cart_id, product_name, image_url, order_quantity, unit_price, sub_total)
VALUES(1, 'mango', 'mango_image_url', 3, 50.00, 150.00);

INSERT INTO t_cart_item(cart_id, product_name, image_url, order_quantity, unit_price, sub_total)
VALUES(1, 'tangerine', 'mango_image_url', 3, 50.00, 150.00);

INSERT INTO t_cart_item(cart_id, product_name, image_url, order_quantity, unit_price, sub_total)
VALUES(1, 'guava', 'mango_image_url', 3, 50.00, 150.00);

INSERT INTO t_cart_item(cart_id, product_name, image_url, order_quantity, unit_price, sub_total)
VALUES(1, 'grape', 'mango_image_url', 3, 50.00, 150.00);

INSERT INTO t_cart_item(cart_id, product_name, image_url, order_quantity, unit_price, sub_total)
VALUES(1, 'pineapple', 'mango_image_url', 3, 50.00, 150.00);

INSERT INTO t_cart_item(cart_id, product_name, image_url, order_quantity, unit_price, sub_total)
VALUES(1, 'orange', 'mango_image_url', 3, 50.00, 150.00);



SELECT * FROM t_cart;
SELECT * FROM t_cart_item;

INSERT INTO t_product (name, unit_price, image_url, available_quantity, color, description)
VALUES('Mango', 45.89, 'image_url_for_mango', 100, 'yellow', 'Ripe, juicy mangoes for your taste buds.');

INSERT INTO t_product (name, unit_price, image_url, available_quantity, color, description)
VALUES('Tangerine', 45.89, 'image_url_for_tangerine', 10, 'yellow', 'Ripe, juicy mangoes for your taste buds.');

INSERT INTO t_product (name, unit_price, image_url, available_quantity, color, description)
VALUES('Orange', 45.89, 'image_url_for_orange', 500, 'sunset yellow', 'Ripe, juicy mangoes for your taste buds.');

INSERT INTO t_product (name, unit_price, image_url, available_quantity, color, description)
VALUES('Banana', 45.89, 'image_url_for_banana', 90, 'green', 'Ripe, juicy mangoes for your taste buds.');