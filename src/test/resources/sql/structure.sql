drop table task;

create table task (
id int generated by default as identity (start with 1) primary key,
name varchar(50) not null,
description varchar(150),
progress varchar(1) not null,
id_employee int,
status varchar(1) not null,
user_registration varchar(10) not null,
date_registration date not null ,
user_update varchar(10),
date_update date
);