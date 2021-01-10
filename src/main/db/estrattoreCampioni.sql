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

select * from DataSetSample;