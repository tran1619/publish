create table news_item
(
   id integer primary key,
   title text not null,
   url text not null
);
create table sequence
(
   next_value integer
);
create table user
(
   id integer primary key,
   username varchar(255) unique,
   password varchar(255),
   accesskey varchar(255) unique
);
insert into sequence value (1000);