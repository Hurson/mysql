# MySQL-Front 3.2  (Build 2.10)

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET CHARACTER SET 'utf8' */;


# Host: localhost    Database: ads
# ------------------------------------------------------
# Server version 5.1.30-community

#
# Table structure for table ad_playlist_gis
#

CREATE TABLE `ad_playlist_gis` (
  `ID` bigint(20) NOT NULL auto_increment,
  `PLOY_ID` decimal(10,0) DEFAULT NULL COMMENT '策略ID',
  `START_TIME` datetime DEFAULT NULL COMMENT '开始时间（YYYY-MM-DD HH24：MI:SS)',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `CONTENT_PATH` varchar(1000) DEFAULT NULL COMMENT '素材路径 或素材JSON数据',
  `CONTENT_TYPE` varchar(2) DEFAULT NULL COMMENT '1开机素材 2 多图素材 3 字幕素材 4 链接素材。如果是单个素材为空',
  `AD_SITE_CODE` varchar(20) DEFAULT NULL COMMENT '广告位编码',
  `CHARACTERISTIC_IDENTIFICATION` varchar(255) DEFAULT NULL COMMENT '广告位影响投放的属性(高标清)，同一个编码会出现多个广告位时的情况',
  `SERVICE_ID` text COMMENT '频道SERVICE_ID  [0]代表所有 ["1,2,3","12,23,5","12,23,5"] 树组个数与区域对应',
  `AREAS` text COMMENT '0代表所有地市，多个地市中间使用"," 分隔',
  `USERINDUSTRYS` varchar(255) DEFAULT NULL COMMENT '投放行业，0为全部行业，多个行业中用"," 分隔  ',
  `USERLEVELS` varchar(255) DEFAULT NULL COMMENT '用户级别，0为所有用户级别，多个级别中用"," 分隔',
  `TVN` varchar(20) DEFAULT NULL COMMENT 'TVN号 数值型',
  `STATE` decimal(1,0) DEFAULT NULL COMMENT '状态；0为未投放，1为已投放，2为投放失败，3为取消投放 4投放完成',
  `CONTRACT_ID` decimal(8,0) DEFAULT NULL COMMENT '合同号',
  `ORDER_ID` decimal(36,0) DEFAULT NULL COMMENT '订单号',
  `CONTENT_ID` varchar(255) DEFAULT NULL COMMENT '内容ID，可多个',
  `CATEGORY_ID` text COMMENT '回看栏目IDS',
  `ASSET_ID` text COMMENT '点播随片',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图文投放式播出单';

#
# Table structure for table ad_playlist_req
#

CREATE TABLE `ad_playlist_req` (
  `ID` bigint(20) NOT NULL auto_increment COMMENT '请求式播出单ID=策略订单ID',
  `ORDER_ID` decimal(38,0) DEFAULT NULL,
  `BEGIN` varchar(8) DEFAULT NULL COMMENT '开始时间（ HH24：MI:SS)',
  `END` varchar(8) DEFAULT NULL COMMENT '结束时间',
  `PLAY_TIME` decimal(8,0) DEFAULT NULL COMMENT '投放次数',
  `AD_SITE_CODE` varchar(20) DEFAULT NULL COMMENT '广告位编码',
  `CHARACTERISTIC_IDENTIFICATION` varchar(255) DEFAULT NULL COMMENT '特征值:高标清(SD/HD),插播位置(0 1/3 2/3)  如 HD,1/3 ',
  `AREAS` text COMMENT '0代表所有地市，多个地市中间使用"," 分隔',
  `CHANNELS` text COMMENT '频道ID  ["0"]代表所有频道  格式:["1,2,3","12,23,5","12,23,5"] 树组个数与区域对应',
  `USERINDUSTRYS` varchar(255) DEFAULT NULL COMMENT '投放行业，0为全部行业，多个行业中用"," 分隔',
  `USERLEVELS` varchar(255) DEFAULT NULL COMMENT '用户级别，0为所有用户级别，多个级别中用#分割',
  `STATE` decimal(1,0) DEFAULT NULL COMMENT '状态；0为未投放，1为已投放，2为投放失败，3为取消投放',
  `CONTRACT_ID` decimal(8,0) DEFAULT NULL COMMENT '合同号',
  `BEGIN_DATE` datetime DEFAULT NULL COMMENT 'YYYY-MM-DD',
  `END_DATE` datetime DEFAULT NULL COMMENT 'YYYY-MM-DD',
  `PLOY_ID` decimal(10,0) DEFAULT NULL COMMENT '策略ID',
  `TVN` varchar(20) DEFAULT NULL COMMENT 'TVN号 数值型',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='请求式双向播出单，基本和投放策略表一致';

#
# Table structure for table ad_playlist_req_content
#

CREATE TABLE `ad_playlist_req_content` (
  `ID` bigint(20) NOT NULL auto_increment NOT NULL,
  `PLAYLIST_ID` decimal(38,0) DEFAULT NULL COMMENT '播出单ID',
  `CONTENT_TYPE` varchar(255) DEFAULT NULL COMMENT '1:开机素材 2: 多图素材 3: 字幕素材。如果是单个素材为空',
  `CONTENT_PATH` varchar(555) DEFAULT NULL COMMENT '素材路径 或素材JSON数据',
  `CONTENT_ID` varchar(255) DEFAULT NULL COMMENT '内容ID，可多个',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Table structure for table ad_playlist_req_history
#

CREATE TABLE `ad_playlist_req_history` (
  `ID` bigint(20) NOT NULL auto_increment NOT NULL,
  `DATETIME` datetime DEFAULT NULL COMMENT '投放时间',
  `AD_SITE_ID` decimal(8,0) DEFAULT NULL,
  `USERCODE` varchar(50) DEFAULT NULL COMMENT '用户编码\r\n            TVN',
  `STATE` decimal(8,0) DEFAULT NULL COMMENT '状态；0为未投放，1为已投放，2为投放失败',
  `CONTRACT_ID` decimal(8,0) DEFAULT NULL COMMENT '合同号',
  `PLAYLIST_ID` decimal(36,0) DEFAULT NULL COMMENT '播出单号',
  `CONTENT_ID` decimal(8,0) DEFAULT NULL COMMENT '内容ID，可多个',
  `CONTENT_TYPE` decimal(8,0) DEFAULT NULL COMMENT '内容类型',
  `CONTENT_PATH` varchar(255) DEFAULT NULL COMMENT '内容路径',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='请求式精准投放记录表，记录每次投放的广告信息';

#
# Table structure for table ad_playlist_req_p_content
#

CREATE TABLE `ad_playlist_req_p_content` (
  `ID` bigint(20) NOT NULL auto_increment NOT NULL,
  `PRECISION_ID` decimal(38,0) DEFAULT NULL COMMENT '精准ID',
  `CONTENT_PATH` varchar(555) DEFAULT NULL COMMENT '素材路径 或素材JSON数据',
  `CONTENT_TYPE` varchar(255) DEFAULT NULL COMMENT '1开机素材 2 多图素材 3 字幕素材。如果是单个素材为空',
  `CONTENT_ID` varchar(255) DEFAULT NULL COMMENT '内容ID，可多个',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='精准内容表';

#
# Table structure for table ad_playlist_req_precision
#

CREATE TABLE `ad_playlist_req_precision` (
  `ID` bigint(20) NOT NULL auto_increment COMMENT '精准订单ID',
  `PLAYLIST_ID` decimal(38,0) NOT NULL COMMENT '策略订单ID=请求式播出单ID',
  `TYPE` decimal(8,0) DEFAULT NULL COMMENT '类型type=1按产品；type=2按影片元数据关键字；type=3 按受众；type=4按影片分类；type=5 按回放频道；type=6 按回看栏目；type=7 按频道；type=8 按影片assertid；',
  `PRODUCT_ID` varchar(255) DEFAULT NULL COMMENT '产品编码"," 分隔',
  `DTV_SERVICE_ID` varchar(255) DEFAULT NULL COMMENT '频道"," 分隔',
  `ASSET_SORT_ID` varchar(255) DEFAULT NULL COMMENT '影片分类"," 分隔',
  `ASSET_KEY` varchar(255) DEFAULT NULL COMMENT '关键字"," 分隔',
  `USER_AREA` varchar(255) DEFAULT NULL COMMENT '用户区域"," 分隔',
  `TVN_NUMBER` varchar(255) DEFAULT NULL COMMENT 'TVN号段  受众属性格式 "startnumber,endnumber"',
  `USERINDUSTRYS` varchar(255) DEFAULT NULL COMMENT '用户行业"," 分隔 受众属性',
  `USERLEVELS` varchar(255) DEFAULT NULL COMMENT '用户级别"," 分隔 受众属性',
  `USE_LEVEL` decimal(10,0) DEFAULT NULL COMMENT '优先级',
  `PRECISION_ID` decimal(38,0) DEFAULT NULL,
  `ASSET_ID` varchar(255) DEFAULT NULL COMMENT '影片IDS"," 分隔',
  `PLAYBACK_SERVICE_ID` varchar(255) DEFAULT NULL COMMENT '回放频道 IDS","分隔',
  `LOOKBACK_CATEGORY_ID` varchar(255) DEFAULT NULL COMMENT '回看栏目 IDS","分隔',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='请求式精准投放表';

#
# Table structure for table ad_questionnaire_data
#

CREATE TABLE `ad_questionnaire_data` (
  `ID` bigint(20) NOT NULL auto_increment COMMENT '主键',
  `ANSWERS` varchar(500) DEFAULT NULL COMMENT '问卷答案，每个问题的答案以#隔开',
  `QUESTIONS` varchar(2000) DEFAULT NULL COMMENT '问卷问题，每个问题以#隔开',
  `SURVEY_TYPE` varchar(2) DEFAULT NULL COMMENT '调查类型',
  `SURVEY_ID` varchar(10) DEFAULT NULL COMMENT '调查问卷的ID',
  `SURVEY_THEME` varchar(100) DEFAULT NULL COMMENT '调查问卷的主题',
  `WRITE_TIME` datetime DEFAULT NULL COMMENT '填写问卷时间',
  `TVN_ID` varchar(50) DEFAULT NULL COMMENT '填写用户的唯一标识符',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保存用户填写的问卷调查表数据';

#
# Table structure for table admin_system_config
#

CREATE TABLE `admin_system_config` (
  `ID` bigint(20) NOT NULL auto_increment,
  `REMIND_KEY` varchar(50) DEFAULT NULL,
  `REMIND_VALUE` varchar(200) DEFAULT NULL,
  `REMIND_DESC` varchar(200) DEFAULT NULL,
  `REMIND_NAME` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Table structure for table t_advertposition
#

CREATE TABLE `t_advertposition` (
  `ID` bigint(20) NOT NULL auto_increment COMMENT '主键',
  `POSITION_NAME` varchar(100) DEFAULT NULL COMMENT '广告位名称',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '广告位描述',
  `IMAGE_RULE_ID` decimal(10,0) DEFAULT NULL COMMENT '图片规格ID',
  `VIDEO_RULE_ID` decimal(10,0) DEFAULT NULL COMMENT '视频规格ID',
  `TEXT_RULE_ID` decimal(10,0) DEFAULT NULL COMMENT '文本规格ID',
  `QUESTION_RULE_ID` decimal(10,0) DEFAULT NULL COMMENT '问卷规格ID',
  `IS_HD` decimal(1,0) DEFAULT NULL COMMENT '是否高清  0：标清 1：高清',
  `IS_ADD` decimal(1,0) DEFAULT NULL COMMENT '是否叠加  1：是 0：否',
  `IS_LOOP` decimal(1,0) DEFAULT NULL COMMENT '是否轮询  1：是 0：否',
  `LOOP_COUNT` decimal(2,0) DEFAULT NULL COMMENT '轮询个数',
  `DELIVERY_MODE` decimal(1,0) DEFAULT NULL COMMENT '投放方式  0 投放式  1 请求式',
  `PRICE` varchar(10) DEFAULT NULL COMMENT '价格',
  `DISCOUNT` varchar(10) DEFAULT NULL COMMENT '折扣',
  `POSITION_PACKAGE_ID` decimal(10,0) DEFAULT NULL COMMENT '广告位包ID',
  `BACKGROUND_PATH` varchar(255) DEFAULT NULL COMMENT '背景图路径',
  `COORDINATE` varchar(20) DEFAULT NULL COMMENT '坐标   x*y',
  `WIDTH_HEIGHT` varchar(20) DEFAULT NULL COMMENT '宽,高 如 800*600',
  `IS_ALLTIME` decimal(1,0) DEFAULT '0' COMMENT '是否全时段  1：是 0：否',
  `IS_TEXT` decimal(1,0) DEFAULT '0' COMMENT '是否字幕  1：是 0：否',
  `IS_IMAGE` decimal(1,0) DEFAULT '0' COMMENT '是否图片  1：是 0：否',
  `IS_VIDEO` decimal(1,0) DEFAULT '0' COMMENT '是否视频  1：是 0：否',
  `IS_QUESTION` decimal(1,0) DEFAULT '0' COMMENT '是否问卷  1：是 0：否',
  `IS_AREA` decimal(1,0) DEFAULT '1' COMMENT '是否区域 1：是 0：否',
  `IS_CHANNEL` decimal(1,0) DEFAULT '0' COMMENT '是否频道  1：是 0：否',
  `IS_FREQ` decimal(1,0) DEFAULT '0' COMMENT '是否频率  1：是 0：否',
  `IS_LOOKBACK` decimal(1,0) DEFAULT '0' COMMENT '是否回看频道 1：是 0：否     废弃 ',
  `IS_PLAYBACK` decimal(1,0) DEFAULT '0' COMMENT '是否回放频道 1：是 0：否  回放菜单，插播，暂停 ',
  `IS_CHARACTERISTIC` decimal(1,0) DEFAULT '0' COMMENT '是否处理特征值CPS与外部系统使用 (1：是CPS的广告位 0：否，不是CPS的广告位)',
  `POSITION_CODE` varchar(50) DEFAULT '0' COMMENT '广告位编码',
  `IS_COLUMN` decimal(1,0) DEFAULT '0' COMMENT '是否回看栏目 1：是 0：否   回看菜单',
  `IS_LOOKBACK_PRODUCT` decimal(1,0) DEFAULT '0' COMMENT '是否回看产品 1：是 0：否   回看插播，暂停',
  `IS_ASSET` decimal(1,0) DEFAULT '0' COMMENT '是否影片精准  针对双向实时请求类广告 可设置影片、硬盘类别、关键字、受众精准',
  `IS_FOLLOW_ASSET` decimal(1,0) DEFAULT '0' COMMENT '是否随片精准  针对随片广告 可设置影片精准',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='广告位子表';

#
# Table structure for table t_asset_product
#

CREATE TABLE `t_asset_product` (
  `ID` bigint(20) NOT NULL auto_increment NOT NULL,
  `ASSET_ID` decimal(16,0) DEFAULT NULL COMMENT '资产ID',
  `PRODUCT_ID` decimal(16,0) DEFAULT NULL COMMENT '产品ID',
  PRIMARY KEY (`ID`),
  KEY `REFERENCE_37_FK` (`ASSET_ID`),
  KEY `REFERENCE_41_FK` (`PRODUCT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品和资源关系表';

#
# Table structure for table t_assetinfo
#

CREATE TABLE `t_assetinfo` (
  `ID` bigint(20) NOT NULL auto_increment,
  `ASSET_ID` varchar(60) NOT NULL COMMENT '资源id',
  `ASSET_NAME` varchar(254) DEFAULT NULL COMMENT '资源名称',
  `SHORT_NAME` varchar(254) DEFAULT NULL COMMENT '资源短名',
  `TITLE` varchar(254) DEFAULT NULL COMMENT '标题',
  `YEAR` varchar(60) DEFAULT NULL COMMENT '年份',
  `KEYWORD` varchar(254) DEFAULT NULL COMMENT '关键字',
  `RATING` varchar(50) DEFAULT NULL COMMENT '级别',
  `RUNTIME` varchar(254) DEFAULT NULL COMMENT '时长',
  `IS_PACKAGE` char(1) DEFAULT NULL COMMENT '是否为资源包（0：资源、1：资源包）',
  `ASSET_CREATE_TIME` datetime NOT NULL  COMMENT '影片上映时间',
  `DISPLAY_RUNTIME` varchar(254) DEFAULT NULL COMMENT '资源在页面上显示时间',
  `ASSET_DESC` varchar(3000) DEFAULT NULL COMMENT '资源描述',
  `POSTER_URL` varchar(254) DEFAULT NULL COMMENT '资源海报的url',
  `PREVIEW_ASSET_ID` varchar(60) DEFAULT NULL COMMENT '资源预览片id',
  `PREVIEW_ASSET_NAME` varchar(254) DEFAULT NULL COMMENT '资源预览片名称',
  `PREVIEW_RUNTIME` varchar(254) DEFAULT NULL COMMENT '资源预览片播放时间',
  `VIDEO_CODE` varchar(255) DEFAULT NULL COMMENT '视频编码格式，包括MPEG2，H.264D等',
  `VIDEO_RESOLUTION` varchar(255) DEFAULT NULL COMMENT '视频分辨率',
  `DIRECTOR` varchar(254) DEFAULT NULL COMMENT '导演',
  `ACTOR` varchar(254) DEFAULT NULL COMMENT '演员',
  `PRODUCT_ID` varchar(255) DEFAULT NULL COMMENT '资源所属产品Id',
  `CATEGORY` varchar(255) DEFAULT NULL COMMENT '类型',
  `SCORE` decimal(19,1) DEFAULT NULL COMMENT '得分',
  `CREATE_TIME` datetime NOT NULL  COMMENT '资源创建时间（yyyy-mm-dd hh:mm:ss）',
  `MODIFY_TIME` datetime NOT NULL  COMMENT '影片的修改时间（yyyy-mm-dd hh:mm:ss）',
  `STATE` char(1) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='影片信息表';

#
# Table structure for table t_audit_log
#

CREATE TABLE `t_audit_log` (
  `ID` bigint(20) NOT NULL auto_increment COMMENT '主键',
  `RELATION_TYPE` decimal(2,0) NOT NULL COMMENT '关联类型  1：订单审核',
  `RELATION_ID` decimal(10,0) NOT NULL COMMENT '关联审核ID',
  `STATE` decimal(1,0) DEFAULT NULL COMMENT '状态 0：审核通过 1：审核不通过 2：修改审核通过 3：修改审核不通过 4：删除审核通过 5：删除审核不通过',
  `OPERATOR_ID` decimal(10,0) DEFAULT NULL COMMENT '操作员ID',
  `AUDIT_TIME` datetime DEFAULT NULL COMMENT '审核时间',
  `AUDIT_OPINION` varchar(255) DEFAULT NULL COMMENT '审核意见',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='审核记录表';

#
# Table structure for table t_category
#

CREATE TABLE `t_category` (
  `ID` bigint(20) NOT NULL auto_increment,
  `CATEGORY_NAME` varchar(255) DEFAULT NULL COMMENT '分类名称',
  `CATEGORY_DESC` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='素材分类表';

#
# Table structure for table t_categoryinfo
#

CREATE TABLE `t_categoryinfo` (
  `ID` bigint(20) NOT NULL auto_increment COMMENT '自增长主键',
  `CATEGORY_ID` varchar(254) DEFAULT NULL,
  `CATEGORY_NAME` varchar(254) DEFAULT NULL COMMENT '目录名称',
  `POSTER_URL` varchar(254) DEFAULT NULL,
  `HAS_CATEGORY` varchar(254) DEFAULT NULL COMMENT '目录的描述',
  `TYPE` char(1) DEFAULT NULL,
  `CREATE_TIME` datetime NOT NULL ,
  `MODIFY_TIME` datetime NOT NULL ,
  `STATE` char(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='从AMS获取影片分类信息数据';

#
# Table structure for table t_channelinfo
#

CREATE TABLE `t_channelinfo` (
  `CHANNEL_ID` bigint(20) NOT NULL auto_increment COMMENT '频道ID',
  `CHANNEL_CODE` varchar(20) DEFAULT NULL COMMENT '频道 编码',
  `CHANNEL_TYPE` varchar(200) DEFAULT NULL COMMENT '频道类型',
  `CHANNEL_NAME` varchar(20) NOT NULL COMMENT '名称',
  `SERVICE_ID` varchar(20) NOT NULL COMMENT 'SERVICE_ID',
  `CHANNEL_LANGUAGE` varchar(20) NOT NULL COMMENT '语言',
  `CHANNEL_LOGO` varchar(200) DEFAULT NULL COMMENT 'Logo',
  `KEYWORD` varchar(254) DEFAULT NULL COMMENT '关键字',
  `LOCATION_CODE` varchar(20) DEFAULT NULL COMMENT '区域编码   ',
  `LOCATION_NAME` varchar(20) DEFAULT NULL COMMENT '区域名称',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `STATE` char(1) DEFAULT NULL COMMENT '状态',
  `TS_ID` decimal(10,0) DEFAULT NULL,
  `NETWORK_ID` decimal(10,0) DEFAULT NULL,
  `CHANNEL_DESC` varchar(254) DEFAULT NULL COMMENT '描述',
  `IS_PLAYBACK` decimal(1,0) DEFAULT '0' COMMENT '是否回放频道 1：是 0：否',
  `SUMMARYSHORT` varchar(254) DEFAULT NULL COMMENT '简称',
  PRIMARY KEY (`CHANNEL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='频道信息表';

#
# Table structure for table t_channelinfo_npvr
#

CREATE TABLE `t_channelinfo_npvr` (
  `CHANNEL_ID` bigint(20) NOT NULL auto_increment COMMENT '频道ID',
  `CHANNEL_CODE` varchar(20) DEFAULT NULL COMMENT '频道 编码',
  `CHANNEL_TYPE` varchar(200) DEFAULT NULL COMMENT '频道类型',
  `CHANNEL_NAME` varchar(20) NOT NULL COMMENT '名称',
  `SERVICE_ID` varchar(20) NOT NULL COMMENT 'SERVICE_ID',
  `CHANNEL_LANGUAGE` varchar(20) NOT NULL COMMENT '语言',
  `CHANNEL_LOGO` varchar(200) DEFAULT NULL COMMENT 'Logo',
  `KEYWORD` varchar(254) DEFAULT NULL COMMENT '关键字',
  `LOCATION_CODE` varchar(20) DEFAULT NULL COMMENT '区域编码   ',
  `LOCATION_NAME` varchar(20) DEFAULT NULL COMMENT '区域名称',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `STATE` char(1) DEFAULT NULL COMMENT '状态',
  `TS_ID` decimal(10,0) DEFAULT NULL,
  `NETWORK_ID` decimal(10,0) DEFAULT NULL,
  `CHANNEL_DESC` varchar(254) DEFAULT NULL COMMENT '描述',
  `IS_PLAYBACK` decimal(1,0) DEFAULT '0' COMMENT '是否回放频道 1：是 0：否',
  `SUMMARYSHORT` varchar(254) DEFAULT NULL COMMENT '简称',
  PRIMARY KEY (`CHANNEL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='频道信息表';

#
# Table structure for table t_columns
#

CREATE TABLE `t_columns` (
  `COLUMN_ID` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(25) DEFAULT NULL COMMENT '栏目功能名称   可扩展 url至此表中',
  `DESCRIPTION` varchar(50) DEFAULT NULL COMMENT '描述',
  `COLUMN_COD` varchar(50) DEFAULT NULL COMMENT '编码   COLUMN_CODE',
  `PARENT_ID` decimal(10,0) DEFAULT NULL COMMENT '父ID',
  `STATE` char(1) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`COLUMN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='栏目权限表 ';

#
# Table structure for table t_columns_role
#

CREATE TABLE `t_columns_role` (
  `RELATION_ID` bigint(20) NOT NULL auto_increment,
  `ROLE_ID` decimal(10,0) DEFAULT NULL COMMENT '角色ID',
  `COLUMN_ID` decimal(10,0) DEFAULT NULL COMMENT '栏目权限ID',
  PRIMARY KEY (`RELATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-栏目权限表';

#
# Table structure for table t_contract
#

CREATE TABLE `t_contract` (
  `ID` bigint(20) NOT NULL auto_increment,
  `CONTRACT_NUMBER` varchar(255) DEFAULT NULL COMMENT '合同代码',
  `CONTRACT_CODE` varchar(255) DEFAULT NULL COMMENT '合同编号',
  `CUSTOM_ID` decimal(10,0) DEFAULT NULL COMMENT '客户ID  CUSTOMER_ID',
  `CONTRACT_NAME` varchar(255) DEFAULT NULL COMMENT '合同名称',
  `EFFECTIVE_START_DATE` datetime DEFAULT NULL COMMENT '生效时间',
  `EFFECTIVE_END_DATE` datetime DEFAULT NULL COMMENT '失效时间',
  `SUBMIT_UNITS` varchar(255) DEFAULT NULL COMMENT '送审单位',
  `FINANCIAL_INFORMATION` varchar(255) DEFAULT NULL COMMENT '合同金额',
  `APPROVAL_CODE` varchar(255) DEFAULT NULL COMMENT '审批文号',
  `APPROVAL_START_DATE` datetime DEFAULT NULL COMMENT '审批文号生效时间',
  `APPROVAL_END_DATE` datetime DEFAULT NULL COMMENT '审批文号失效时间',
  `METARIAL_PATH` varchar(255) DEFAULT NULL COMMENT '资源路径',
  `STATUS` decimal(2,0) DEFAULT NULL COMMENT '状态',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `OPERATOR_ID` decimal(10,0) DEFAULT NULL COMMENT '操作员',
  `OTHER_CONTENT` varchar(255) DEFAULT NULL COMMENT '其他内容',
  `CONTRACT_DESC` varchar(255) DEFAULT NULL COMMENT '合同描述',
  PRIMARY KEY (`ID`),
  KEY `IDX_CONTRACT_CUSTOMERID` (`CUSTOM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同运行期表';

#
# Table structure for table t_contract_accounts
#

CREATE TABLE `t_contract_accounts` (
  `ACCOUNTS_ID` bigint(20) NOT NULL auto_increment COMMENT '台账主键',
  `CONTRACT_ID` decimal(10,0) DEFAULT NULL COMMENT '合同ID',
  `CONTRACT_NAME` varchar(255) DEFAULT NULL COMMENT '合同名称',
  `MONEY_AMOUNT` decimal(10,0) DEFAULT NULL COMMENT '付款金额',
  `PAY_DAY` datetime DEFAULT NULL COMMENT '付款日期',
  `PAY_SORT` decimal(2,0) DEFAULT NULL COMMENT '结算方式 1:按月付款，  2:按季度付款',
  `PAY_VALLIDITY_PERIOD_BEGIN` datetime DEFAULT NULL COMMENT '付款有效期开始时间',
  `PAY_VALLIDITY_PERIOD_END` datetime DEFAULT NULL COMMENT '付款有效期结束时间',
  `ACCOUNTS_CODE` varchar(50) DEFAULT NULL COMMENT '台账订单号',
  PRIMARY KEY (`ACCOUNTS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Table structure for table t_contract_ad
#

CREATE TABLE `t_contract_ad` (
  `ID` bigint(20) NOT NULL auto_increment,
  `CONTRACT_ID` decimal(10,0) DEFAULT NULL COMMENT '合同ID',
  `AD_ID` decimal(10,0) DEFAULT NULL COMMENT '广告位ID',
  `VALID_START` datetime DEFAULT NULL COMMENT '投放开始日期时间',
  `VALID_END` datetime DEFAULT NULL COMMENT '投放结束日期时间',
  `RULE_ID` decimal(10,0) DEFAULT NULL COMMENT '营销规则编码',
  `CONTRACT_CODE` varchar(255) DEFAULT NULL COMMENT '合同编号',
  `CONTRACT_NAME` varchar(255) DEFAULT NULL COMMENT '合同名称',
  `AD_NAME` varchar(255) DEFAULT NULL COMMENT '广告位名称 ',
  `AD_TYPE` decimal(10,0) DEFAULT NULL COMMENT '广告位类型（废弃）',
  `RULE_NAME` varchar(255) DEFAULT NULL COMMENT '营销规则名称',
  `CONTRACT_STARTTIME` datetime DEFAULT NULL COMMENT '合同生效时间',
  `CONTRACT_ENDTIME` datetime DEFAULT NULL COMMENT '合同失效时间',
  `ADVERTISERS_ID` decimal(10,0) DEFAULT NULL COMMENT '广告商ID（废弃）',
  `AD_TYPE_NAME` varchar(255) DEFAULT NULL COMMENT '广告位类型名称（废弃）',
  `ADVERTISERS_NAME` varchar(255) DEFAULT NULL COMMENT '广告商名称（废弃）',
  PRIMARY KEY (`ID`),
  KEY `IDX_CONTRACTAD_ADID` (`AD_ID`),
  KEY `IDX_CONTRACTAD_CONTRACTENDTIME` (`CONTRACT_ENDTIME`),
  KEY `IDX_CONTRACTAD_CONTRACTID` (`CONTRACT_ID`),
  KEY `IDX_CONTRACTAD_RULEID` (`RULE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运行期合同广告位关系表';

#
# Table structure for table t_contract_ad_backup
#

CREATE TABLE `t_contract_ad_backup` (
  `ID` bigint(20) NOT NULL auto_increment,
  `CONTRACT_ID` decimal(10,0) DEFAULT NULL COMMENT '合同ID',
  `AD_ID` decimal(10,0) DEFAULT NULL COMMENT '广告位ID',
  `VALID_START` datetime DEFAULT NULL COMMENT '投放开始日期时间',
  `VALID_END` datetime DEFAULT NULL COMMENT '投放结束日期时间',
  `RULE_ID` decimal(10,0) DEFAULT NULL COMMENT '营销规则编码',
  `CONTRACT_CODE` varchar(255) DEFAULT NULL COMMENT '合同编号',
  `CONTRACT_NAME` varchar(255) DEFAULT NULL COMMENT '合同名称',
  `AD_NAME` varchar(255) DEFAULT NULL COMMENT '广告位名称',
  `AD_TYPE` decimal(10,0) DEFAULT NULL COMMENT '广告位类型（废弃）',
  `RULE_NAME` varchar(255) DEFAULT NULL COMMENT '营销规则名称',
  `CONTRACT_STARTTIME` datetime DEFAULT NULL COMMENT '合同生效时间',
  `CONTRACT_ENDTIME` datetime DEFAULT NULL COMMENT '合同失效时间',
  `ADVERTISERS_ID` decimal(10,0) DEFAULT NULL COMMENT '广告商（废弃）',
  `AD_TYPE_NAME` varchar(255) DEFAULT NULL COMMENT '广告位类型名称（废弃）',
  `ADVERTISERS_NAME` varchar(255) DEFAULT NULL COMMENT '广告商名称（废弃）',
  KEY `IDX_CONADBACKUP_CID` (`CONTRACT_ID`),
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='维护期合同广告位关系表';

#
# Table structure for table t_contract_backup
#

CREATE TABLE `t_contract_backup` (
  `ID` bigint(20) NOT NULL auto_increment,
  `CONTRACT_NUMBER` varchar(255) DEFAULT NULL COMMENT '合同代码',
  `CONTRACT_CODE` varchar(255) DEFAULT NULL COMMENT '合同编号',
  `CUSTOMER_ID` decimal(10,0) DEFAULT NULL COMMENT '客户ID',
  `CONTRACT_NAME` varchar(255) DEFAULT NULL COMMENT '合同名称',
  `EFFECTIVE_START_DATE` datetime DEFAULT NULL COMMENT '生效时间',
  `EFFECTIVE_END_DATE` datetime DEFAULT NULL COMMENT '失效时间',
  `SUBMIT_UNITS` varchar(255) DEFAULT NULL COMMENT '送审单位',
  `FINANCIAL_INFORMATION` varchar(255) DEFAULT NULL COMMENT '合同金额',
  `APPROVAL_CODE` varchar(255) DEFAULT NULL COMMENT '审批文号',
  `APPROVAL_START_DATE` datetime DEFAULT NULL COMMENT '审批文号生效时间',
  `APPROVAL_END_DATE` datetime DEFAULT NULL COMMENT '审批文号失效时间',
  `METARIAL_PATH` varchar(255) DEFAULT NULL COMMENT '资源路径',
  `STATUS` decimal(2,0) DEFAULT NULL COMMENT '状态',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `OPERATOR_ID` decimal(10,0) DEFAULT NULL COMMENT '操作员',
  `OTHER_CONTENT` varchar(255) DEFAULT NULL COMMENT '其他内容',
  `AUDIT_TAFF` varchar(255) DEFAULT NULL COMMENT '审核人',
  `EXAMINATION_OPINIONS` varchar(255) DEFAULT NULL COMMENT '审核意见',
  `AUDIT_DATE` datetime DEFAULT NULL COMMENT '审核时间',
  `CONTRACT_DESC` varchar(255) DEFAULT NULL COMMENT '合同描述',
  PRIMARY KEY (`ID`),
  KEY `IDX_CONTRACT_BACK_CNAME` (`CONTRACT_NAME`),
  KEY `IDX_CONTRACT_ENDDATE` (`EFFECTIVE_END_DATE`),
  KEY `IDX_CONTRACT_STARTDATE` (`EFFECTIVE_START_DATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同维护期表';

#
# Table structure for table t_customer
#

CREATE TABLE `t_customer` (
  `ID` bigint(20) NOT NULL auto_increment,
  `ADVERTISERS_NAME` varchar(255) DEFAULT NULL COMMENT '广告商名称',
  `CLIENT_CODE` varchar(255) DEFAULT NULL COMMENT '客户代码',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '描述',
  `CONPANY_ADDRESS` varchar(255) DEFAULT NULL COMMENT '公司地址  company',
  `CONPANY_SHEET` varchar(255) DEFAULT NULL COMMENT '公司主页',
  `COMMUNICATOR` varchar(255) DEFAULT NULL COMMENT '联系人',
  `TEL` varchar(255) DEFAULT NULL COMMENT '电话',
  `MOBILE_TEL` varchar(255) DEFAULT NULL COMMENT '移动电话',
  `FAX` varchar(255) DEFAULT NULL COMMENT '传真',
  `CONTACTS` varchar(255) DEFAULT NULL COMMENT '通讯地址',
  `COOPERATIONTIME` varchar(255) DEFAULT NULL COMMENT '合作期限',
  `CREDIT_RATING` decimal(10,0) DEFAULT NULL COMMENT '信用等级',
  `CONTRACT` varchar(255) DEFAULT NULL COMMENT '合同文件存储路径',
  `BUSINESS_LICENCE` varchar(255) DEFAULT NULL COMMENT '营业执照',
  `STATUS` varchar(255) DEFAULT NULL COMMENT '状态 待审核0，审核通过 1 ，审核不通过 2',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `OPERATOR` decimal(10,0) DEFAULT NULL COMMENT '操作员ID',
  `CUSTOMER_LEVEL` decimal(10,0) DEFAULT NULL COMMENT '1:国家级 2 省级 3 市级 4其他',
  `REGISTER_FINANCING` decimal(10,0) DEFAULT NULL COMMENT '注册资金',
  `REGISTER_ADDRESS` varchar(255) DEFAULT NULL COMMENT '注册地',
  `BUSINESS_AREA` decimal(10,0) DEFAULT NULL COMMENT '营业面积',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='广告商运行期表';

#
# Table structure for table t_customer_backup
#

CREATE TABLE `t_customer_backup` (
  `ID` bigint(20) NOT NULL auto_increment,
  `ADVERTISERS_NAME` varchar(255) DEFAULT NULL COMMENT '广告商名称',
  `CLIENT_CODE` varchar(255) DEFAULT NULL COMMENT '客户代码',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '描述',
  `CONPANY_ADDRESS` varchar(255) DEFAULT NULL COMMENT '公司地址  company',
  `CONPANY_SHEET` varchar(255) DEFAULT NULL COMMENT '公司主页',
  `COMMUNICATOR` varchar(255) DEFAULT NULL COMMENT '联系人',
  `TEL` varchar(255) DEFAULT NULL COMMENT '电话',
  `MOBILE_TEL` varchar(255) DEFAULT NULL COMMENT '移动电话',
  `FAX` varchar(255) DEFAULT NULL COMMENT '传真',
  `CONTACTS` varchar(255) DEFAULT NULL COMMENT '通讯地址',
  `COOPERATIONTIME` varchar(255) DEFAULT NULL COMMENT '合作期限',
  `CREDIT_RATING` varchar(255) DEFAULT NULL COMMENT '信用等级',
  `CONTRACT` varchar(255) DEFAULT NULL COMMENT '合同文件存储路径',
  `BUSINESS_LICENCE` varchar(255) DEFAULT NULL COMMENT '营业执照',
  `STATUS` varchar(255) DEFAULT NULL COMMENT '状态 待审核0，审核通过 1 ，审核不通过 2',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `OPERATOR` decimal(10,0) DEFAULT NULL COMMENT '操作员ID',
  `AUDIT_TAFF` varchar(255) DEFAULT NULL COMMENT '审核人',
  `EXAMINATION_OPINIONS` varchar(255) DEFAULT NULL COMMENT '审核意见',
  `AUDIT_DATE` datetime DEFAULT NULL COMMENT '审核时间',
  `CUSTOMER_LEVEL` decimal(10,0) DEFAULT NULL COMMENT '1:国级 2 省级 3 市级 4其他',
  `REGISTER_FINANCING` decimal(10,0) DEFAULT NULL COMMENT '注册资金',
  `REGISTER_ADDRESS` varchar(255) DEFAULT NULL COMMENT '注册地',
  `BUSINESS_AREA` decimal(10,0) DEFAULT NULL COMMENT '营业面积',
  `DELFLAG` decimal(2,0) DEFAULT '0' COMMENT '是否删除0未删除  1删除无效',
  PRIMARY KEY (`ID`),
  KEY `IDX_CUSTOMER_BACKUP_ANAME` (`ADVERTISERS_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='广告商维护`期表';

#
# Table structure for table t_image_meta
#

CREATE TABLE `t_image_meta` (
  `ID` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(255) DEFAULT NULL COMMENT '图片名称/图片文件名  ？',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '描述',
  `FILE_SIZE` varchar(10) DEFAULT NULL COMMENT '文件大小',
  `FILE_HEIGTH` varchar(10) DEFAULT NULL COMMENT '高',
  `FILE_WIDTH` varchar(10) DEFAULT NULL COMMENT '宽',
  `FILE_FORMAT` varchar(50) DEFAULT NULL COMMENT '类型',
  `FORMAL_FILE_PATH` varchar(255) DEFAULT NULL COMMENT '存储路径 是否带文件名的相对路径    是否扩展链接属性',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运行期维护期资产子表-图';

#
# Table structure for table t_image_meta_backup
#

CREATE TABLE `t_image_meta_backup` (
  `ID` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(255) DEFAULT NULL COMMENT '图片名称  /文件名？',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '描述',
  `FILE_SIZE` varchar(10) DEFAULT NULL COMMENT '文件大小',
  `FILE_HEIGTH` varchar(10) DEFAULT NULL COMMENT '高',
  `FILE_WIDTH` varchar(10) DEFAULT NULL COMMENT '宽',
  `FILE_FORMAT` varchar(50) DEFAULT NULL COMMENT '类型',
  `TEMPORARY_FILE_PATH` varchar(255) DEFAULT NULL COMMENT '存储路径 是否包含文件名的相对路径',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='维护期维护期资产子表-图片';

#
# Table structure for table t_image_specification
#

CREATE TABLE `t_image_specification` (
  `ID` bigint(20) NOT NULL auto_increment,
  `IMAGE_DESC` varchar(255) DEFAULT NULL COMMENT '描述',
  `IMAGE_HEIGHT` varchar(10) DEFAULT NULL COMMENT '高  IMAGE_HEIGHT',
  `IMAGE_WIDTH` varchar(10) DEFAULT NULL COMMENT '宽',
  `TYPE` varchar(255) DEFAULT NULL COMMENT '类型',
  `FILE_SIZE` varchar(255) DEFAULT NULL COMMENT '文件大小',
  `IS_LINK` decimal(2,0) DEFAULT NULL COMMENT '是否链接 1链接 0非链接',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片规格信息表';

#
# Table structure for table t_location
#

CREATE TABLE `t_location` (
  `LOCATIONCODE` varchar(2) NOT NULL,
  `LOCATIONNAME` varchar(40) NOT NULL,
  `CREATETIME` datetime  ,
  `MODIFYTIME` datetime ,
  `STATE` char(1) DEFAULT NULL,
  PRIMARY KEY (`LOCATIONCODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Table structure for table t_location_code
#

CREATE TABLE `t_location_code` (
  `LOCATIONCODE` decimal(12,0) DEFAULT NULL COMMENT '150000000000',
  `LOCATIONNAME` varchar(200) DEFAULT NULL,
  `PARENTLOCATION` decimal(12,0) DEFAULT NULL,
  `LOCATIONTYPE` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Table structure for table t_loopback_category
#

CREATE TABLE `t_loopback_category` (
  `ID` bigint(20) NOT NULL auto_increment,
  `CATEGORY_ID` varchar(255) DEFAULT NULL COMMENT '栏目ID',
  `CATEGORY_NAME` varchar(255) DEFAULT NULL COMMENT '栏目名称',
  `CATEGORY_TYPE` char(1) DEFAULT '0' COMMENT '栏目类型  高标清',
  `TEMPLATE_ID` varchar(255) DEFAULT NULL COMMENT '模板ID',
  `TEMPLATE_NAME` varchar(255) DEFAULT NULL COMMENT '模板名称',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='回看栏目表由 外部系统同步';

#
# Table structure for table t_marketing_rule
#

CREATE TABLE `t_marketing_rule` (
  `ID` bigint(20) NOT NULL auto_increment,
  `START_TIME` varchar(8) DEFAULT NULL COMMENT '开始时间  12：00：00     varchar',
  `END_TIME` varchar(8) DEFAULT NULL COMMENT '结束时间  12：00：00      varchar',
  `RULE_ID` decimal(10,0) DEFAULT NULL COMMENT '营销规则编码  varchar    规则编码，策略编码，订单编码均采用number是否调整',
  `RULE_NAME` varchar(255) DEFAULT NULL COMMENT '营销规则名称  ',
  `POSITION_ID` decimal(10,0) DEFAULT NULL COMMENT '广告位ID',
  `LOCATION_ID` decimal(10,0) DEFAULT NULL COMMENT '区域ID     是否调整为区域编码 如为ID，则同步数据处需明确不能自生成ID',
  `CHANNEL_ID` decimal(10,0) DEFAULT NULL COMMENT '频道ID     是否调整为频道编码如为ID，则同步数据处需明确不能自生成ID',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `OPERATION_ID` decimal(10,0) DEFAULT NULL COMMENT '操作员ID',
  `STATE` decimal(1,0) DEFAULT NULL COMMENT '状态',
  `LOCATION_NAME` varchar(200) DEFAULT NULL COMMENT '区域名称  无用',
  `CHANNEL_NAME` varchar(200) DEFAULT NULL COMMENT '频道名称  无用',
  `NPVR_CHANNEL_ID` decimal(10,0) DEFAULT NULL COMMENT '回看频道ID',
  `BACK_CHANNEL_ID` decimal(10,0) DEFAULT NULL COMMENT '回放频道ID',
  `COLUMN_ID` decimal(10,0) DEFAULT NULL COMMENT '栏目ID',
  PRIMARY KEY (`ID`),
  KEY `IDX_MARKETING_RULE_LOCATIONID` (`LOCATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='营销规则表';

#
# Table structure for table t_no_ad_ploy
#

CREATE TABLE `t_no_ad_ploy` (
  `ID` bigint(20) NOT NULL auto_increment,
  `PLOYNAME` varchar(255) DEFAULT NULL COMMENT '策略名称',
  `POSITIONID` decimal(10,0) DEFAULT NULL COMMENT '广告位ID',
  `TVN` varchar(255) DEFAULT NULL COMMENT 'TVN号',
  `START_DATE` datetime DEFAULT NULL COMMENT '开始日期',
  `END_DATE` datetime DEFAULT NULL COMMENT '结束日期',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='禁播策略表';

#
# Table structure for table t_operate_log
#

CREATE TABLE `t_operate_log` (
  `ID` bigint(20) NOT NULL auto_increment,
  `USER_ID` decimal(10,0) DEFAULT NULL COMMENT '操作员ID',
  `MODULE_NAME` varchar(50) DEFAULT NULL COMMENT '模块名称',
  `OPERATE_TYPE` varchar(64) DEFAULT NULL COMMENT '操作类型',
  `OPERATE_RESULT` decimal(1,0) DEFAULT NULL COMMENT '操作结果  0：成功  1：失败',
  `OPERATE_IP` varchar(20) DEFAULT NULL COMMENT '操作员IP',
  `OPERATE_TIME` datetime DEFAULT NULL COMMENT '操作时间',
  `OPERATE_INFO` varchar(2000) DEFAULT NULL COMMENT '操作详情',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';

#
# Table structure for table t_order
#

CREATE TABLE `t_order` (
  `ID` bigint(20) NOT NULL auto_increment COMMENT 'ID',
  `ORDER_CODE` varchar(20) DEFAULT NULL COMMENT '订单编码',
  `PLOY_ID` decimal(10,0) DEFAULT NULL COMMENT '策略ID',
  `START_TIME` datetime DEFAULT NULL COMMENT '订单开始时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '订单结束时间',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `STATE` char(1) DEFAULT NULL COMMENT '状态 0：待审核 1：修改待审核 2：删除待审核 3：审核未通过 4：修改审核不通过 5：删除审核不通过 6：已发布 7：执行完毕',
  `CONTRACT_ID` decimal(10,0) DEFAULT NULL COMMENT '合同ID',
  `POSITION_ID` decimal(10,0) DEFAULT NULL COMMENT '广告位ID',
  `OPERATOR_ID` decimal(10,0) DEFAULT NULL COMMENT '操作员ID',
  `ORDER_TYPE` decimal(1,0) DEFAULT NULL COMMENT '订单类型  0：投放式  1：请求式',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表 ';

#
# Table structure for table t_order_mate_rel
#

CREATE TABLE `t_order_mate_rel` (
  `ID` bigint(20) NOT NULL auto_increment,
  `ORDER_ID` decimal(10,0) DEFAULT NULL COMMENT '订单ID',
  `MATE_ID` decimal(10,0) DEFAULT NULL COMMENT '资源ID',
  `PLAY_LOCATION` varchar(255) DEFAULT NULL COMMENT '插播位置 0   1/3   2/3',
  `IS_HD` char(1) DEFAULT NULL COMMENT '是否高清',
  `POLL_INDEX` decimal(2,0) DEFAULT NULL COMMENT '轮询索引',
  `PRECISE_ID` decimal(10,0) DEFAULT NULL COMMENT '精准ID    ',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单-素材表';

#
# Table structure for table t_package_asset
#

CREATE TABLE `t_package_asset` (
  `ID` bigint(20) NOT NULL auto_increment,
  `PACKAGE_ID` decimal(16,0) DEFAULT NULL COMMENT '资源包Id',
  `ASSET_ID` decimal(16,0) DEFAULT NULL COMMENT '资源Id',
  PRIMARY KEY (`ID`),
  KEY `REFERENCE_49_FK` (`ASSET_ID`),
  KEY `REFERENCE_50_FK` (`PACKAGE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Table structure for table t_plat_category
#

CREATE TABLE `t_plat_category` (
  `ID` bigint(20) NOT NULL auto_increment,
  `CATEGORY_ID` varchar(255) DEFAULT NULL,
  `CATEGORY_NAME` varchar(255) DEFAULT NULL,
  `CATEGORY_TYPE` char(1) DEFAULT NULL,
  `TEMPLATE_ID` varchar(255) DEFAULT NULL,
  `TEMPLATE_NAME` varchar(255) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `MODIFY_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Table structure for table t_ploy
#

CREATE TABLE `t_ploy` (
  `ID` bigint(20) NOT NULL auto_increment COMMENT '策略ID',
  `PLOY_ID` decimal(10,0) DEFAULT NULL COMMENT '策略编号',
  `PLOY_NAME` varchar(50) DEFAULT NULL COMMENT '策略名称',
  `CONTRACT_ID` decimal(10,0) DEFAULT NULL COMMENT '合同ID',
  `POSITION_ID` decimal(10,0) DEFAULT NULL COMMENT '广告位ID',
  `RULE_ID` decimal(10,0) DEFAULT NULL COMMENT '规则编码',
  `START_TIME` varchar(8) DEFAULT NULL COMMENT '开始时间  12：00：00',
  `END_TIME` varchar(8) DEFAULT NULL COMMENT '结束时间  12：00：00',
  `AREA_ID` decimal(10,0) DEFAULT NULL COMMENT '区域ID',
  `CHANNEL_ID` decimal(10,0) DEFAULT NULL COMMENT '频道ID',
  `OPERATION_ID` decimal(10,0) DEFAULT NULL COMMENT '操作员id',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间 ',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `STATE` char(1) DEFAULT '0' COMMENT '状态',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '描述',
  `PLOY_NUMBER` decimal(2,0) DEFAULT NULL COMMENT '投放次数',
  `USERINDUSTRYS` varchar(255) DEFAULT NULL COMMENT '用户行业 ',
  `USERLEVELS` varchar(255) DEFAULT NULL COMMENT '用户级别 ',
  `TVN_NUMBER` varchar(255) DEFAULT NULL COMMENT '用户TVN  0为全部 ',
  `OPERATOR_ID` decimal(10,0) DEFAULT NULL COMMENT '操作员ID',
  `AUDIT_ID` decimal(10,0) DEFAULT NULL COMMENT '审核人id',
  `AUDIT_OPTION` varchar(255) DEFAULT NULL COMMENT '审核意见',
  `AUDIT_DATE` datetime DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='投放策略表';

#
# Table structure for table t_ploy_backup
#

CREATE TABLE `t_ploy_backup` (
  `ID` bigint(20) NOT NULL auto_increment COMMENT '策略ID',
  `PLOY_ID` decimal(10,0) DEFAULT NULL COMMENT '策略编号',
  `PLOY_NAME` varchar(50) DEFAULT NULL COMMENT '策略名称',
  `CONTRACT_ID` decimal(10,0) DEFAULT NULL COMMENT '合同ID',
  `POSITION_ID` decimal(10,0) DEFAULT NULL COMMENT '广告位ID',
  `RULE_ID` decimal(10,0) DEFAULT NULL COMMENT '规则编码',
  `START_TIME` varchar(8) DEFAULT NULL COMMENT '开始时间  12：00：00',
  `END_TIME` varchar(8) DEFAULT NULL COMMENT '结束时间  12：00：00',
  `AREA_ID` decimal(10,0) DEFAULT NULL COMMENT '区域ID',
  `CHANNEL_ID` decimal(10,0) DEFAULT NULL COMMENT '频道ID',
  `OPERATION_ID` decimal(10,0) DEFAULT NULL COMMENT '操作员id',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间 ',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `STATE` varchar(2) DEFAULT '0' COMMENT '状态 0：待审核 1：审核通过 2：审核未通过 ',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '描述',
  `PLOY_NUMBER` decimal(2,0) DEFAULT NULL COMMENT '投放次数',
  `USERINDUSTRYS` varchar(255) DEFAULT NULL COMMENT '用户行业',
  `USERLEVELS` varchar(255) DEFAULT NULL COMMENT '用户级别',
  `TVN_NUMBER` varchar(255) DEFAULT NULL COMMENT '用户TVN  0为全部 ',
  `OPERATOR_ID` decimal(10,0) DEFAULT NULL COMMENT '操作员ID',
  `AUDIT_ID` decimal(10,0) DEFAULT NULL COMMENT '审核人id',
  `AUDIT_OPTION` varchar(255) DEFAULT NULL COMMENT '审核意见',
  `AUDIT_DATE` datetime DEFAULT NULL COMMENT '审核时间',
  `DELFLAG` decimal(2,0) DEFAULT '0' COMMENT '是否删除0未删除  1删除无效',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='投放策略表';

#
# Table structure for table t_portal_plat_category
#

CREATE TABLE `t_portal_plat_category` (
  `ID` bigint(20) NOT NULL auto_increment,
  `CATEGORY_ID` decimal(10,0) DEFAULT NULL,
  `POSITION_ID` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Table structure for table t_position_package
#

CREATE TABLE `t_position_package` (
  `ID` bigint(20) NOT NULL auto_increment COMMENT '主键',
  `POSITION_PACKAGE_CODE` varchar(20) DEFAULT NULL COMMENT '广告位包编码',
  `POSITION_PACKAGE_NAME` varchar(100) DEFAULT NULL COMMENT '广告位包名称',
  `POSITION_PACKAGE_TYPE` decimal(1,0) DEFAULT NULL COMMENT '广告位类型  0：双向实时广告 1：双向实时请求广告 2：单向实时广告 3：单向非实时广告',
  `VIDEO_TYPE` decimal(1,0) DEFAULT '2' COMMENT '高标清标识  0：只支持标清  1：只支持高清  2：高清标清都支持',
  `IS_LOOP` decimal(1,0) DEFAULT '0' COMMENT '是否轮训  1：是 0：否',
  `IS_ADD` decimal(1,0) DEFAULT '0' COMMENT '是否叠加   1：是 0：否',
  `POSITION_COUNT` decimal(2,0) DEFAULT NULL COMMENT '子广告位个数',
  `PLOY_DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '投放策略描述',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '广告位包描述',
  `DELIVERY_MODE` decimal(1,0) DEFAULT NULL COMMENT '投放方式  0 投放式  1 请求式',
  `IS_VIDEO` decimal(1,0) DEFAULT NULL COMMENT '是否可以投放视频   1：是 0：否',
  `IS_IMAGE` decimal(1,0) DEFAULT NULL COMMENT '是否可以投放图片   1：是 0：否',
  `IS_TEXT` decimal(1,0) DEFAULT NULL COMMENT '是否可以投放文字   1：是 0：否',
  `IS_QUESTION` decimal(1,0) DEFAULT NULL COMMENT '是否可以投放问卷   1：是 0：否',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='广告位包';

#
# Table structure for table t_precise_match
#

CREATE TABLE `t_precise_match` (
  `ID` bigint(20) NOT NULL auto_increment,
  `MATCH_NAME` varchar(100) DEFAULT NULL COMMENT '精准名称',
  `PRECISETYPE` decimal(8,0) DEFAULT NULL COMMENT '''类型type=1按产品；type=2按影片元数据关键字；type=3 按受众；type=4按影片分类；type=5 按回放频道；type=6 按回看栏目；type=7 按频道；type=8 按影片assertid；',
  `PRODUCT_ID` varchar(255) DEFAULT NULL COMMENT '产品 IDS","分隔   ',
  `ASSET_ID` varchar(255) DEFAULT NULL COMMENT '影片IDS","分隔',
  `ASSET_KEY` varchar(255) DEFAULT NULL COMMENT '影片关键字","分隔',
  `ASSET_SORT_ID` varchar(255) DEFAULT NULL COMMENT '影片分类IDS","分隔',
  `DTV_CHANNEL_ID` varchar(255) DEFAULT NULL COMMENT '直播频道IDS',
  `PLAYBACK_CHANNEL_ID` varchar(255) DEFAULT NULL COMMENT '回放频道 IDS","分隔',
  `LOOKBACK_CATEGORY_ID` varchar(255) DEFAULT NULL COMMENT '回看栏目 IDS","分隔',
  `USER_AREA` varchar(255) DEFAULT NULL COMMENT '用户区域 IDS","分隔',
  `USERINDUSTRYS` varchar(255) DEFAULT NULL COMMENT '用户行业 IDS","分隔',
  `USERLEVELS` varchar(255) DEFAULT NULL COMMENT '用户级别 IDS","分隔',
  `TVN_NUMBER` varchar(255) DEFAULT NULL COMMENT 'TVN ',
  `PRIORITY` decimal(3,0) DEFAULT NULL COMMENT '优先级',
  `PLOY_ID` decimal(10,0) DEFAULT NULL COMMENT '策略ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Table structure for table t_precise_match_bk
#

CREATE TABLE `t_precise_match_bk` (
  `ID` bigint(20) NOT NULL auto_increment,
  `MATCH_NAME` varchar(100) DEFAULT NULL COMMENT '精准名称',
  `PRECISETYPE` decimal(8,0) DEFAULT NULL COMMENT '''类型type=1按回看产品；type=2按影片元数据关键字；type=3 按受众；type=4按影片分类；type=5 按回放频道；type=6 按回看栏目；type=7 按频道；type=8 按影片assertid；type=9 按区域；',
  `PRODUCT_ID` varchar(255) DEFAULT NULL COMMENT '回看产品 IDS","分隔',
  `ASSET_ID` varchar(255) DEFAULT NULL COMMENT '影片IDS","分隔',
  `ASSET_KEY` varchar(255) DEFAULT NULL COMMENT '影片关键字","分隔',
  `ASSET_SORT_ID` varchar(255) DEFAULT NULL COMMENT '影片分类IDS","分隔',
  `DTV_CHANNEL_ID` varchar(255) DEFAULT NULL COMMENT '直播频道IDS',
  `PLAYBACK_CHANNEL_ID` varchar(255) DEFAULT NULL COMMENT '回放频道 IDS","分隔',
  `LOOKBACK_CATEGORY_ID` varchar(255) DEFAULT NULL COMMENT '回看栏目 IDS","分隔',
  `USER_AREA` varchar(255) DEFAULT NULL COMMENT '用户区域 IDS","分隔',
  `USERINDUSTRYS` varchar(255) DEFAULT NULL COMMENT '用户行业 IDS","分隔',
  `USERLEVELS` varchar(255) DEFAULT NULL COMMENT '用户级别 IDS","分隔',
  `TVN_NUMBER` varchar(255) DEFAULT NULL COMMENT 'TVN ',
  `PRIORITY` decimal(3,0) DEFAULT NULL COMMENT '优先级',
  `PLOY_ID` decimal(10,0) DEFAULT NULL COMMENT '策略ID',
  `DELFLAG` decimal(2,0) DEFAULT '0' COMMENT '是否删除0未删除  1删除无效',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Table structure for table t_productinfo
#

CREATE TABLE `t_productinfo` (
  `ID` bigint(20) NOT NULL auto_increment,
  `PRODUCT_ID` varchar(254) DEFAULT NULL COMMENT '产品编码',
  `PRODUCT_NAME` varchar(254) DEFAULT NULL COMMENT '产品名称',
  `PRODUCT_DESC` varchar(254) DEFAULT NULL COMMENT '产品描述',
  `PRICE` varchar(254) DEFAULT NULL COMMENT '价格',
  `BILLING_MODEL_NAME` varchar(254) DEFAULT NULL COMMENT 'BillingModelName',
  `BILLING_MODEL_ID` varchar(100) DEFAULT NULL,
  `BILLING_MODEL_TYPE` varchar(100) DEFAULT NULL,
  `SP_ID` varchar(100) DEFAULT NULL,
  `IS_PACKAGE` char(1) DEFAULT NULL COMMENT '是否为产品包（0：产品、1：产品包）',
  `POSTER_URL` varchar(254) DEFAULT NULL COMMENT '产品海报地址',
  `TYPE` varchar(20) DEFAULT NULL COMMENT '类型  回看产品类型标识是什么 ',
  `BIZ_ID` varchar(254) DEFAULT NULL COMMENT '计费代码',
  `BIZ_DESC` varchar(254) DEFAULT NULL COMMENT '计费描述',
  `CREATE_TIME` datetime   COMMENT '产品创建时间（yyyy-mm-dd hh:mm:ss）',
  `MODIFY_TIME` datetime   COMMENT '产品修改时间（yyyy-mm-dd hh:mm:ss）',
  `STATE` char(1) DEFAULT NULL COMMENT '状态    ',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品信息表';

#
# Table structure for table t_question
#

CREATE TABLE `t_question` (
  `ID` bigint(20) NOT NULL auto_increment,
  `QUESTION` varchar(200) DEFAULT NULL,
  `OPTIONS` varchar(200) DEFAULT NULL,
  `QUESTIONNAIRE_INDEX` decimal(2,0) DEFAULT NULL,
  `QUESTIONNAIRE_ID` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ASSOCIATION17_FK` (`QUESTIONNAIRE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Table structure for table t_question_real
#

CREATE TABLE `t_question_real` (
  `ID` bigint(20) NOT NULL auto_increment,
  `QUESTION` varchar(200) DEFAULT NULL,
  `OPTIONS` varchar(200) DEFAULT NULL,
  `QUESTIONNAIRE_INDEX` decimal(2,0) DEFAULT NULL,
  `QUESTIONNAIRE_ID` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Table structure for table t_questionnaire
#

CREATE TABLE `t_questionnaire` (
  `ID` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(100) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `QUESTIONNAIRE_ID` varchar(100) DEFAULT NULL,
  `SUMMARY` varchar(600) DEFAULT NULL,
  `QUESTIONNAIRE_TYPE` char(1) DEFAULT NULL,
  `PICTURE_PATH` varchar(500) DEFAULT NULL,
  `VIDEO_PATH` varchar(500) DEFAULT NULL,
  `TEMPLATE_ID` decimal(10,0) DEFAULT NULL,
  `TEMPORARY_FILE_PATH` varchar(255) DEFAULT NULL,
  `FILE_PATH` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='维护期资产子表-问卷';

#
# Table structure for table t_questionnaire_real
#

CREATE TABLE `t_questionnaire_real` (
  `ID` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(100) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `QUESTIONNAIRE_ID` varchar(100) DEFAULT NULL,
  `SUMMARY` varchar(600) DEFAULT NULL,
  `QUESTIONNAIRE_TYPE` char(1) DEFAULT NULL,
  `PICTURE_PATH` varchar(500) DEFAULT NULL,
  `VIDEO_PATH` varchar(500) DEFAULT NULL,
  `FORMAL_FILE_PATH` varchar(255) DEFAULT NULL,
  `FILE_PATH` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运行期资产子表-问卷';

#
# Table structure for table t_questionnaire_specification
#

CREATE TABLE `t_questionnaire_specification` (
  `ID` bigint(20) NOT NULL auto_increment,
  `TYPE` varchar(255) DEFAULT NULL,
  `FILE_SIZE` varchar(255) DEFAULT NULL,
  `OPTION_NUMBER` decimal(5,0) DEFAULT NULL,
  `MAX_LENGTH` decimal(5,0) DEFAULT NULL,
  `EXCLUDE_CONTENT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Table structure for table t_questionnaire_template
#

CREATE TABLE `t_questionnaire_template` (
  `ID` bigint(20) NOT NULL auto_increment,
  `TEMPLATE_NAME` varchar(50) DEFAULT NULL,
  `TEMPLATE_PACKAGE_NAME` varchar(50) DEFAULT NULL,
  `HTML_PATH` varchar(500) DEFAULT NULL,
  `SHOW_IMAGE_PATH` varchar(500) DEFAULT NULL,
  `STATE` char(1) DEFAULT NULL,
  `CREATE_TIME` datetime NOT NULL ,
  `MODIFY_TIME` datetime NOT NULL ,
  `OPERATOR` decimal(10,0) DEFAULT NULL,
  `REMARK` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Table structure for table t_release_area
#

CREATE TABLE `t_release_area` (
  `ID` bigint(20) NOT NULL auto_increment,
  `AREA_CODE` varchar(12) NOT NULL COMMENT '区域编码 152010000000  与t_location_code.locationcode对应',
  `AREA_NAME` varchar(200) DEFAULT NULL COMMENT '区域名称',
  `PARENT_CODE` varchar(12) DEFAULT NULL COMMENT '父区域编码',
  `LOCATIONTYPE` varchar(2) DEFAULT NULL COMMENT '区域类型',
  `LOCATION_CODE` varchar(20) DEFAULT NULL COMMENT '频道区域编码 20481',
  PRIMARY KEY (`ID`),
  KEY `RELEASE_AREA_NAME_INDEX` (`AREA_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='投放区域';

#
# Table structure for table t_resource
#

CREATE TABLE `t_resource` (
  `ID` bigint(20) NOT NULL auto_increment,
  `RESOURCE_NAME` varchar(255) DEFAULT NULL COMMENT '资产名称',
  `RESOURCE_TYPE` decimal(2,0) DEFAULT NULL COMMENT '资产类型0：图片、1：视频、2：文字、3：调查问卷',
  `RESOURCE_ID` decimal(10,0) DEFAULT NULL COMMENT '资产ID',
  `RESOURCE_DESC` varchar(255) DEFAULT NULL COMMENT '资产描述',
  `CUSTOMER_ID` decimal(10,0) DEFAULT NULL COMMENT '广告商ID',
  `CATEGORY_ID` decimal(10,0) DEFAULT NULL COMMENT '内容分类ID',
  `CONTRACT_ID` decimal(10,0) DEFAULT NULL COMMENT '合同ID',
  `START_TIME` datetime DEFAULT NULL COMMENT '生效时间  DATE',
  `END_TIME` datetime DEFAULT NULL COMMENT '失效时间  DATE',
  `ADVERT_POSITION_ID` decimal(10,0) DEFAULT NULL COMMENT '广告位ID',
  `STATE` char(1) DEFAULT NULL COMMENT '状态0待审核，1审核不通过，2上线，3下线',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `OPERATION_ID` decimal(10,0) DEFAULT NULL COMMENT '操作员ID',
  `IS_DEFAULT` decimal(1,0) DEFAULT '0' COMMENT '是否默认  1：是  0：否',
  `KEY_WORDS` varchar(255) DEFAULT NULL COMMENT '关键字',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运行期广告资产表-主表';

#
# Table structure for table t_resource_ad
#

CREATE TABLE `t_resource_ad` (
  `ID` bigint(20) NOT NULL auto_increment COMMENT '主键',
  `POSITION_PACKAGE_ID` decimal(10,0) DEFAULT NULL COMMENT '广告位包ID',
  `AD_ID` decimal(10,0) DEFAULT NULL COMMENT '子广告位ID',
  `RESOURCE_ID` decimal(10,0) DEFAULT NULL COMMENT '默认素材ID',
  `RESOURCE_TYPE` decimal(1,0) DEFAULT NULL COMMENT '资产类型1：图片、2：文字、3：视频、4：调查问卷',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='广告位默认素材';

#
# Table structure for table t_resource_backup
#

CREATE TABLE `t_resource_backup` (
  `ID` bigint(20) NOT NULL auto_increment,
  `RESOURCE_NAME` varchar(255) DEFAULT NULL COMMENT '资产名称',
  `RESOURCE_TYPE` decimal(2,0) DEFAULT NULL COMMENT '资产类型0：图片、1：视频、2：文字、3：调查问卷',
  `RESOURCE_ID` decimal(10,0) DEFAULT NULL COMMENT '资产ID',
  `RESOURCE_DESC` varchar(255) DEFAULT NULL COMMENT '资产描述',
  `CUSTOMER_ID` decimal(10,0) DEFAULT NULL COMMENT '广告商ID',
  `CATEGORY_ID` decimal(10,0) DEFAULT NULL COMMENT '内容分类ID',
  `CONTRACT_ID` decimal(10,0) DEFAULT NULL COMMENT '合同ID',
  `START_TIME` datetime DEFAULT NULL COMMENT '生效时间  DATE',
  `END_TIME` datetime DEFAULT NULL COMMENT '失效时间  DATE',
  `ADVERT_POSITION_ID` decimal(10,0) DEFAULT NULL COMMENT '广告位ID',
  `STATE` char(1) DEFAULT NULL COMMENT '状态0待审核，1审核不通过，2上线，3下线',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `OPERATION_ID` decimal(10,0) DEFAULT NULL COMMENT '操作员ID',
  `EXAMINATION_OPINIONS` varchar(255) DEFAULT NULL COMMENT '审核意见',
  `AUDIT_DATE` datetime DEFAULT NULL COMMENT '审核时间',
  `AUDIT_TAFF` varchar(255) DEFAULT NULL COMMENT '审核人',
  `IS_DEFAULT` decimal(1,0) DEFAULT '0' COMMENT '是否默认  1：是  0：否',
  `KEY_WORDS` varchar(255) DEFAULT NULL COMMENT '关键字',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Table structure for table t_role
#

CREATE TABLE `t_role` (
  `ROLE_ID` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(25) DEFAULT NULL COMMENT '角色名称',
  `DESCRIPTION` varchar(50) DEFAULT NULL COMMENT '角色描述',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `STATE` char(1) DEFAULT NULL COMMENT '角色状态（0-失效，1-有效）',
  `TYPE` decimal(1,0) DEFAULT NULL COMMENT '角色类型（1-广告商，2-运营商）',
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色信息表';

#
# Table structure for table t_text_meta
#

CREATE TABLE `t_text_meta` (
  `ID` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(255) DEFAULT NULL COMMENT '内容标题',
  `CONTENT` longblob COMMENT '字幕内容',
  `URL` varchar(255) DEFAULT NULL COMMENT '链接',
  `ACTION` varchar(10) DEFAULT NULL COMMENT '文本显示动作，静止0 1滚动   ',
  `DURATION_TIME` decimal(10,0) DEFAULT NULL COMMENT '文本显示持续时间',
  `FONT_SIZE` decimal(10,0) DEFAULT NULL COMMENT '文本显示字体大小',
  `FONT_COLOR` varchar(10) DEFAULT NULL COMMENT '文本显示字体颜色',
  `BACKGROUND_COLOR` varchar(10) DEFAULT NULL COMMENT '文本显示背景色',
  `ROLL_SPEED` decimal(10,0) DEFAULT NULL COMMENT '文本显示滚动速度',
  `POSITION_VERTEX_COORDINATES` varchar(20) DEFAULT NULL COMMENT '文本显示显示坐标 x,y',
  `POSITION_WIDTH_HEIGHT` varchar(20) DEFAULT NULL COMMENT '文本显示显示区域宽高  w,h',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运行期资产子表-文本';

#
# Table structure for table t_text_meta_backup
#

CREATE TABLE `t_text_meta_backup` (
  `ID` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(255) DEFAULT NULL COMMENT '字幕标题',
  `CONTENT` longblob COMMENT '字幕内容',
  `URL` varchar(255) DEFAULT NULL COMMENT '字幕链接',
  `ACTION` varchar(10) DEFAULT NULL COMMENT '文本显示动作，静止0 1滚动   ',
  `DURATION_TIME` decimal(10,0) DEFAULT NULL COMMENT '文本显示持续时间',
  `FONT_SIZE` decimal(10,0) DEFAULT NULL COMMENT '文本显示字体大小',
  `FONT_COLOR` varchar(10) DEFAULT NULL COMMENT '文本显示字体颜色',
  `BACKGROUND_COLOR` varchar(10) DEFAULT NULL COMMENT '文本显示背景色',
  `ROLL_SPEED` decimal(10,0) DEFAULT NULL COMMENT '文本显示滚动速度',
  `POSITION_VERTEX_COORDINATES` varchar(20) DEFAULT NULL COMMENT '文本显示显示坐标 x*y',
  `POSITION_WIDTH_HEIGHT` varchar(20) DEFAULT NULL COMMENT '文本显示显示区域宽高  w*h',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='维护期资产子表-文本';

#
# Table structure for table t_text_specification
#

CREATE TABLE `t_text_specification` (
  `ID` bigint(20) NOT NULL auto_increment,
  `TEXT_DESC` varchar(255) DEFAULT NULL COMMENT '描述',
  `TEXT_LENGTH` varchar(255) DEFAULT NULL COMMENT '文字最大长度',
  `IS_LINK` decimal(2,0) DEFAULT NULL COMMENT '是否链接1:有链接、0没有连接',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文字规格信息表';

#
# Table structure for table t_user
#

CREATE TABLE `t_user` (
  `USER_ID` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(25) DEFAULT NULL COMMENT '用户名称',
  `USER_NAME` varchar(25) DEFAULT NULL COMMENT '登陆名',
  `PASSWORD` varchar(50) DEFAULT NULL COMMENT '登陆密码',
  `MAIL` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `STATE` char(1) DEFAULT NULL COMMENT '状态（0-失效，1-有效）',
  `DELFLAG` decimal(2,0) DEFAULT '0' COMMENT '是否删除0未删除  1删除无效',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

#
# Table structure for table t_user_adcustomer
#

CREATE TABLE `t_user_adcustomer` (
  `ID` bigint(20) NOT NULL auto_increment,
  `USER_ID` decimal(10,0) DEFAULT NULL COMMENT '用户ID',
  `CUTOMER_ID` decimal(10,0) DEFAULT NULL COMMENT '广告商ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-广告商信息表';

#
# Table structure for table t_user_area
#

CREATE TABLE `t_user_area` (
  `ID` bigint(20) NOT NULL auto_increment,
  `USER_ID` decimal(10,0) DEFAULT NULL,
  `RELEASE_AREACODE` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-区域表 ';

#
# Table structure for table t_user_industry_category
#

CREATE TABLE `t_user_industry_category` (
  `ID` bigint(20) NOT NULL auto_increment,
  `USER_INDUSTRY_CATEGORY_CODE` varchar(5) DEFAULT NULL COMMENT '行业编码',
  `USER_INDUSTRY_CATEGORY_VALUE` varchar(50) DEFAULT NULL COMMENT '行业名称',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户行业';

#
# Table structure for table t_user_rank
#

CREATE TABLE `t_user_rank` (
  `USER_RANK_CODE` varchar(20) DEFAULT NULL COMMENT '用户级别编码',
  `USER_RANK_NAME` varchar(50) DEFAULT NULL COMMENT '用户级别名称',
  `ID` bigint(20) NOT NULL auto_increment COMMENT '主键',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户级别信息表 ';

#
# Table structure for table t_user_role
#

CREATE TABLE `t_user_role` (
  `RELATION_ID` bigint(20) NOT NULL auto_increment,
  `USER_ID` decimal(10,0) DEFAULT NULL COMMENT '用户ID',
  `ROLE_ID` decimal(10,0) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`RELATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-角色关联表';

#
# Table structure for table t_user_type
#

CREATE TABLE `t_user_type` (
  `USER_TYPE_CODE` varchar(2) NOT NULL,
  `USER_TYPE_VALUE` varchar(20) NOT NULL,
  PRIMARY KEY (`USER_TYPE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Table structure for table t_video_meta
#

CREATE TABLE `t_video_meta` (
  `ID` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(255) DEFAULT NULL COMMENT '名称  /文件名 ？',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '描述',
  `RUNTIME` varchar(50) DEFAULT NULL COMMENT '时长',
  `FORMAL_FILE_PATH` varchar(255) DEFAULT NULL COMMENT '存储路径',
  `FILE_SIZE` varchar(30) DEFAULT NULL COMMENT '文件大小',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运行期资产子表-视频';

#
# Table structure for table t_video_meta_backup
#

CREATE TABLE `t_video_meta_backup` (
  `ID` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(255) DEFAULT NULL COMMENT '名称/文件名',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '描述',
  `RUNTIME` varchar(50) DEFAULT NULL COMMENT '时长',
  `TEMPORARY_FILE_PATH` varchar(255) DEFAULT NULL COMMENT '存储路径 ',
  `FILE_SIZE` varchar(30) DEFAULT NULL COMMENT '文件大小',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='维护期资产子表-视频';

#
# Table structure for table t_video_specification
#

CREATE TABLE `t_video_specification` (
  `ID` bigint(20) NOT NULL auto_increment,
  `MOVIE_DESC` varchar(255) DEFAULT NULL COMMENT '视频规格描述',
  `RESOLUTION` varchar(255) DEFAULT NULL,
  `DURATION` decimal(10,0) DEFAULT NULL COMMENT '时长  单位秒',
  `TYPE` varchar(255) DEFAULT NULL COMMENT '视频类型  .mp4',
  `FILE_SIZE` varchar(255) DEFAULT NULL COMMENT '文件最大尺寸',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='视频规格信息表';

#
# Table structure for table tmpss
#

CREATE TABLE `tmpss` (
  `A` decimal(20,0) DEFAULT NULL,
  `B` decimal(20,0) DEFAULT NULL,
  `C` decimal(20,0) DEFAULT NULL,
  `D` decimal(20,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
