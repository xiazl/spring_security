/*
 Navicat Premium Data Transfer

 Source Server         : test_master
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost
 Source Database       : cims

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : utf-8

 Date: 04/26/2019 19:23:52 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `bank`
-- ----------------------------
DROP TABLE IF EXISTS `bank`;
CREATE TABLE `bank` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `bank_name` varchar(8) NOT NULL COMMENT '银行名称',
  `is_delete` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '0未删除，1已删除',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建者用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_bank_name` (`bank_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='银行名称';

-- ----------------------------
--  Records of `bank`
-- ----------------------------
BEGIN;
INSERT INTO `bank` VALUES ('1', '建设', '0', '-1', '2019-04-15 17:13:39'), ('2', '工商U盾', '0', '-1', '2019-04-15 17:13:39'), ('3', '上海', '0', '-1', '2019-04-15 17:13:39'), ('4', '兴业', '0', '-1', '2019-04-15 17:13:39'), ('5', '浦发', '0', '-1', '2019-04-15 17:13:39'), ('6', '中信', '0', '-1', '2019-04-15 17:13:39'), ('7', '农业', '0', '-1', '2019-04-15 17:13:39'), ('8', '光大', '0', '-1', '2019-04-15 17:13:39'), ('9', '广发U盾', '0', '-1', '2019-04-15 17:13:39'), ('10', '交通', '0', '-1', '2019-04-15 17:13:39'), ('11', '北京', '0', '-1', '2019-04-15 17:13:39'), ('12', '温州', '0', '-1', '2019-04-15 17:13:39'), ('13', '成都', '0', '-1', '2019-04-15 17:13:39'), ('14', '招商', '0', '-1', '2019-04-15 17:13:39'), ('15', '中国K令', '0', '-1', '2019-04-15 17:13:39'), ('16', '包商', '0', '-1', '2019-04-15 17:13:39'), ('17', '唐山', '0', '-1', '2019-04-15 17:13:39'), ('18', '天津', '0', '-1', '2019-04-15 17:13:39'), ('19', '营口', '0', '-1', '2019-04-15 17:13:39'), ('20', '泰隆', '0', '-1', '2019-04-15 17:13:39'), ('21', '渤海', '0', '-1', '2019-04-15 17:13:39'), ('22', '深圳', '0', '-1', '2019-04-15 17:13:39'), ('23', '顺德农商', '0', '-1', '2019-04-15 17:13:39'), ('24', '安徽农商', '0', '-1', '2019-04-15 17:13:39'), ('25', '齐鲁', '0', '-1', '2019-04-15 17:13:39'), ('26', '青岛', '0', '-1', '2019-04-15 17:13:39'), ('27', '湖南农信', '0', '-1', '2019-04-15 17:13:39'), ('28', '长沙', '0', '-1', '2019-04-15 17:13:39'), ('29', '福建农信', '0', '-1', '2019-04-15 17:13:39'), ('30', '长安 ', '0', '-1', '2019-04-15 17:13:39'), ('31', '深圳农商', '0', '-1', '2019-04-15 17:13:39'), ('33', '哈尔滨', '0', '-1', '2019-04-15 17:13:39'), ('34', '济宁', '0', '-1', '2019-04-15 17:13:39'), ('35', '嘉兴', '0', '-1', '2019-04-15 17:13:39'), ('36', '江西', '0', '-1', '2019-04-15 17:13:39'), ('37', '南京', '0', '-1', '2019-04-15 17:13:39'), ('38', '大连', '0', '-1', '2019-04-15 17:13:39'), ('39', '海峡', '0', '-1', '2019-04-15 17:13:39'), ('40', '安徽农信', '0', '-1', '2019-04-15 17:13:39'), ('41', '鄞州', '0', '-1', '2019-04-15 17:13:39'), ('42', '甘肃', '0', '-1', '2019-04-15 17:13:39'), ('43', '桂林', '0', '-1', '2019-04-15 17:13:39'), ('44', '武汉', '0', '-1', '2019-04-15 17:13:39'), ('45', '东营', '0', '-1', '2019-04-15 17:13:39'), ('46', '盛京', '0', '-1', '2019-04-15 17:13:39'), ('47', '柳州 ', '0', '-1', '2019-04-15 17:13:39'), ('49', '江苏', '0', '-1', '2019-04-15 17:13:39'), ('50', '华兴', '0', '-1', '2019-04-15 17:13:39'), ('51', '西安', '0', '-1', '2019-04-15 17:13:39'), ('52', '烟台', '0', '-1', '2019-04-15 17:13:39'), ('53', '张家口', '0', '-1', '2019-04-15 17:13:39'), ('54', '丹东', '0', '-1', '2019-04-15 17:13:39'), ('55', '兰州', '0', '-1', '2019-04-15 17:13:39'), ('56', '南粤', '0', '-1', '2019-04-15 17:13:39'), ('57', '中旅', '0', '-1', '2019-04-15 17:13:39'), ('58', '广东农信', '0', '-1', '2019-04-15 17:13:39'), ('59', '广州农信', '0', '-1', '2019-04-15 17:13:39'), ('60', '贵州', '0', '-1', '2019-04-15 17:13:39'), ('61', '东莞', '0', '-1', '2019-04-15 17:13:39'), ('62', '山东', '0', '-1', '2019-04-15 17:13:39'), ('63', '邢台 ', '0', '-1', '2019-04-15 17:13:39'), ('64', '北部湾', '0', '-1', '2019-04-15 17:13:39'), ('65', '江苏农信', '0', '-1', '2019-04-15 17:13:39'), ('66', '河北', '0', '-1', '2019-04-15 17:13:39'), ('67', '威海', '0', '-1', '2019-04-15 17:13:39'), ('68', '广发K令', '0', '-1', '2019-04-15 17:13:39'), ('69', '浙商', '0', '-1', '2019-04-15 17:13:39'), ('70', '民泰', '0', '-1', '2019-04-15 17:13:39'), ('71', '九江', '0', '-1', '2019-04-15 17:13:40'), ('72', '晋商', '0', '-1', '2019-04-15 17:13:40'), ('73', '徽商', '0', '-1', '2019-04-15 17:13:40'), ('74', '广州', '0', '-1', '2019-04-15 17:13:40'), ('75', '四川', '0', '-1', '2019-04-15 17:13:40'), ('76', '北京农商', '0', '-1', '2019-04-15 17:13:40'), ('77', '承德', '0', '-1', '2019-04-15 17:13:40'), ('78', '厦门', '0', '-1', '2019-04-15 17:13:40'), ('79', '福建农商', '0', '-1', '2019-04-15 17:13:40'), ('80', '陕西信合 ', '0', '-1', '2019-04-15 17:13:40'), ('82', '宁波', '0', '-1', '2019-04-15 17:13:40'), ('83', '华夏', '0', '-1', '2019-04-15 17:13:40'), ('84', '民生', '0', '-1', '2019-04-15 17:13:40'), ('85', '锦州', '0', '-1', '2019-04-15 17:13:40'), ('86', '昆山', '0', '-1', '2019-04-15 17:13:40'), ('87', '郑州', '0', '-1', '2019-04-15 17:13:40'), ('89', '沧州', '0', '-1', '2019-04-15 17:13:40'), ('90', '廊坊', '0', '-1', '2019-04-15 17:13:40'), ('91', '台州', '0', '-1', '2019-04-15 17:13:40'), ('92', '鞍山', '0', '-1', '2019-04-15 17:13:40'), ('93', '北京银座', '0', '-1', '2019-04-15 17:13:40'), ('94', '日照', '0', '-1', '2019-04-15 17:13:40'), ('96', '浙江农信', '0', '-1', '2019-04-15 17:13:40'), ('97', '重庆', '0', '-1', '2019-04-15 17:13:40'), ('98', '赣州', '0', '-1', '2019-04-15 17:13:40'), ('99', '平安K令', '0', '-1', '2019-04-15 17:13:40');
COMMIT;

-- ----------------------------
--  Table structure for `card`
-- ----------------------------
DROP TABLE IF EXISTS `card`;
CREATE TABLE `card` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `main_type` tinyint(1) NOT NULL COMMENT '类型 1 - 个人银行，2 - 手机银行， 3 - 支付宝',
  `bank_name` varchar(8) NOT NULL COMMENT '银行名称',
  `owner` varchar(24) NOT NULL COMMENT '户名',
  `card_no` varchar(20) NOT NULL COMMENT '卡号',
  `province` varchar(10) DEFAULT NULL COMMENT '开户省',
  `city` varchar(15) DEFAULT NULL COMMENT '开户市',
  `branch_name` varchar(30) DEFAULT NULL COMMENT '开户行',
  `id_no` varchar(18) NOT NULL COMMENT '身份证号',
  `login_password` varchar(20) DEFAULT NULL COMMENT '登陆密码',
  `pay_password` varchar(20) DEFAULT NULL COMMENT '支付密码',
  `ukey_password` varchar(20) DEFAULT NULL COMMENT 'U盾密码',
  `type_user_name` varchar(20) DEFAULT NULL COMMENT '类型用户名',
  `type_login_password` varchar(20) DEFAULT NULL COMMENT '类型登录密码',
  `type_pay_password` varchar(20) DEFAULT NULL COMMENT '类型支付密码',
  `secondary_type` tinyint(1) NOT NULL COMMENT '二级分类: 个人银行分类下包含 [1 - A, 2 - B, 3 - C], 手机银行分类下包含[4-手机银行], 支付宝分类下包含 [5-支付宝主卡, 6-支付宝下挂卡]',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态0未用,1在用,2停用,3冻结,4问题',
  `phone_no` varchar(16) DEFAULT NULL COMMENT '手机号',
  `phone_password` varchar(20) DEFAULT NULL COMMENT '手机密码',
  `roaming_end_date` date DEFAULT NULL COMMENT '国际漫游到期时间',
  `comment` varchar(200) DEFAULT NULL COMMENT '备注、支付宝密保问题及答案',
  `warehouse_id` int(4) NOT NULL COMMENT '仓库id',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态 1 删除',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `archive` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否是归档卡 1 是',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_card_no` (`card_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='银行卡信息表';

-- ----------------------------
--  Table structure for `card_channel`
-- ----------------------------
DROP TABLE IF EXISTS `card_channel`;
CREATE TABLE `card_channel` (
  `id` int(4) unsigned NOT NULL AUTO_INCREMENT,
  `channel_name` varchar(30) NOT NULL COMMENT '渠道名',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态',
  `create_user_id` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='入库渠道';

-- ----------------------------
--  Table structure for `card_maintain`
-- ----------------------------
DROP TABLE IF EXISTS `card_maintain`;
CREATE TABLE `card_maintain` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `card_no` varchar(20) NOT NULL COMMENT '卡号',
  `date` date DEFAULT NOT NULL COMMENT '发生日期',
  `status` tinyint(1) NOT NULL COMMENT '状态 2停用,3冻结,4问题',
  `disable_type` tinyint(1) DEFAULT NULL COMMENT '停用原因:1主动拉停，2渠道拉停',
  `freeze_type` tinyint(1) DEFAULT NULL COMMENT '冻结类型 1 风控 2 故障 3人为 4司法 5 疑似司法',
  `reason` varchar(80) DEFAULT NULL COMMENT '冻结显示原因',
  `freeze_amount` decimal(12,2) DEFAULT NULL COMMENT '冻结金额',
  `process_status` tinyint(1) DEFAULT NULL COMMENT '处理状态 1 处理中 2 未完成 3 按时完成 4 逾期完成 5 被代扣 6 不需处理',
  `over_head` decimal(12,2) DEFAULT NULL COMMENT '处理开销',
  `payback_amount` decimal(12,2) DEFAULT NULL COMMENT '回款金额',
  `department` varchar(20) DEFAULT NULL COMMENT '回款部门',
  `account` varchar(20) DEFAULT NULL COMMENT '回款账号',
  `finish_date` datetime DEFAULT NULL COMMENT '完成日期',
  `process_method` tinyint(1) DEFAULT NULL COMMENT '处理方式 1 自主代扣 2 渠道处理 3 自行解冻 4 卡费扣除 ',
  `problem_desc` varchar(80) DEFAULT NULL COMMENT '问题卡的描述信息',
  `comment` varchar(80) DEFAULT NULL COMMENT '备注',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态 1 删除',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_card_no` (`card_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='卡片维护';

-- ----------------------------
--  Table structure for `card_project`
-- ----------------------------
DROP TABLE IF EXISTS `card_project`;
CREATE TABLE `card_project` (
  `id` int(4) unsigned NOT NULL AUTO_INCREMENT,
  `project_name` varchar(30) NOT NULL COMMENT '项目名',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态',
  `create_user_id` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出库项目';

-- ----------------------------
--  Table structure for `card_purchase`
-- ----------------------------
DROP TABLE IF EXISTS `card_purchase`;
CREATE TABLE `card_purchase` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `card_no` varchar(20) NOT NULL COMMENT '卡号',
  `channel` int(4) NOT NULL COMMENT '渠道id 对应channel表',
  `balance` decimal(12,2) DEFAULT NULL COMMENT '卡片余额',
  `ckr` varchar(20) NOT NULL COMMENT 'ckr信息',
  `price` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '每日成本价格',
  `purchase_date` date NOT NULL COMMENT '采购日期',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态 1 删除',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_card_no` (`card_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='卡片入库信息';

-- ----------------------------
--  Table structure for `card_return`
-- ----------------------------
DROP TABLE IF EXISTS `card_return`;
CREATE TABLE `card_return` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `card_no` varchar(20) NOT NULL COMMENT '卡号',
  `verify_amount` decimal(12,2) NOT NULL COMMENT '核实金额',
  `diff_amount` decimal(12,2) NOT NULL COMMENT '差异金额',
  `return_date` date NOT NULL COMMENT '退库时间',
  `verify_user` varchar(20) NOT NULL COMMENT '退库人',
  `comment` varchar(100) DEFAULT NULL COMMENT '备注',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态 1 删除',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='卡片退库信息';

-- ----------------------------
--  Table structure for `card_sale`
-- ----------------------------
DROP TABLE IF EXISTS `card_sale`;
CREATE TABLE `card_sale` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `card_no` varchar(20) NOT NULL COMMENT '卡号',
  `project_id` int(4) NOT NULL COMMENT '出卡项目',
  `price` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '出卡价格/日',
  `sale_date` date NOT NULL COMMENT '出库日期',
  `receiver` varchar(10) NOT NULL COMMENT '接收人',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态 1 删除',
  `create_user_id` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_card_no` (`card_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='卡片出库信息';

-- ----------------------------
--  Table structure for `card_settlement`
-- ----------------------------
DROP TABLE IF EXISTS `card_settlement`;
CREATE TABLE `card_settlement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `card_no` varchar(20) NOT NULL COMMENT '卡号',
  `status` tinyint(1) DEFAULT NULL COMMENT '结算时卡状态 ',
  `start_date` date NOT NULL COMMENT '结算开始时间',
  `stop_date` date NOT NULL COMMENT '结算终止时间',
  `days` int(11) NOT NULL COMMENT '结算天数',
  `type` tinyint(1) NOT NULL COMMENT '类型 1 入库结算 2 出库结算',
  `price` decimal(12,2) NOT NULL COMMENT '结算日价',
  `amount` decimal(12,2) NOT NULL COMMENT '结算金额',
  `deduct_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '扣除金额',
  `real_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '实际结算金额',
  `payment_date` date DEFAULT NULL COMMENT '付款日期',
  `is_last` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否是最新的结算记录 1 是',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态 1 删除',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_card_no` (`card_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='卡片结算信息';

-- ----------------------------
--  Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_code` varchar(20) DEFAULT NULL COMMENT '角色编码',
  `role_name` varchar(12) NOT NULL COMMENT '角色名称',
  `role_des` varchar(100) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户角色';

-- ----------------------------
--  Records of `role`
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES ('1', 'ROLE_ADMIN', '管理员', '管理员'), ('2', 'ROLE_IN_CARD', '入库员', '入库员'), ('3', 'ROLE_OUT_CARD', '出库员', '出库员'), ('4', 'ROLE_STOCK', '库存管理员', '库存管理员'), ('5', 'ROLE_SETTLER', '结算员', '结算员'), ('6', 'ROLE_RETURN', '退库员', '退库员'), ('7', 'ROLE_OP', '操作员', '操作员');
COMMIT;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `nickname` varchar(20) NOT NULL COMMENT '密码',
  `password` varchar(60) NOT NULL COMMENT '密码',
  `is_disable` tinyint(1) DEFAULT NULL COMMENT '是否被停用 0 未停用， 1已停用',
  `is_delete` tinyint(4) DEFAULT '0' COMMENT '删除状态 0 未删除 1 已删除',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'admin', 'admin', '$2a$10$ouklJVGKDtXpaqJ4R.R0Je4gQr/rnWJx7zS7F9bzxhuepHLnEVrqu', '0', '0', '2019-04-14 18:13:33', '0', '2019-04-17 19:34:40', '1');
COMMIT;

-- ----------------------------
--  Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态 1 删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id_role_id` (`user_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户角色关系';

-- ----------------------------
--  Records of `user_role`
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES ('1', '1', '1', '0');
COMMIT;

-- ----------------------------
--  Table structure for `user_warehouse`
-- ----------------------------
DROP TABLE IF EXISTS `user_warehouse`;
CREATE TABLE `user_warehouse` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `warehouse_id` tinyint(1) NOT NULL COMMENT '仓库id',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0未删除，1已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`,`warehouse_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-仓库对应关系表';

-- ----------------------------
--  Table structure for `warehouse`
-- ----------------------------
DROP TABLE IF EXISTS `warehouse`;
CREATE TABLE `warehouse` (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 未删除 1 已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='仓库';

-- ----------------------------
--  Records of `warehouse`
-- ----------------------------
BEGIN;
INSERT INTO `warehouse` VALUES ('1', '金边', '0'), ('2', '金木棉', '0'), ('3', '菲律宾', '0');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
