-- 订单素材关系表加上广告作用目标类型

ALTER TABLE t_order_mate_rel_tmp ADD `PRECISETYPE` decimal(8,0) DEFAULT NULL COMMENT '类型\r\ntype=1按回看产品；\r\ntype=2按影片元数据关键字；\r\ntype=3 按受众；\r\ntype=4按影片分类-CPS栏目；\r\ntype=5 按回放频道；\r\ntype=6 按回看栏目；\r\ntype=7 按频道；\r\ntype=8 按影片模糊名称；\r\ntype=9 按区域；\r\ntype=10 按频道分组；\r\ntype=11 按用户区域；\r\ntype=12 按行业；\r\ntype=13 按级别；';

ALTER TABLE t_order_mate_rel ADD `PRECISETYPE` decimal(8,0) DEFAULT NULL COMMENT '类型\r\ntype=1按回看产品；\r\ntype=2按影片元数据关键字；\r\ntype=3 按受众；\r\ntype=4按影片分类-CPS栏目；\r\ntype=5 按回放频道；\r\ntype=6 按回看栏目；\r\ntype=7 按频道；\r\ntype=8 按影片模糊名称；\r\ntype=9 按区域；\r\ntype=10 按频道分组；\r\ntype=11 按用户区域；\r\ntype=12 按行业；\r\ntype=13 按级别；';


-- 播出单表加上优先级字段
ALTER TABLE ad_playlist_gis ADD `PRIORITY` decimal(3,0) DEFAULT NULL COMMENT '优先级';

-- 高清切换列表修正背景图片路径
UPDATE t_advertposition SET BACKGROUND_PATH='images/position/position.jpg' WHERE ID=8;

-- 河南区域与编码对应关系修正
create table t_location_code_bak as select * from t_location_code;

update t_location_code t set t.LOCATIONCODE='21111' where t.LOCATIONCODE='152060000000';
update t_location_code t set t.LOCATIONCODE='21222' where t.LOCATIONCODE='152070000000';
update t_location_code t set t.LOCATIONCODE='21333' where t.LOCATIONCODE='152080000000';

update t_location_code t set t.LOCATIONCODE='152080000000' where t.LOCATIONCODE='21111';
update t_location_code t set t.LOCATIONCODE='152060000000' where t.LOCATIONCODE='21222';
update t_location_code t set t.LOCATIONCODE='152070000000' where t.LOCATIONCODE='21333';

update t_location_code t set t.PARENTLOCATION='31111' where t.PARENTLOCATION='152060000000';
update t_location_code t set t.PARENTLOCATION='31222' where t.PARENTLOCATION='152070000000';
update t_location_code t set t.PARENTLOCATION='31333' where t.PARENTLOCATION='152080000000';

update t_location_code t set t.PARENTLOCATION='152080000000' where t.PARENTLOCATION='31111';
update t_location_code t set t.PARENTLOCATION='152060000000' where t.PARENTLOCATION='31222';
update t_location_code t set t.PARENTLOCATION='152070000000' where t.PARENTLOCATION='31333';