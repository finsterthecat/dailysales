delete from monthly_sales;
delete from store;
delete from mall;

insert into mall(id, name)
    values(next value for seq_mall, 'New WestMinster Mall');
insert into mall(id, name)
    values(next value for seq_mall, 'East Edmonton Mall');
insert into mall(id, name)
    values(next value for seq_mall, 'Mall of the Americas');
insert into mall(id, name)
    values(next value for seq_mall, 'Abu Dabi Mall');


insert into store (id, mall_id, name)
    values(
        next value for seq_store,
        (select id from mall where name = 'New WestMinster Mall'),
        'Harry Rosen'
        );
insert into store (id, mall_id, name)
    values(
        next value for seq_store,
        (select id from mall where name = 'New WestMinster Mall'),
        'Holt Renfrew'
        );

insert into store (id, mall_id, name)
    values(
        next value for seq_store,
        (select id from mall where name = 'New WestMinster Mall'),
        'Burberry'
        );

