-- create table t_channel_group_npvr 
CREATE TABLE IF NOT EXISTS `t_channel_group_npvr` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '回放频道组ID',
  `CODE` varchar(20) DEFAULT NULL COMMENT '频道组编码',
  `NAME` varchar(20) NOT NULL DEFAULT '' COMMENT '频道组名称',
  `CHANNEL_DESC` varchar(254) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- create table t_channel_group_ref_npvr
CREATE TABLE IF NOT EXISTS `t_channel_group_ref_npvr` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `GROUP_ID` bigint(20) DEFAULT NULL COMMENT '回放频道组ID',
  `CHANNEL_ID` bigint(20) DEFAULT NULL COMMENT '回放频道ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8;

-- add column 'CHANNEL_GROUP_TYPE' for table t_ploy_backup
ALTER TABLE t_ploy_backup ADD `CHANNEL_GROUP_TYPE` decimal(1,0) DEFAULT NULL COMMENT '频道组类型（1：直播频道组， 2：回放频道组）';

-- add column 'CHANNEL_GROUP_TYPE' for table t_ploy
ALTER TABLE t_ploy ADD `CHANNEL_GROUP_TYPE` decimal(1,0) DEFAULT NULL COMMENT '频道组类型（1：直播频道组， 2：回放频道组）';

-- 添加 回放频道组管理菜单
INSERT INTO t_columns(`COLUMN_ID`, `NAME`, `DESCRIPTION`, `COLUMN_COD`, `PARENT_ID`, `STATE`) VALUES ('1104', '回放频道组管理', '系统管理二级栏目', 'hkpdzgl', '101', '1');


-- 高清回放菜单广告（ID：17）、标/高清回看菜单广告位（ID: 15/16）、高清点播菜单广告（ID: 24）
-- 标/高清点播随片图片广告（ID: 25/26）、热点推荐广告位（ID: 44）、菜单图片广告（ID: 3/4）
-- 改为非全时段（IS_ALLTIME = 0）

UPDATE t_advertposition SET IS_ALLTIME = 0 WHERE ID IN(17, 15, 16, 24, 25, 26, 44,3,4);

UPDATE t_advertposition SET IS_ALLTIME = 1 WHERE ID IN(46);  -- 开机视频是全时段的

-- 在订单和素材关系表t_order_mate_rel添加开始时间段、结束时间段、区域ID、频道组ID

alter table t_order_mate_rel add start_time VARCHAR(10) 
default NULL COMMENT '开始时间段00:00:00';
alter table t_order_mate_rel add end_time VARCHAR(10) 
default NULL COMMENT '结束时间段00:00:00';
alter table t_order_mate_rel add area_code VARCHAR(20) 
default NULL COMMENT '区域编码';
alter table t_order_mate_rel add channel_group_id decimal(10) 
default NULL COMMENT '频道组ID';
alter table t_order_mate_rel add SERVICE_ID text 
default NULL COMMENT '频道SERVICE_ID,多个以逗号分割["1,2,3"]  [0]代表所有';


-- 创建订单和素材关系临时表
CREATE TABLE `t_order_mate_rel_tmp` (
  `ID` bigint(20) NOT NULL auto_increment,
  `ORDER_CODE` varchar(20) default NULL COMMENT '订单编码',
  `MATE_ID` decimal(10,0) default NULL COMMENT '资源ID',
  `PLAY_LOCATION` varchar(255) default NULL COMMENT '插播位置/开机图片时段 0   1/3   2/3',
  `IS_HD` char(1) default NULL COMMENT '是否高清',
  `POLL_INDEX` decimal(2,0) default NULL COMMENT '轮询索引',
  `PRECISE_ID` decimal(10,0) default NULL COMMENT '精准ID： 0表示该记录对应基本策略',
  `type` int(1) default NULL COMMENT '精准/分策略类型：0表示precise_id对应精准表ID，1表示precise_id对应策略表ID',
  `start_time` varchar(10) default NULL COMMENT '开始时间段00:00:00',
  `end_time` varchar(10) default NULL COMMENT '结束时间段00:00:00',
  `area_code` varchar(20) default NULL COMMENT '区域编码',
  `channel_group_id` decimal(10,0) default NULL COMMENT '频道组ID',
  PRIMARY KEY  USING BTREE (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4275 DEFAULT CHARSET=utf8 COMMENT='订单-素材表临时表';

  update t_advertposition t   set t.IS_VIDEO = 0,t.IS_LOOP=0,t.LOOP_COUNT=NULL where t.POSITION_PACKAGE_ID='1' ;
  
  INSERT INTO `t_advertposition` (`ID`, `POSITION_NAME`, `DESCRIPTION`, `IMAGE_RULE_ID`, `VIDEO_RULE_ID`, `TEXT_RULE_ID`, `QUESTION_RULE_ID`, `IS_HD`, `IS_ADD`, `IS_LOOP`, `LOOP_COUNT`, `DELIVERY_MODE`, `PRICE`, `DISCOUNT`, `POSITION_PACKAGE_ID`, `BACKGROUND_PATH`, `COORDINATE`, `WIDTH_HEIGHT`, `IS_ALLTIME`, `IS_TEXT`, `IS_IMAGE`, `IS_VIDEO`, `IS_QUESTION`, `IS_AREA`, `IS_CHANNEL`, `IS_FREQ`, `IS_LOOKBACK`, `IS_PLAYBACK`, `IS_CHARACTERISTIC`, `POSITION_CODE`, `IS_COLUMN`, `IS_LOOKBACK_PRODUCT`, `IS_ASSET`, `IS_FOLLOW_ASSET`) VALUES 
  (45,'标清开机视频广告','动态有声视频10秒+静态图片3秒。全省统一播出，至少1个月更新一次。EPG前端发送更新描述符，机顶盒开机解析到描述符后通过数据广播获取到更新文件，下次开机生效。0',2,2,NULL,NULL,0,0,0,NULL,0,NULL,NULL,1,'images/position/position.jpg','50*50','80*80',1,0,0,1,0,0,0,0,0,0,0,'01003',0,0,0,0),
  (46,'高清开机视频广告','动态有声视频10秒+静态图片3秒。全省统一播出，至少1个月更新一次。EPG前端发送更新描述符，机顶盒开机解析到描述符后通过数据广播获取到更新文件，下次开机生效。',1,1,NULL,NULL,1,0,0,NULL,0,NULL,NULL,1,'images/position/position.jpg','5*5','420*230',1,0,0,1,0,0,0,0,0,0,0,'01004',0,0,0,0);

alter   table   t_image_meta_backup   add   imageUrl   varchar(64) comment "图片Url";
alter   table   t_image_meta   add   imageUrl   varchar(64) comment "图片Url";

-- 告警信息表
DROP TABLE IF EXISTS `t_warn_info`;
CREATE TABLE `t_warn_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `time` datetime DEFAULT NULL COMMENT '告警时间',
  `content` varchar(255) DEFAULT NULL COMMENT '告警内容',
  `is_processed` int(2) DEFAULT NULL COMMENT '是否处理（1：已处理， 0：未处理）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 点播菜单、点播随片图片广告 改成投放式
UPDATE t_position_package  SET DELIVERY_MODE = 0 WHERE POSITION_PACKAGE_CODE IN(0211,0212);












