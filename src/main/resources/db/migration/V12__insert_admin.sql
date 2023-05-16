INSERT INTO cart(total_price)
VALUES (0);

INSERT INTO "user" (active, email, password, phone, username, cart_id)
VALUES (true, 'shadaev2001@icloud.com', 'admin', '+79999999999', 'admin', 1);

INSERT INTO user_role (user_id, user_roles)
VALUES (1, 'ADMIN')