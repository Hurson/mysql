alter table `t_productinfo` add COLUMN networkID varchar(20);


--������Ʒ��Ϣ��ʱ��
CREATE TABLE `t_productinfo_temp` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `PRODUCT_ID` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT '��Ʒ����',
  `PRODUCT_NAME` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT '��Ʒ����',
  `PRODUCT_DESC` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT '��Ʒ����',
  `PRICE` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT '�۸�',
  `BILLING_MODEL_NAME` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT 'BillingModelName',
  `BILLING_MODEL_ID` VARCHAR(100) COLLATE utf8_general_ci DEFAULT NULL,
  `BILLING_MODEL_TYPE` VARCHAR(100) COLLATE utf8_general_ci DEFAULT NULL,
  `SP_ID` VARCHAR(100) COLLATE utf8_general_ci DEFAULT NULL,
  `IS_PACKAGE` CHAR(1) COLLATE utf8_general_ci DEFAULT NULL COMMENT '�Ƿ�Ϊ��Ʒ����0����Ʒ��1����Ʒ����',
  `POSTER_URL` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT '��Ʒ������ַ',
  `TYPE` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL COMMENT '����  �ؿ���Ʒ���ͱ�ʶ��ʲô',
  `BIZ_ID` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT '�ƷѴ���',
  `BIZ_DESC` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT '�Ʒ�����',
  `CREATE_TIME` DATETIME DEFAULT NULL COMMENT '��Ʒ����ʱ�䣨yyyy-mm-dd hh:mm:ss��',
  `MODIFY_TIME` DATETIME DEFAULT NULL COMMENT '��Ʒ�޸�ʱ�䣨yyyy-mm-dd hh:mm:ss��',
  `STATE` CHAR(1) COLLATE utf8_general_ci DEFAULT NULL COMMENT '״̬',
  `networkID` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL COMMENT '����ID�������ֵ��Ӧ��networkId',
  PRIMARY KEY (`ID`)
)ENGINE=InnoDB
AUTO_INCREMENT=716 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'
COMMENT='��Ʒ��Ϣ��ʱ��';

CREATE TABLE `t_channelinfo_npvr` (
  `CHANNEL_ID` VARCHAR(20) COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT 'Ƶ��ID',
  `CHANNEL_CODE` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Ƶ�� ����',
  `CHANNEL_TYPE` VARCHAR(200) COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Ƶ������',
  `CHANNEL_NAME` VARCHAR(20) COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '����',
  `SERVICE_ID` VARCHAR(20) COLLATE utf8_general_ci DEFAULT '' COMMENT 'SERVICE_ID',
  `CHANNEL_LANGUAGE` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL COMMENT '����',
  `CHANNEL_LOGO` VARCHAR(200) COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Logo',
  `KEYWORD` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT '�ؼ���',
  `LOCATION_CODE` VARCHAR(256) COLLATE utf8_general_ci DEFAULT NULL COMMENT '�������',
  `LOCATION_NAME` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL COMMENT '��������',
  `CREATE_TIME` DATETIME DEFAULT NULL COMMENT '����ʱ��',
  `MODIFY_TIME` DATETIME DEFAULT NULL COMMENT '�޸�ʱ��',
  `STATE` CHAR(1) COLLATE utf8_general_ci DEFAULT NULL COMMENT '״̬',
  `TS_ID` DECIMAL(10,0) DEFAULT NULL,
  `NETWORK_ID` DECIMAL(10,0) DEFAULT NULL,
  `CHANNEL_DESC` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT '����',
  `IS_PLAYBACK` DECIMAL(1,0) DEFAULT '0' COMMENT '�Ƿ�ط�Ƶ�� 1���� 0����',
  `SUMMARYSHORT` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT '���',
  `ID` INTEGER(11) NOT NULL AUTO_INCREMENT COMMENT '����',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
)ENGINE=InnoDB
AUTO_INCREMENT=44 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'
COMMENT='Ƶ����Ϣ��; '

