create table schedule(
	id int auto_increment primary key,
	title varchar(20) not null,
	content text,
	time_from datetime,
	time_to datetime,
	location varchar(20),
	modified_at datetime,
	tmp1 int,
	tmp2 int,
	tmp3 int
);