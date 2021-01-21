create table User
(
    username varchar(45) not null,
    password varchar(60) not null,
    name varchar(25) not null,
    surname varchar(25) not null,
    address varchar(40) not null,
    city varchar(25) not null,
    country varchar(25) not null,
    birthDate date not null,
    mail varchar(50) not null,
    sex char not null,
    telephone varchar(15) not null,
    check ((sex='M') OR (sex='F')),
    constraint User_pk
        primary key (username)
);

create unique index User_mail_uindex
    on User (mail);

create table DigitalProduct
(
    id int auto_increment,
    name varchar(50) not null,
    price decimal(5,2) not null,
    description varchar(1000) not null default '',
    image varchar(256) not null,
    platform varchar(50) not null,
    releaseDate date not null,
    requiredAge int not null,
    softwareHouse varchar(50) not null,
    publisher varchar(50) not null,
    quantity int default 0 not null,
    check (requiredAge > 0 AND requiredAge < 19),
    check (price > 0),
    constraint DigitalProduct_pk
        primary key (id)
);

create table PhysicalProduct
(
    id int auto_increment,
    name varchar(50) not null,
    price decimal(5,2) not null,
    description varchar(1000) not null default '',
    image varchar(256) not null,
    weight decimal(5,2) not null,
    size varchar(30) not null,
    quantity int default 0 not null,
    check (price > 0),
    constraint DigitalProduct_pk
        primary key (id)
);

create table Moderator
(
    contractTime date not null,
    user varchar(45) not null primary key,
    constraint Moderator_user_username_fk
        foreign key (user) references User (username)
            on update cascade on delete cascade
);

create table Admin
(
    superAdmin boolean default false,
    moderator varchar(45) not null primary key,
    constraint Admin_moderator_user_fk
        foreign key (moderator) references Moderator (user)
            on update cascade on delete cascade
);

create table Operator
(
    contractTime date not null,
    cv varchar(500) not null,
    user varchar(45) not null primary key,
    constraint Operator_user_username_fk
        foreign key (user) references User (username)
            on update cascade on delete cascade
);

create table Cart
(
    user varchar(45) not null,
    totalPrice decimal(5,2) not null,
    numberOfProduct int not null,
    check (totalPrice >= 0),
    check ( numberOfProduct >= 0),
    constraint Cart_pk
        primary key (user),
    constraint Cart_user_username_fk
        foreign key (user) references user (username)
            on update cascade on delete cascade
);

create table `Order`
(
    user varchar(45) not null,
    id int auto_increment not null,
    totalPrice decimal(5,2) not null,
    numberOfProduct int not null,
    date date not null,
    operator varchar(45),
    check (totalPrice >= 0),
    check (numberOfProduct >= 0),
    foreign key (operator) references Operator (user)
        on update cascade on delete cascade,
    constraint Order_pk
        primary key (id),
    constraint Order_user_username_fk
        foreign key (user) references user (username)
            on update cascade on delete cascade
);

create table Tag
(
    name varchar(45) not null,
    constraint Tag_pk
        primary key (name)
);

create table PhysicalCharacteristic
(
    tag varchar(45) not null,
    physicalProduct int not null,
    constraint PhysicalCharacteristic_physicalproduct_id_fk
        foreign key (physicalProduct) references physicalproduct (id),
    constraint PhysicalCharacteristic_tag_name_fk
        foreign key (tag) references tag (name)
            on update cascade on delete cascade,
    primary key (tag, physicalProduct)
);

create table DigitalCharacteristic
(
    tag varchar(45) not null,
    digitalProduct int not null,
    constraint DigitalCharacteristic_digitalproduct_id_fk
        foreign key (digitalProduct) references DigitalProduct (id),
    constraint DigitalCharacteristic_tag_name_fk
        foreign key (tag) references Tag (name)
            on update cascade on delete cascade,
    primary key (tag, digitalProduct)
);