CREATE TABLE `t_channelinfo_npvr_temp` (
  `CHANNEL_ID` VARCHAR(20) COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT 'Ƶ��ID',
  `CHANNEL_CODE` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Ƶ�� ����',
  `CHANNEL_TYPE` VARCHAR(200) COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Ƶ������',
  `CHANNEL_NAME` VARCHAR(20) COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '����',
  `SERVICE_ID` VARCHAR(20) COLLATE utf8_general_ci DEFAULT '' COMMENT 'SERVICE_ID',
  `CHANNEL_LANGUAGE` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL COMMENT '����',
  `CHANNEL_LOGO` VARCHAR(200) COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Logo',
  `KEYWORD` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT '�ؼ���',
  `LOCATION_CODE` VARCHAR(256) COLLATE utf8_general_ci DEFAULT NULL COMMENT '�������',
  `LOCATION_NAME` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL COMMENT '��������',
  `CREATE_TIME` DATETIME DEFAULT NULL COMMENT '����ʱ��',
  `MODIFY_TIME` DATETIME DEFAULT NULL COMMENT '�޸�ʱ��',
  `STATE` CHAR(1) COLLATE utf8_general_ci DEFAULT NULL COMMENT '״̬',
  `TS_ID` DECIMAL(10,0) DEFAULT NULL,
  `NETWORK_ID` DECIMAL(10,0) DEFAULT NULL,
  `CHANNEL_DESC` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT '����',
  `IS_PLAYBACK` DECIMAL(1,0) DEFAULT '0' COMMENT '�Ƿ�ط�Ƶ�� 1���� 0����',
  `SUMMARYSHORT` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT '���',
  `ID` INTEGER(11) NOT NULL AUTO_INCREMENT COMMENT '����',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
)ENGINE=InnoDB
AUTO_INCREMENT=44 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'
COMMENT='Ƶ����Ϣ��ʱ��; ';
--�û�����
CREATE TABLE `t_user_rank` (
  `USER_RANK_CODE` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL COMMENT '�û��������',
  `USER_RANK_NAME` VARCHAR(50) COLLATE utf8_general_ci DEFAULT NULL COMMENT '�û���������',
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '����',
  `DESCRIPTION` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '����',
  `areacode` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
)ENGINE=InnoDB
AUTO_INCREMENT=6 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'
COMMENT='�û�������Ϣ��';
--�û�������ʱ��
CREATE TABLE `t_user_rank_temp` (
  `USER_RANK_CODE` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL COMMENT '�û��������',
  `USER_RANK_NAME` VARCHAR(50) COLLATE utf8_general_ci DEFAULT NULL COMMENT '�û���������',
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '����',
  `DESCRIPTION` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '����',
  `areacode` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
)ENGINE=InnoDB
AUTO_INCREMENT=6 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'
COMMENT='�û�������Ϣ��ʱ��';

--�û���ҵ���
CREATE TABLE `t_user_industry_category` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `USER_INDUSTRY_CATEGORY_CODE` VARCHAR(5) COLLATE utf8_general_ci DEFAULT NULL COMMENT '��ҵ����',
  `USER_INDUSTRY_CATEGORY_VALUE` VARCHAR(50) COLLATE utf8_general_ci DEFAULT NULL COMMENT '��ҵ����',
  `areacode` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
)ENGINE=InnoDB
AUTO_INCREMENT=18 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'
COMMENT='�û���ҵ';

--�û���ҵ�����ʱ��
CREATE TABLE `t_user_industry_category_temp` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `USER_INDUSTRY_CATEGORY_CODE` VARCHAR(5) COLLATE utf8_general_ci DEFAULT NULL COMMENT '��ҵ����',
  `USER_INDUSTRY_CATEGORY_VALUE` VARCHAR(50) COLLATE utf8_general_ci DEFAULT NULL COMMENT '��ҵ����',
  `areacode` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
)ENGINE=InnoDB
AUTO_INCREMENT=18 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'
COMMENT='�û���ҵ��ʱ��';

