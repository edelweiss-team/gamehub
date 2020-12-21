create table physicalbelonging
(
	physicalProduct int not null,
	category varchar(45) not null,
	primary key (physicalProduct, category),
	constraint PhysicalBelonging_order_id_fk
		foreign key (category) references category (name)
			on update cascade on delete cascade,
	constraint PhysicalBelonging_physicalproduct_id_fk
		foreign key (physicalProduct) references physicalproduct (id)
			on update cascade on delete cascade
);

