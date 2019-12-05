create table journal(
	id int auto_increment primary key,
	title varchar(20),
	content text,
	file_src text,
	recorded_at date,
	modified_at datetime,
	tmp1 int,
	tmp2 int,
	tmp3 int
);