--˫��������Ϣ��
CREATE TABLE `t_location_code` (
  `LOCATIONCODE` DECIMAL(12,0) DEFAULT NULL COMMENT '150000000000',
  `LOCATIONNAME` VARCHAR(200) COLLATE utf8_general_ci DEFAULT NULL,
  `PARENTLOCATION` DECIMAL(12,0) DEFAULT NULL,
  `LOCATIONTYPE` VARCHAR(2) COLLATE utf8_general_ci DEFAULT NULL,
  `areacode` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL,
  KEY `location_code_index` (`LOCATIONCODE`, `areacode`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'
COMMENT='������Ϣ��';

--˫��������Ϣ��ʱ��
CREATE TABLE `t_location_code_temp` (
  `LOCATIONCODE` DECIMAL(12,0) DEFAULT NULL COMMENT '150000000000',
  `LOCATIONNAME` VARCHAR(200) COLLATE utf8_general_ci DEFAULT NULL,
  `PARENTLOCATION` DECIMAL(12,0) DEFAULT NULL,
  `LOCATIONTYPE` VARCHAR(2) COLLATE utf8_general_ci DEFAULT NULL,
  `areacode` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL
 )ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'
COMMENT='������Ϣ��ʱ��';

--˫����Ŀ��Ϣ��
ALTER TABLE `t_categoryinfo` change `CATEGORY_ID` `RESOURCE_ID` varchar(254),
change `CATEGORY_NAME` `RESOURCE_NAME` varchar(254),
change `HAS_CATEGORY` `RESOURCE_DESC` varchar(254),
add `AREA_CODE` varchar(20);

--˫����Ŀ��Ϣ��ʱ��
CREATE TABLE `t_categoryinfo_temp` (
  `ID` bigint(20) NOT NULL COMMENT '����������',
  `RESOURCE_ID` varchar(254) default NULL COMMENT '��ĿID���ĿID',
  `RESOURCE_NAME` varchar(254) default NULL COMMENT '��Դ����',
  `POSTER_URL` varchar(254) default NULL,
  `RESOURCE_DESC` varchar(254) default NULL COMMENT '��Դ������',
  `RESOURCE_TYPE` char(1) default '1' COMMENT '��Ŀ����  1 Ҷ�ڵ� 0��Ҷ�ڵ�',
  `CREATE_TIME` datetime default NULL,
  `MODIFY_TIME` datetime default NULL,
  `STATE` char(1) default NULL,
  `AREA_CODE` varchar(20) default NULL,
  `PARENT_ID` decimal(16,0) default NULL,
  PRIMARY KEY  USING BTREE (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��AMS��ȡӰƬ������Ϣ����'; 

--˫���Ŀ��Ϣ��
ALTER TABLE `t_assetinfo` ADD `FORMAT` char(1) default NULL COMMENT '�߱��� 0 ���� 1����',
  ADD `AREA_CODE` varchar(20) default NULL,
  ADD `CATEGORY_ID`  VARCHAR(254) COMMENT '��ĿID', 
  ADD `PROGRAM_ID`  VARCHAR(254) COMMENT '��ĿID', 
  ADD `PROGRAM_NUMBER` varchar(20) COMMENT '���Ӿ��Ӽ����к�',
  ADD `PARENT_ID`       BIGINT(20) COMMENT '���Ӿ��ID'; 

--˫���Ŀ��Ϣ��ʱ��
CREATE TABLE `t_assetinfo_temp` (
  `ID` BIGINT(20) NOT NULL,
  `ASSET_ID` VARCHAR(60) COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '��Դid',
  `ASSET_NAME` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT '��Դ����',
  `SHORT_NAME` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT '��Դ����',
  `TITLE` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT '����',
  `YEAR` VARCHAR(60) COLLATE utf8_general_ci DEFAULT NULL COMMENT '���',
  `KEYWORD` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT '�ؼ���',
  `RATING` VARCHAR(50) COLLATE utf8_general_ci DEFAULT NULL COMMENT '����',
  `RUNTIME` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT 'ʱ��',
  `IS_PACKAGE` CHAR(1) COLLATE utf8_general_ci DEFAULT NULL COMMENT '�Ƿ�Ϊ��Դ����0����Դ��1����Դ����',
  `ASSET_CREATE_TIME` DATETIME DEFAULT NULL COMMENT 'ӰƬ��ӳʱ��',
  `DISPLAY_RUNTIME` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT '��Դ��ҳ������ʾʱ��',
  `ASSET_DESC` VARCHAR(3000) COLLATE utf8_general_ci DEFAULT NULL COMMENT '��Դ����',
  `POSTER_URL` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT '��Դ������url',
  `PREVIEW_ASSET_ID` VARCHAR(60) COLLATE utf8_general_ci DEFAULT NULL COMMENT '��ԴԤ��Ƭid',
  `PREVIEW_ASSET_NAME` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT '��ԴԤ��Ƭ����',
  `PREVIEW_RUNTIME` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT '��ԴԤ��Ƭ����ʱ��',
  `VIDEO_CODE` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '��Ƶ�����ʽ������MPEG2��H.264D��',
  `VIDEO_RESOLUTION` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '��Ƶ�ֱ���',
  `DIRECTOR` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT '����',
  `ACTOR` VARCHAR(254) COLLATE utf8_general_ci DEFAULT NULL COMMENT '��Ա',
  `PRODUCT_ID` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '��Դ������ƷId',
  `CATEGORY` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '����',
  `SCORE` DECIMAL(19,1) DEFAULT NULL COMMENT '�÷�',
  `CREATE_TIME` DATETIME DEFAULT NULL COMMENT '��Դ����ʱ�䣨yyyy-mm-dd hh:mm:ss��',
  `MODIFY_TIME` DATETIME DEFAULT NULL COMMENT 'ӰƬ���޸�ʱ�䣨yyyy-mm-dd hh:mm:ss��',
  `STATE` CHAR(1) COLLATE utf8_general_ci DEFAULT NULL COMMENT '״̬',
  
  `FORMAT` char(1) default NULL COMMENT '�߱��� 0 ���� 1����',
  `AREA_CODE` varchar(20) default NULL,
  `CATEGORY_ID`  VARCHAR(254) COMMENT '��ĿID', 
  `PROGRAM_ID`  VARCHAR(254) COMMENT '��ĿID', 
  `PROGRAM_NUMBER` varchar(20) COMMENT '���Ӿ��Ӽ����к�',
  `PARENT_ID`       BIGINT(20) COMMENT '���Ӿ��ID',  
   PRIMARY KEY (`ID`),
  KEY `ASSET_ID` (`ASSET_ID`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'
COMMENT='ӰƬ��Ϣ��;';



--�޸Ĳ�Ʒ��Ϣ���ֶ�
alter table `t_productinfo` change newworkID AREA_CODE VARCHAR(20);

alter table `t_productinfo_temp` change newworkID AREA_CODE VARCHAR(20);

alter table `t_channelinfo_npvr` change `NETWORK_ID` AREA_CODE VARCHAR(20);

alter table `t_channelinfo_npvr_temp` change NETWORK_ID AREA_CODE VARCHAR(20);

alter table `t_location_code` change areacode AREA_CODE VARCHAR(20);

alter table `t_location_code_temp` change areacode AREA_CODE VARCHAR(20);

alter table `t_user_industry_category` change areacode AREA_CODE VARCHAR(20);

alter table `t_user_industry_category_temp` change areacode AREA_CODE VARCHAR(20);

alter table `t_user_rank` change areacode AREA_CODE VARCHAR(20);

alter table `t_user_rank_temp` change areacode AREA_CODE VARCHAR(20);
