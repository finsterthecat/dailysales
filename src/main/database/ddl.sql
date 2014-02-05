drop table monthly_sales;
drop table store;
drop table mall;

drop sequence seq_mall restrict;
drop sequence seq_store restrict;
drop sequence seq_monthly_sales restrict;

create table mall (
    id bigint primary key,
    name varchar(50) not null
);
create unique index mall_ak on mall(name);

create sequence seq_mall as bigint start with 100000;

create table store (
    id bigint primary key,
    mall_id bigint not null
        constraint store_mall_fk
        references mall(id),
    name varchar(50) not null
);
create unique index store_ak on store(name);

create sequence seq_store as bigint start with 200000;

create table monthly_sales (
    id bigint primary key,
    store_id bigint not null
        constraint monthly_sales_store_fk
        references store(id),
    sales_amt decimal(10,2) not null,
    cost_amt decimal(10,2) not null
);
create sequence seq_monthly_sales as bigint start with 300000;
