create database homedb;

create table homeregister(
idn int primary key auto_increment,
id varchar(12) unique,
pass varchar(255) 
);

create table homeboard(
idx int primary key auto_increment,
idn int not null,
sdate timestamp default current_timestamp,
readcount int default 0,
title varchar(100) not null,
conten varchar(15000) not null,
foreign key( idn ) references homeregister ( idn ) on delete cascade
);

select idx, idn, id, sdate, readcount, title, conten  from homeboard natural join homeregister order by idx desc;

update homeboard set title = '성낙원', conten = '낑깡' where idx= 1;

create table messageleejin(
idx int auto_increment primary key,
sender varchar(20),
senderMail varchar(30),
phoneNumber varchar(30),
msgContent varchar(30));