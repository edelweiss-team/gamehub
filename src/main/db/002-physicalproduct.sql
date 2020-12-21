create table physicalproduct
(
	id int auto_increment
		primary key,
	name varchar(50) not null,
	price decimal(5,2) not null,
	description varchar(1000) default '' not null,
	image varchar(256) not null,
	weight decimal(5,2) not null,
	size varchar(30) not null,
	quantity int default 0 not null
);

