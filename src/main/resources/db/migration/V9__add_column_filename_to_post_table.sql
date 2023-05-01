ALTER TABLE post
    ADD COLUMN filename VARCHAR(255);


create table cart
(
    id          int8 generated by default as identity,
    total_price float8,
    primary key (id)
) Hibernate:
create table cart_product
(
    id         int8 generated by default as identity,
    price      float8 not null,
    quantity   int4   not null,
    cart_id    int8,
    product_id int8,
    primary key (id)
) Hibernate:
create table category
(
    id    int8 generated by default as identity,
    image varchar(255) not null,
    name  varchar(255) not null,
    primary key (id)
) Hibernate:
create table "order"
(
    id               int8 generated by default as identity,
    apartment_number int4         not null,
    comment          text,
    created_at       timestamp    not null,
    door_password    int4         not null,
    entrance_number  int4         not null,
    floor            int4         not null,
    shipping_address varchar(255) not null,
    user_id          int8,
    primary key (id)
) Hibernate:
create table order_product
(
    id         int8 generated by default as identity,
    price      float8 not null,
    quantity   int4   not null,
    order_id   int8,
    product_id int8,
    primary key (id)
) Hibernate:
create table post
(
    id          int8 generated by default as identity,
    created_at  timestamp    not null,
    description varchar(255) not null,
    filename    varchar(255),
    title       varchar(255) not null,
    user_id     int8,
    primary key (id)
) Hibernate:
create table product
(
    id          int8 generated by default as identity,
    description text         not null,
    image       varchar(255) not null,
    name        varchar(255) not null,
    price       float8       not null,
    category_id int8,
    primary key (id)
) Hibernate:
create table "user"
(
    id       int8 generated by default as identity,
    active   boolean,
    email    varchar(255) not null,
    password varchar(255) not null,
    phone    varchar(255) not null,
    username varchar(255) not null,
    cart_id  int8,
    primary key (id)
) Hibernate:
create table user_role
(
    user_id    int8 not null,
    user_roles varchar(255)
) Hibernate:
alter table if exists cart_product
    add constraint FKlv5x4iresnv4xspvomrwd8ej9 foreign key (cart_id) references cart Hibernate:
alter table if exists cart_product
    add constraint FK2kdlr8hs2bwl14u8oop49vrxi foreign key (product_id) references product Hibernate:
alter table if exists "order"
    add constraint FKrcaf946w0bh6qj0ljiw3pwpnu foreign key (user_id) references "user" Hibernate:
alter table if exists order_product
    add constraint FKm6igrp4lwucj1me05axmv885c foreign key (order_id) references "order" Hibernate:
alter table if exists order_product
    add constraint FKhnfgqyjx3i80qoymrssls3kno foreign key (product_id) references product Hibernate:
alter table if exists post
    add constraint FK51aeb5le008k8klgnyfaalmn foreign key (user_id) references "user" Hibernate:
alter table if exists product
    add constraint FK1mtsbur82frn64de7balymq9s foreign key (category_id) references category Hibernate:
alter table if exists "user"
    add constraint FKe9st2xo1fo7uxex0k6pqq414r foreign key (cart_id) references cart Hibernate:
alter table if exists user_role
    add constraint FKfgsgxvihks805qcq8sq26ab7c foreign key (user_id) references "user"