create table user
(
	username varchar(45) not null
		primary key,
	password varchar(25) not null,
	name varchar(25) not null,
	surname varchar(25) not null,
	address varchar(40) not null,
	city varchar(25) not null,
	country varchar(25) not null,
	birthDate date not null,
	mail varchar(50) not null,
	sex char not null,
	telephone varchar(15) not null,
	constraint User_mail_uindex
		unique (mail)
);

