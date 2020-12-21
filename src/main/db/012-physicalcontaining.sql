create table physicalcontaining
(
	physicalProduct int not null,
	cart varchar(45) not null,
	quantity int default 1 not null,
	primary key (physicalProduct, cart),
	constraint PhysicalContaining_order_id_fk
		foreign key (cart) references cart (user)
			on update cascade on delete cascade,
	constraint PhysicalContaining_physicalproduct_id_fk
		foreign key (physicalProduct) references physicalproduct (id)
			on update cascade on delete cascade
);

