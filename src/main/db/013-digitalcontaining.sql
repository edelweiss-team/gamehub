create table digitalcontaining
(
	digitalProduct int not null,
	cart varchar(45) not null,
	quantity int default 1 not null,
	primary key (digitalProduct, cart),
	constraint DigitalContaining_digitalproduct_id_fk
		foreign key (digitalProduct) references digitalproduct (id)
			on update cascade on delete cascade,
	constraint DigitalContaining_order_id_fk
		foreign key (cart) references cart (user)
			on update cascade on delete cascade
);

