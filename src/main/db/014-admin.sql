create table admin
(
	superAdmin tinyint(1) default 0 null,
	moderator varchar(45) not null
		primary key,
	constraint Admin_moderator_user_fk
		foreign key (moderator) references moderator (user)
			on update cascade on delete cascade
);

