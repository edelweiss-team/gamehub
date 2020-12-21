create table digitalpurchasing
(
	digitalProduct int not null,
	`order` int not null,
	quantity int default 1 not null,
	primary key (digitalProduct, `order`),
	constraint DigitalPurchasing_digitalproduct_id_fk
		foreign key (digitalProduct) references digitalproduct (id)
			on update cascade on delete cascade,
	constraint DigitalPurchasing_order_id_fk
		foreign key (`order`) references `order` (id)
			on update cascade on delete cascade
);

