drop table if exists categories;
drop table if exists products;

create table categories
(
    id          bigint       not null auto_increment,
    name        varchar(255) not null,
    description text         not null,
    image       varchar(255),
    primary key (id)
);

create table products
(
    id          bigint       not null auto_increment,
    name        varchar(255) not null,
    description text         not null,
    image       varchar(255),
    price       integer      not null,
    category_id bigint,
    primary key (id)
);

alter table products
    add constraint products_category_fk
        foreign key (category_id) references categories (id);