create table physicalcharacteristic
(
	tag varchar(45) not null,
	physicalProduct int not null,
	primary key (tag, physicalProduct),
	constraint PhysicalCharacteristic_physicalproduct_id_fk
		foreign key (physicalProduct) references physicalproduct (id),
	constraint PhysicalCharacteristic_tag_name_fk
		foreign key (tag) references tag (name)
			on update cascade on delete cascade
);

