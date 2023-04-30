ALTER TABLE cart_product
    ALTER COLUMN quantity SET NOT NULL;

ALTER TABLE cart_product
    ALTER COLUMN price SET NOT NULL;


ALTER TABLE category
    ALTER COLUMN name SET NOT NULL;


ALTER TABLE "order"
    ALTER COLUMN shipping_address SET NOT NULL;

ALTER TABLE "order"
    ALTER COLUMN entrance_number SET NOT NULL;

ALTER TABLE "order"
    ALTER COLUMN door_password SET NOT NULL;

ALTER TABLE "order"
    ALTER COLUMN floor SET NOT NULL;

ALTER TABLE "order"
    ALTER COLUMN apartment_number SET NOT NULL;

ALTER TABLE "order"
    ALTER COLUMN created_date SET NOT NULL;


ALTER TABLE order_product
    ALTER COLUMN quantity SET NOT NULL;

ALTER TABLE order_product
    ALTER COLUMN price SET NOT NULL;


ALTER TABLE post
    ALTER COLUMN title SET NOT NULL;

ALTER TABLE post
    ALTER COLUMN description SET NOT NULL;


ALTER TABLE product
    ALTER COLUMN name SET NOT NULL;

ALTER TABLE product
    ALTER COLUMN description SET NOT NULL;

ALTER TABLE product
    ALTER COLUMN price SET NOT NULL;


ALTER TABLE "user"
    ALTER COLUMN email SET NOT NULL;

ALTER TABLE "user"
    ALTER COLUMN username SET NOT NULL;

ALTER TABLE "user"
    ALTER COLUMN password SET NOT NULL;

ALTER TABLE "user"
    ALTER COLUMN phone SET NOT NULL;