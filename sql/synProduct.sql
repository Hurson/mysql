--同步双向产品
CREATE DEFINER = CURRENT_USER PROCEDURE `synProduct`()
    NOT DETERMINISTIC
    CONTAINS SQL
    SQL SECURITY DEFINER
    COMMENT ''
BEGIN
update `t_productinfo` m join t_productinfo_temp n on m.`PRODUCT_ID`=n.`PRODUCT_ID`
 and m.`networkID`=n.`networkID` set m.`PRODUCT_NAME`=n.`PRODUCT_NAME`;
commit;
delete FROM t_productinfo  where (`PRODUCT_ID`,`networkID`) not in (
select  PRODUCT_ID,networkID from  `t_productinfo_temp`
);
commit;
insert into t_productinfo  select n.* from t_productinfo_temp n  where (n.`PRODUCT_ID`,n.`networkID`) not IN(select PRODUCT_ID,networkID from  `t_productinfo` );
commit;
   
END;

--同步双向频道信息
CREATE DEFINER = CURRENT_USER PROCEDURE `synChannelNPVR`()
    NOT DETERMINISTIC
    CONTAINS SQL
    SQL SECURITY DEFINER
    COMMENT ''
BEGIN

update `t_channelinfo_npvr` m join t_channelinfo_npvr_temp n on m.`CHANNEL_ID`=n.`CHANNEL_ID`
 and m.`networkID`=n.`networkID` set m.`CHANNEL_NAME`=n.`CHANNEL_NAME`;
commit;
delete FROM t_channelinfo_npvr  where (`CHANNEL_ID`,`networkID`) not in (
select  CHANNEL_ID,networkID from  `t_channelinfo_npvr_temp`
);
commit;   
insert into t_channelinfo_npvr  select n.* from t_channelinfo_npvr_temp n  where (n.`CHANNEL_ID`,n.`networkID`) not IN(select CHANNEL_ID,networkID from  `t_channelinfo_npvr` );
commit;

END;

--同步用户级别信息
CREATE DEFINER = CURRENT_USER PROCEDURE `synUserRank`()
    NOT DETERMINISTIC
    CONTAINS SQL
    SQL SECURITY DEFINER
    COMMENT ''
BEGIN

update `t_user_rank` m join t_user_rank_temp n on m.`USER_RANK_CODE`=n.`USER_RANK_CODE`
 and m.`areacode`=n.`areacode` set m.`USER_RANK_NAME`=n.`USER_RANK_NAME`;
commit;
delete FROM t_user_rank  where (`USER_RANK_CODE`,`areacode`) not in (
select  USER_RANK_CODE,areacode from  `t_user_rank_temp`
);
commit;
insert into t_user_rank  select n.* from t_user_rank_temp n  where (n.`USER_RANK_CODE`,n.`areacode`) not IN(select USER_RANK_CODE,areacode from  `t_user_rank` );
commit;   
END;

--同步行业类别信息
CREATE DEFINER = CURRENT_USER PROCEDURE `synUserIndustryCategory`()
    NOT DETERMINISTIC
    CONTAINS SQL
    SQL SECURITY DEFINER
    COMMENT ''
BEGIN

update `t_user_industry_category` m join t_user_industry_category_temp n on m.`USER_INDUSTRY_CATEGORY_CODE`=n.`USER_INDUSTRY_CATEGORY_CODE`
 and m.`areacode`=n.`areacode` set m.`USER_INDUSTRY_CATEGORY_VALUE`=n.`USER_INDUSTRY_CATEGORY_VALUE`;
commit;
delete FROM t_user_rank  where (`USER_INDUSTRY_CATEGORY_CODE`,`areacode`) not in (
select  USER_INDUSTRY_CATEGORY_CODE,areacode from  `t_user_industry_category_temp`
);
commit;   
insert into t_user_industry_category  select n.* from t_user_industry_category_temp n  where (n.`USER_INDUSTRY_CATEGORY_CODE`,n.`areacode`) not IN(select USER_INDUSTRY_CATEGORY_CODE,areacode from  `t_user_industry_category` );
commit;
END;

--同步区域信息
CREATE DEFINER = CURRENT_USER PROCEDURE `synLocationCode`()
    NOT DETERMINISTIC
    CONTAINS SQL
    SQL SECURITY DEFINER
    COMMENT ''
BEGIN

update `t_location_code` m join t_location_code_temp n on m.`LOCATIONCODE`=n.`LOCATIONCODE`
 and m.`areacode`=n.`areacode` set m.`LOCATIONNAME`=n.`LOCATIONNAME` and m.PARENTLOCATION=n.PARENTLOCATION and m.LOCATIONTYPE=n.LOCATIONTYPE;
commit;
delete FROM t_location_code  where (`LOCATIONCODE`,`areacode`) not in (
select  LOCATIONCODE,areacode from  `t_location_code_temp`
);
commit;   
insert into t_location_code  select n.* from t_location_code_temp n  where (n.`LOCATIONCODE`,n.`areacode`) not IN(select LOCATIONCODE,areacode from  `t_location_code` );
commit;
END;

--同步双向栏目信息
CREATE DEFINER = CURRENT_USER PROCEDURE `synCategory`()
    NOT DETERMINISTIC
    CONTAINS SQL
    SQL SECURITY DEFINER
    COMMENT ''
BEGIN

update `t_categoryinfo` m join t_categoryinfo_temp n on m.`RESOURCE_ID`=n.`RESOURCE_ID`
 and m.`AREA_CODE`=n.`AREA_CODE` set m.`RESOURCE_NAME`=n.`RESOURCE_NAME`;
commit;
delete FROM t_categoryinfo  where (`RESOURCE_ID`,`AREA_CODE`) not in (
select  RESOURCE_ID,AREA_CODE from  `t_categoryinfo_temp`
);
commit;   
insert into t_categoryinfo  select n.* from t_categoryinfo_temp n  where (n.`RESOURCE_ID`,n.`AREA_CODE`) not IN(select RESOURCE_ID,AREA_CODE from  `t_categoryinfo` );
commit;

END;

--同步双向节目信息
CREATE DEFINER = CURRENT_USER PROCEDURE `synProgram`()
    NOT DETERMINISTIC
    CONTAINS SQL
    SQL SECURITY DEFINER
    COMMENT ''
BEGIN

update `t_assetinfo` m join t_assetinfo_temp n on m.`PROGRAM_ID`=n.`PROGRAM_ID` and m.`ASSET_ID`=n.`ASSET_ID`
 and m.`AREA_CODE`=n.`AREA_CODE` set m.`ASSET_NAME`=n.`ASSET_NAME`;
commit;
delete FROM `t_assetinfo`  where (`PROGRAM_ID`,`AREA_CODE`) not in (
select  CHANNEL_ID,networkID from  `t_assetinfo_temp`
);
commit;   
insert into `t_assetinfo`  select n.* from t_assetinfo_temp n  where (n.`PROGRAM_ID`,n.`AREA_CODE`) not IN(select PROGRAM_ID,AREA_CODE from  `t_assetinfo` );
commit;

END;


