create table alarm(
	id int auto_increment primary key,
	start_alarm datetime,
	id_schedule int,
	
	foreign key (id_schedule) references schedule (id)
	
);