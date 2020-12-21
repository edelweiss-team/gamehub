create table physicalpurchasing
(
	physicalProduct int not null,
	`order` int not null,
	quantity int default 1 not null,
	primary key (physicalProduct, `order`),
	constraint PhysicalPurchasing_order_id_fk
		foreign key (`order`) references `order` (id)
			on update cascade on delete cascade,
	constraint PhysicalPurchasing_physicalproduct_id_fk
		foreign key (physicalProduct) references physicalproduct (id)
			on update cascade on delete cascade
);