create table DigitalPurchasing
(
    digitalProduct int not null,
    `order` int not null,
    quantity int default 1 not null,
    constraint DigitalPurchasing_digitalproduct_id_fk
        foreign key (digitalProduct) references digitalproduct (id)
            on update cascade on delete cascade,
    constraint DigitalPurchasing_order_id_fk
        foreign key (`order`) references `order` (id)
            on update cascade on delete cascade,
    constraint DigitalPurchasing_pk
        primary key (digitalProduct, `order`)
);

create table PhysicalPurchasing
(
    physicalProduct int not null,
    `order` int not null,
    quantity int default 1 not null,
    constraint PhysicalPurchasing_physicalproduct_id_fk
        foreign key (physicalProduct) references physicalproduct (id)
            on update cascade on delete cascade,
    constraint PhysicalPurchasing_order_id_fk
        foreign key (`order`) references `order` (id)
            on update cascade on delete cascade,
    constraint PhysicalPurchasing_pk
        primary key (physicalProduct, `order`)
);

create table DigitalContaining
(
    digitalProduct int not null,
    cart varchar(45) not null,
    quantity int default 1 not null,
    constraint DigitalContaining_digitalproduct_id_fk
        foreign key (digitalProduct) references digitalproduct (id)
            on update cascade on delete cascade,
    constraint DigitalContaining_order_id_fk
        foreign key (cart) references Cart (user)
            on update cascade on delete cascade,
    constraint DigitalContaining_pk
        primary key (digitalProduct, cart)
);

create table PhysicalContaining
(
    physicalProduct int not null,
    cart varchar(45) not null,
    quantity int default 1 not null,
    constraint PhysicalContaining_physicalproduct_id_fk
        foreign key (physicalProduct) references physicalproduct (id)
            on update cascade on delete cascade,
    constraint PhysicalContaining_order_id_fk
        foreign key (cart) references Cart (user)
            on update cascade on delete cascade,
    constraint PhysicalContaining_pk
        primary key (physicalProduct, cart)
);


create table Category
(
    name varchar(45) not null,
    description varchar(1000) not null,
    image varchar(256) not null,
    constraint Category_pk
        primary key (name)
);

create table DigitalBelonging
(
    digitalProduct int not null,
    category varchar(45) not null,
    constraint DigitalBelonging_digitalproduct_id_fk
        foreign key (digitalProduct) references digitalproduct (id)
            on update cascade on delete cascade,
    constraint DigitalBelonging_order_id_fk
        foreign key (category) references Category (name)
            on update cascade on delete cascade,
    constraint DigitalBelonging_pk
        primary key (digitalProduct, category)
);

create table PhysicalBelonging
(
    physicalProduct int not null,
    category varchar(45) not null,
    constraint PhysicalBelonging_physicalproduct_id_fk
        foreign key (physicalProduct) references physicalproduct (id)
            on update cascade on delete cascade,
    constraint PhysicalBelonging_order_id_fk
        foreign key (category) references Category (name)
            on update cascade on delete cascade,
    constraint PhysicalBelonging_pk
        primary key (physicalProduct, category)
);

/* Trigger Carrello */
create definer = root@localhost TRIGGER TriggerOnCartInsertDigital
    after insert on DigitalContaining
    for each row
    update Cart
    set numberOfProduct = numberOfProduct + new.quantity,
        totalPrice = totalPrice + new.quantity*(
            select price from DigitalProduct D where new.digitalProduct=D.id
        )
    where new.cart=Cart.user;

create definer = root@localhost TRIGGER TriggerOnCartRemoveDigital
    before delete on DigitalContaining
    for each row
    update Cart
    set numberOfProduct=numberOfProduct - old.quantity,
        totalPrice = totalPrice - old.quantity*(
            select price from DigitalProduct D where old.digitalProduct=D.id
        )
    where old.cart=Cart.user;

create definer = root@localhost TRIGGER TriggerOnCartQuantityUpdateDigital
    after update on DigitalContaining
    for each row
    update Cart
    set numberOfProduct = numberOfProduct - old.quantity + new.quantity,
        totalPrice = totalPrice - old.quantity*(
            select price from DigitalProduct D where old.digitalProduct=D.id
        )  + new.quantity*(
            select price from DigitalProduct D where old.digitalProduct=D.id
        )
    where old.cart=Cart.user;

