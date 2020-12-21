create table `order`
(
	user varchar(45) not null,
	id int auto_increment
		primary key,
	totalPrice decimal(5,2) not null,
	numberOfProduct int not null,
	date date not null,
	operator varchar(45) null,
	constraint Order_user_username_fk
		foreign key (user) references user (username)
			on update cascade on delete cascade,
	constraint order_ibfk_1
		foreign key (operator) references operator (user)
			on update cascade on delete cascade
);

create index operator
	on `order` (operator);

