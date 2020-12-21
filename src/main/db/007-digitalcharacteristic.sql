create table digitalcharacteristic
(
	tag varchar(45) not null,
	digitalProduct int not null,
	primary key (tag, digitalProduct),
	constraint DigitalCharacteristic_digitalproduct_id_fk
		foreign key (digitalProduct) references digitalproduct (id),
	constraint DigitalCharacteristic_tag_name_fk
		foreign key (tag) references tag (name)
			on update cascade on delete cascade
);