create definer = root@localhost TRIGGER TriggerOnCartInsertPhysical
    after insert on PhysicalContaining
    for each row
    update Cart
    set numberOfProduct = numberOfProduct + new.quantity,
        totalPrice = totalPrice + new.quantity*(
            select price from PhysicalProduct D where new.physicalProduct=D.id
        )
    where new.cart=Cart.user;

create definer = root@localhost TRIGGER TriggerOnCartRemovePhysical
    before delete on PhysicalContaining
    for each row
    update Cart
    set numberOfProduct=numberOfProduct - old.quantity,
        totalPrice = totalPrice - old.quantity*(
            select price from PhysicalProduct D where old.physicalProduct=D.id
        )
    where old.cart=Cart.user;

create definer = root@localhost TRIGGER TriggerOnCartQuantityUpdatePhysical
    after update on PhysicalContaining
    for each row
    update Cart
    set numberOfProduct = numberOfProduct - old.quantity + new.quantity,
        totalPrice = totalPrice - old.quantity*(
            select price from PhysicalProduct D where old.physicalProduct=D.id
        )  + new.quantity*(
            select price from PhysicalProduct D where old.physicalProduct=D.id
        )
    where old.cart=Cart.user;



/* Trigger Ordini */
create definer = root@localhost TRIGGER TriggerOnOrderInsertDigital
    after insert on DigitalPurchasing
    for each row
    update `Order`
    set numberOfProduct = numberOfProduct + new.quantity,
        totalPrice = totalPrice + new.quantity*(
            select price from DigitalProduct D where new.digitalProduct=D.id
        )
    where new.`order`=`Order`.id;

create definer = root@localhost TRIGGER TriggerOnOrderRemoveDigital
    before delete on DigitalPurchasing
    for each row
    update `Order`
    set numberOfProduct=numberOfProduct - old.quantity,
        totalPrice = totalPrice - old.quantity*(
            select price from DigitalProduct D where old.digitalProduct=D.id
        )
    where old.`order`=`Order`.id;

create definer = root@localhost TRIGGER TriggerOnOrderQuantityUpdateDigital
    after update on DigitalPurchasing
    for each row
    update `Order`
    set numberOfProduct = numberOfProduct - old.quantity + new.quantity,
        totalPrice = totalPrice - old.quantity*(
            select price from DigitalProduct D where old.digitalProduct=D.id
        )  + new.quantity*(
            select price from DigitalProduct D where old.digitalProduct=D.id
        )
    where old.`order`=`Order`.id;

create definer = root@localhost TRIGGER TriggerOnOrderInsertPhysical
    after insert on PhysicalPurchasing
    for each row
    update `Order`
    set numberOfProduct = numberOfProduct + new.quantity,
        totalPrice = totalPrice + new.quantity*(
            select price from PhysicalProduct D where new.physicalProduct=D.id
        )
    where new.`order`=`Order`.id;

create definer = root@localhost TRIGGER TriggerOnOrderRemovePhysical
    before delete on PhysicalPurchasing
    for each row
    update `Order`
    set numberOfProduct=numberOfProduct - old.quantity,
        totalPrice = totalPrice - old.quantity*(
            select price from PhysicalProduct D where old.physicalProduct=D.id
        )
    where old.`order`=`Order`.id;

create definer = root@localhost TRIGGER TriggerOnOrderQuantityUpdatePhysical
    after update on PhysicalPurchasing
    for each row
    update `Order`
    set numberOfProduct = numberOfProduct - old.quantity + new.quantity,
        totalPrice = totalPrice - old.quantity*(
            select price from PhysicalProduct D where old.physicalProduct=D.id
        )  + new.quantity*(
            select price from PhysicalProduct D where old.physicalProduct=D.id
        )
    where old.`order`=`Order`.id;
alter table user modify password varchar(60) not null;

alter table digitalcharacteristic drop foreign key DigitalCharacteristic_digitalproduct_id_fk;

