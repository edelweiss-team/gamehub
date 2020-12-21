create table moderator
(
	contractTime date not null,
	user varchar(45) not null
		primary key,
	constraint Moderator_user_username_fk
		foreign key (user) references user (username)
			on update cascade on delete cascade
);

