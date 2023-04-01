drop table if exists cart;
drop table if exists cart_items;
drop table if exists categories;
drop table if exists orders;
drop table if exists orders_info;
drop table if exists posts;
drop table if exists products;
drop table if exists users;
drop table if exists users_roles;

create table cart
(
    id          bigint not null auto_increment,
    total_price double precision,
    primary key (id)
) engine = InnoDB;

create table cart_items
(
    id          bigint not null auto_increment,
    quantity    integer,
    total_price double precision,
    cart_id     bigint,
    product_id  bigint,
    primary key (id)
) engine = InnoDB;

create table categories
(
    id    bigint not null auto_increment,
    image varchar(255),
    name  varchar(255),
    primary key (id)
) engine = InnoDB;

create table orders
(
    id               bigint not null auto_increment,
    city             varchar(255),
    comment          text,
    email            varchar(255),
    name             varchar(255),
    phone_number     varchar(255),
    shipping_address varchar(255),
    status           integer,
    user_id          bigint,
    primary key (id)
) engine = InnoDB;

create table orders_info
(
    id          bigint not null auto_increment,
    quantity    integer,
    total_price double precision,
    order_id    bigint,
    product_id  bigint,
    primary key (id)
) engine = InnoDB;

create table posts
(
    id          bigint not null auto_increment,
    description varchar(255),
    header      varchar(255),
    user_id     bigint,
    primary key (id)
) engine = InnoDB;

create table products
(
    id          bigint not null auto_increment,
    description text,
    image       varchar(255),
    name        varchar(255),
    price       double precision,
    category_id bigint,
    primary key (id)
) engine = InnoDB;

create table users
(
    id       bigint not null auto_increment,
    active   bit,
    password varchar(255),
    username varchar(255),
    cart_id  bigint,
    primary key (id)
) engine = InnoDB;

create table users_roles
(
    user_id       bigint not null,
    user_role_set varchar(255)
) engine = InnoDB;

alter table cart_items
    add constraint cart_items_cart_fk foreign key (cart_id) references cart (id);

alter table cart_items
    add constraint cart_items_products_fk foreign key (product_id) references products (id);

alter table orders
    add constraint orders_users_fk foreign key (user_id) references users (id);

alter table orders_info
    add constraint orders_info_orders_fk foreign key (order_id) references orders (id);

alter table orders_info
    add constraint orders_info_products_fk foreign key (product_id) references products (id);

alter table posts
    add constraint posts_users_fk foreign key (user_id) references users (id);

alter table products
    add constraint products_categories_fk foreign key (category_id) references categories (id);

alter table users
    add constraint users_cart_fk foreign key (cart_id) references cart (id);

alter table users_roles
    add constraint users_roles_users_fk foreign key (user_id) references users (id)
