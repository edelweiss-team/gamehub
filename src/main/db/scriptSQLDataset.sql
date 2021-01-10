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


create view DataSetProduct as
select distinct A.appid, A.Title, A.Type, A.Price,
                A.Required_Age, A.Is_Multiplayer,
                GROUP_CONCAT(DISTINCT GG.Genre) as Genres,
                GROUP_CONCAT(DISTINCT GP.Publisher) as Publishers,
                GROUP_CONCAT(DISTINCT GD.Developer) as Developers
from app_id_info A, games_genres GG, games_publishers GP,
     games_developers GD
where A.appid=GG.appid AND GG.appid=GP.appid AND GD.appid=GP.appid
group by A.appid, A.Title, A.Type, A.Price, A.Release_Date,
         A.Required_Age, A.Is_Multiplayer;

select * from DataSetProduct;

SET group_concat_max_len = 4000000;
drop view if exists DataSetSample;
create view DataSetSample as
    select distinct P.steamid, P.personaname, P.personastate,
                    P.communityvisibilitystate, P.profilestate,
                    P.cityid, P.loccountrycode, P.loccityid,
                    AVG(A.Price) as avgPrice,
                    MAX(A.Required_Age) as maxRequiredAge,
                    MAX(A.Is_Multiplayer) as usesMultiplayer,
                    get_unique_items(GROUP_CONCAT(DISTINCT A.Genres)) as Genres,
                    get_unique_items(GROUP_CONCAT(DISTINCT A.Publishers)) as Publishers,
                    get_unique_items(GROUP_CONCAT(DISTINCT A.Developers)) as Developers,
                    GROUP_CONCAT(DISTINCT A.Type) as Categories
    from DataSetProduct A, games_2 G, player_summaries P
    where A.appid=G.appid AND G.steamid=P.steamid
    group by P.steamid, P.personaname, P.personastate,
             P.communityvisibilitystate, P.profilestate,
             P.cityid, P.loccountrycode, P.loccityid;

select * from DataSetSample;