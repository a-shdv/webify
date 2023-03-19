alter table products
    add column quantity int after image;

update products
set quantity = 50
where category_id = 1;

update products
set quantity = 25
where category_id = 2;

update products
set quantity = 15
where category_id = 3;

update products
set quantity = 25
where category_id = 4;

update products
set quantity = 5
where category_id = 5;

update products
set quantity = 5
where category_id = 6;