alter table digitalcharacteristic
    add constraint DigitalCharacteristic_digitalproduct_id_fk
        foreign key (digitalProduct) references digitalproduct (id)
            on update cascade on delete cascade;

alter table physicalcharacteristic drop foreign key PhysicalCharacteristic_physicalproduct_id_fk;

alter table physicalcharacteristic
    add constraint PhysicalCharacteristic_physicalproduct_id_fk
        foreign key (physicalProduct) references physicalproduct (id)
            on update cascade on delete cascade;


/*Estrazione dati per il dataset di profilazione*/
SET GLOBAL log_bin_trust_function_creators = 1;
DROP FUNCTION IF EXISTS `get_unique_items`;
DELIMITER //
CREATE FUNCTION `get_unique_items`(str text) RETURNS text CHARSET utf8
BEGIN

    SET @String = str;
    IF(@String not like '%,%') THEN /*se non contiene virgole ritorniamo*/
        return @String;
    END IF;
    SET @Occurrences = LENGTH(@String) - LENGTH(REPLACE(@String, ',', ''));
    SET @ret='';
    myloop: WHILE (@Occurrences > 0)
        DO
            SET @myValue = SUBSTRING_INDEX(@String, ',', 1);
            IF (TRIM(@myValue) != '') THEN
                IF((LENGTH(@ret) - LENGTH(REPLACE(@ret, @myValue, '')))=0) THEN
                    SELECT CONCAT(@ret,@myValue,',') INTO @ret;
                END if;
            END IF;
            SET @Occurrences = LENGTH(@String) - LENGTH(REPLACE(@String, ',', ''));
            IF (@occurrences = 0) THEN
                LEAVE myloop;
            END IF;
            SET @String = SUBSTRING(@String,LENGTH(SUBSTRING_INDEX(@String, ',', 1))+2);
        END WHILE;
    SET @ret=concat(substring(@ret,1,length(@ret)-1), '');
    return @ret;

END //
DELIMITER ;

/*Viste che rappresentano il prodotto digitale/fisico con tutte le loro categorie e tag
*/
drop view if exists JoinedDigitalProduct;
create view JoinedDigitalProduct as
select distinct D.id, D.name, D.publisher, D.softwareHouse, D.requiredAge, D.price,
                GROUP_CONCAT(DISTINCT DC.tag) as Tags,
                GROUP_CONCAT(DISTINCT DB.category) as Categories
from digitalproduct D, digitalcharacteristic DC, digitalbelonging DB
where D.id=DC.digitalProduct AND D.id=DB.digitalProduct
group by D.id, D.name, D.publisher, D.softwareHouse, D.requiredAge, D.price;

select * from JoinedDigitalProduct;

drop view if exists JoinedPhysicalProduct;
create view JoinedPhysicalProduct as
select distinct P.id, P.name, P.price,
                GROUP_CONCAT(DISTINCT PC.tag) as Tags,
                GROUP_CONCAT(DISTINCT PB.category) as Categories
from physicalproduct P, physicalcharacteristic PC, physicalbelonging PB
where P.id=PC.physicalProduct AND P.id=PB.physicalProduct
group by P.id, P.name, P.price;

select * from JoinedPhysicalProduct;

SET group_concat_max_len = 4000000;
/*Campione di un utente con le caratteristiche dei prodotti fisici dei suoi ordini,
  escludendo gli utenti fittizi, che hanno una '@' nello username.
*/
drop view if exists DataSetSamplePhysical;
create view DataSetSamplePhysical as
select distinct U.username as Username, U.country as Country,
                (AVG(JPP.price)) as avgPrice,
                get_unique_items(GROUP_CONCAT(DISTINCT JPP.Tags)) as Tags,
                get_unique_items(GROUP_CONCAT(DISTINCT JPP.Categories)) as Categories
from JoinedPhysicalProduct JPP, `order` O, physicalpurchasing PP, user U
where U.username=O.user AND O.id=PP.`order` AND PP.physicalProduct=JPP.id AND U.username not like '%@%'
group by U.username, U.country;

select * from DataSetSamplePhysical;

