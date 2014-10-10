
-- 订单添加【投放失败】状态
ALTER TABLE t_order MODIFY COLUMN `STATE` char(1) DEFAULT NULL COMMENT '状态 0：待审核 1：修改待审核 2：删除待审核 3：审核未通过 4：修改审核不通过 5：删除审核不通过 6：已发布 7：执行完毕, 9:投放失败';

-- 播出单添加重新投放标识

ALTER TABLE ad_playlist_gis ADD `re_push` char(1) DEFAULT '' COMMENT '重新投放标识';