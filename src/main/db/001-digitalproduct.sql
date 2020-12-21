create table digitalproduct
(
	id int auto_increment
		primary key,
	name varchar(50) not null,
	price decimal(5,2) not null,
	description varchar(1000) default '' not null,
	image varchar(256) not null,
	platform varchar(50) not null,
	releaseDate date not null,
	requiredAge int not null,
	softwareHouse varchar(50) not null,
	publisher varchar(50) not null,
	quantity int default 0 not null
);

