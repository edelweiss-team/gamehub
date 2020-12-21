create table cart
(
	user varchar(45) not null
		primary key,
	totalPrice decimal(5,2) not null,
	numberOfProduct int not null,
	constraint Cart_user_username_fk
		foreign key (user) references user (username)
			on update cascade on delete cascade
);

