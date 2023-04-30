drop table if exists cart cascade;
drop table if exists cart_product cascade;
drop table if exists category cascade;
drop table if exists "order" cascade;
drop table if exists order_product cascade;
drop table if exists post cascade;
drop table if exists product cascade;
drop table if exists "user" cascade;
drop table if exists user_role cascade;

create table cart
(
    id          int8 generated by default as identity,
    total_price float8,
    primary key (id)
);

create table cart_product
(
    id         int8 generated by default as identity,
    price      float8,
    quantity   int4,
    cart_id    int8,
    product_id int8,
    primary key (id)
);

create table category
(
    id    int8 generated by default as identity,
    image varchar(255),
    name  varchar(255),
    primary key (id)
);

create table "order"
(
    id               int8 generated by default as identity,
    apartment_number int4,
    comment          text,
    created_date     timestamp,
    door_password    int4,
    entrance_number  int4,
    floor            int4,
    shipping_address varchar(255),
    user_id          int8,
    primary key (id)
);

create table order_product
(
    id         int8 generated by default as identity,
    price      float8,
    quantity   int4,
    order_id   int8,
    product_id int8,
    primary key (id)
);

create table post
(
    id          int8 generated by default as identity,
    description varchar(255),
    header      varchar(255),
    user_id     int8,
    primary key (id)
);

create table product
(
    id          int8 generated by default as identity,
    description text,
    image       varchar(255),
    name        varchar(255),
    price       float8,
    category_id int8,
    primary key (id)
);

create table "user"
(
    id       int8 generated by default as identity,
    active   boolean,
    email    varchar(255),
    password varchar(255),
    phone    varchar(255),
    username varchar(255),
    cart_id  int8,
    primary key (id)
);

create table user_role
(
    user_id       int8 not null,
    user_role_set varchar(255)
);

alter table if exists cart_product
    add constraint cart_product_cart foreign key (cart_id) references cart;
alter table if exists cart_product
    add constraint cart_product_product foreign key (product_id) references product;
alter table if exists "order"
    add constraint order_user foreign key (user_id) references "user";
alter table if exists order_product
    add constraint order_product_order foreign key (order_id) references "order";
alter table if exists order_product
    add constraint order_product_product foreign key (product_id) references product;
alter table if exists post
    add constraint post_user foreign key (user_id) references "user";
alter table if exists product
    add constraint product_category foreign key (category_id) references category;
alter table if exists "user"
    add constraint user_cart foreign key (cart_id) references cart;
alter table if exists user_role
    add constraint user_role_user foreign key (user_id) references "user";