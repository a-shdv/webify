drop table if exists carts;
drop table if exists carts_products;
drop table if exists categories;
drop table if exists orders;
drop table if exists orders_details;
drop table if exists posts;
drop table if exists products;
drop table if exists users;
drop table if exists users_roles;

create table carts
(
    id      bigint not null auto_increment,
    user_id bigint,
    primary key (id)
) engine = InnoDB;

create table carts_products
(
    cart_id    bigint not null,
    product_id bigint not null
) engine = InnoDB;

create table categories
(
    id    bigint not null auto_increment,
    name  varchar(255),
    image varchar(255),
    primary key (id)
) engine = InnoDB;

create table orders
(
    id         bigint not null auto_increment,
    sum        decimal(19, 2),
    address    varchar(255),
    created_at datetime(6),
    updated_at datetime(6),
    status     varchar(255),
    user_id    bigint,
    primary key (id)
) engine = InnoDB;

create table orders_details
(
    id         bigint not null auto_increment,
    amount     decimal(19, 2),
    price      decimal(19, 2),
    order_id   bigint,
    product_id bigint,
    primary key (id)
) engine = InnoDB;

create table posts
(
    id          bigint not null auto_increment,
    header      varchar(255),
    description varchar(255),
    user_id     bigint,
    primary key (id)
) engine = InnoDB;

create table products
(
    id          bigint not null auto_increment,
    name        varchar(255),
    description text,
    price       integer,
    image       varchar(255),
    category_id bigint,
    primary key (id)
) engine = InnoDB;

create table users
(
    id       bigint not null auto_increment,
    username varchar(255),
    password varchar(255),
    active   bit,
    cart_id  bigint,
    primary key (id)
) engine = InnoDB;

create table users_roles
(
    user_id    bigint not null,
    user_roles varchar(255)
) engine = InnoDB;

alter table carts
    add constraint carts_users_fk foreign key (user_id) references users (id);

alter table carts_products
    add constraint carts_products_products_fk foreign key (product_id) references products (id);

alter table carts_products
    add constraint carts_products_carts_fk foreign key (cart_id) references carts (id);

alter table orders
    add constraint orders_users_fk foreign key (user_id) references users (id);

alter table orders_details
    add constraint orders_details_orders_fk foreign key (order_id) references orders (id);

alter table orders_details
    add constraint orders_details_products_fk foreign key (product_id) references products (id);

alter table posts
    add constraint posts_users_fk foreign key (user_id) references users (id);

alter table products
    add constraint products_categories_fk foreign key (category_id) references categories (id);

alter table users
    add constraint users_carts_fk foreign key (cart_id) references carts (id);

alter table users_roles
    add constraint users_roles_users_fk foreign key (user_id) references users (id)
