create table operator
(
	contractTime date not null,
	cv varchar(500) not null,
	user varchar(45) not null
		primary key,
	constraint Operator_user_username_fk
		foreign key (user) references user (username)
			on update cascade on delete cascade
);

