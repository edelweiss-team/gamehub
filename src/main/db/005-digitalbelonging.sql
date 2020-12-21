create table digitalbelonging
(
	digitalProduct int not null,
	category varchar(45) not null,
	primary key (digitalProduct, category),
	constraint DigitalBelonging_digitalproduct_id_fk
		foreign key (digitalProduct) references digitalproduct (id)
			on update cascade on delete cascade,
	constraint DigitalBelonging_order_id_fk
		foreign key (category) references category (name)
			on update cascade on delete cascade
);