/*Campione di un utente con le caratteristiche dei prodotti digitali dei suoi ordini,
  escludendo gli utenti fittizi, che hanno una '@' nello username.
*/
drop view if exists DataSetSampleDigital;
create view DataSetSampleDigital as
select distinct U.username as Username, U.country as Country,
                (AVG(JDP.price)) as avgPrice,
                MAX(JDP.requiredAge) as maxRequiredAge,
                get_unique_items(GROUP_CONCAT(DISTINCT JDP.Tags)) as Tags,
                get_unique_items(GROUP_CONCAT(DISTINCT JDP.Categories)) as Categories,
                GROUP_CONCAT(DISTINCT JDP.softwareHouse) as Developers,
                GROUP_CONCAT(DISTINCT JDP.publisher) as Publishers
from JoinedDigitalProduct JDP, `order` O, digitalpurchasing DP,  user U
where U.username=O.user AND (O.id=DP.`order` AND DP.digitalProduct=JDP.id) AND U.username not like '%@%'
group by U.username, U.country;

select * from DataSetSampleDigital;

/*Creiamo la vista dei campioni del dataset, facendo la join full unendo la left e la right,
  per garantire che vengano considerate sia le persone che hanno fatto ordini su prodotti fisici,
  sia quelle sui prodotti digitali, sia quelle che li hanno fatti su entrambi.
*/
drop view if exists DataSetSample;
create view DataSetSample as
select *
from (
         select distinct DSSP.Username as Username, DSSP.Country as Country,
                         (DSSP.avgPrice + DSSD.avgPrice)/2 as avgPrice,
                         DSSD.maxRequiredAge as maxRequiredAge,
                         get_unique_items(
                                 GROUP_CONCAT(distinct concat(DSSD.Tags, ',', DSSP.Tags))
                             ) as Tags,
                         get_unique_items(
                                 GROUP_CONCAT(distinct concat(DSSD.Categories, ',', DSSP.Categories))
                             ) as Categories,
                         DSSD.Developers as Developers,
                         DSSD.Publishers as Publishers
         from DataSetSamplePhysical DSSP join DataSetSampleDigital DSSD on (DSSP.Username=DSSD.Username)
         group by DSSP.Username, DSSP.Country
         UNION
         select distinct DSSP.Username as Username, DSSP.Country as Country,
                         (DSSP.avgPrice)/2 as avgPrice,
                         DSSD.maxRequiredAge as maxRequiredAge,
                         get_unique_items(
                                 GROUP_CONCAT(distinct DSSP.Tags)
                             ) as Tags,
                         get_unique_items(
                                 GROUP_CONCAT(distinct DSSP.Categories)
                             ) as Categories,
                         DSSD.Developers as Developers,
                         DSSD.Publishers as Publishers
         from DataSetSamplePhysical DSSP left join DataSetSampleDigital DSSD on (DSSP.Username=DSSD.Username)
         group by DSSP.Username, DSSP.Country
         UNION
         select distinct DSSD.Username, DSSD.Country as Country,
                         (DSSD.avgPrice) as avgPrice,
                         DSSD.maxRequiredAge as maxRequiredAge,
                         get_unique_items(
                                 GROUP_CONCAT(distinct DSSD.Tags)
                             ) as Tags,
                         get_unique_items(
                                 GROUP_CONCAT(distinct DSSD.Categories)
                             ) as Categories,
                         DSSD.Developers as Developers,
                         DSSD.Publishers as Publishers
         from DataSetSamplePhysical DSSP right join DataSetSampleDigital DSSD on (DSSP.Username=DSSD.Username)
         group by DSSD.Username, DSSD.Country, DSSD.avgPrice, DSSD.Developers, DSSD.Publishers
     ) as SubQuery
group by Username;

insert into User values
    ('fferrucci', sha1('password'), 'Filomena', 'Ferrucci', 'Via Giovanni Paolo II',
     'Fisciano', 'IT', '1980-12-12', 'fferrucci@unisa.it', 'F', '3334374421'
    );

insert into Moderator values
    ('2030-12-12', 'fferrucci');

insert into Admin values
    (true, 'fferrucci');