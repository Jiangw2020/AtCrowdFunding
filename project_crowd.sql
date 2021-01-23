/*
 Navicat Premium Data Transfer

 Source Server         : jw2021
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : localhost:3306
 Source Schema         : project_crowd

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 23/01/2021 23:19:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for inner_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `inner_admin_role`;
CREATE TABLE `inner_admin_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) NULL DEFAULT NULL,
  `role_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inner_admin_role
-- ----------------------------
INSERT INTO `inner_admin_role` VALUES (14, 729, 90);
INSERT INTO `inner_admin_role` VALUES (15, 729, 91);
INSERT INTO `inner_admin_role` VALUES (16, 730, 92);
INSERT INTO `inner_admin_role` VALUES (17, 730, 93);

-- ----------------------------
-- Table structure for inner_role_auth
-- ----------------------------
DROP TABLE IF EXISTS `inner_role_auth`;
CREATE TABLE `inner_role_auth`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NULL DEFAULT NULL,
  `auth_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inner_role_auth
-- ----------------------------
INSERT INTO `inner_role_auth` VALUES (27, 91, 1);
INSERT INTO `inner_role_auth` VALUES (28, 91, 8);
INSERT INTO `inner_role_auth` VALUES (29, 93, 4);
INSERT INTO `inner_role_auth` VALUES (30, 93, 5);
INSERT INTO `inner_role_auth` VALUES (31, 93, 3);

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_acct` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_pswd` char(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `login_acct`(`login_acct`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 733 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES (1, 'admin', '$2a$10$HGr9lketkb32fBu9uUtK2eXTWwsTpps0aR6b9e9LSfsi.bdlOFRwG', '姜', '123456@qq.com', '2021-1-12');
INSERT INTO `t_admin` VALUES (728, 'jiang', '$2a$10$LTnf221I/STcUryHHumAI.6YfI5dOUlL43eZjX1XcyASJXZ8fCaGq', 'Jiang', '123@qq.com', '2021-01-18 21:20:02');
INSERT INTO `t_admin` VALUES (729, 'adminOperator', '$2a$10$HGr9lketkb32fBu9uUtK2eXTWwsTpps0aR6b9e9LSfsi.bdlOFRwG', '管理员', '123@qq.com', NULL);
INSERT INTO `t_admin` VALUES (730, 'roleOperator', '$2a$10$HGr9lketkb32fBu9uUtK2eXTWwsTpps0aR6b9e9LSfsi.bdlOFRwG', '角色', '123@qq.com', NULL);
INSERT INTO `t_admin` VALUES (731, '123', '$2a$10$HGr9lketkb32fBu9uUtK2eXTWwsTpps0aR6b9e9LSfsi.bdlOFRwG', '11', '1123@qq.com', NULL);
INSERT INTO `t_admin` VALUES (732, '321', '$2a$10$HGr9lketkb32fBu9uUtK2eXTWwsTpps0aR6b9e9LSfsi.bdlOFRwG', '22', '123@qq.com', NULL);

-- ----------------------------
-- Table structure for t_auth
-- ----------------------------
DROP TABLE IF EXISTS `t_auth`;
CREATE TABLE `t_auth`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `category_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_auth
-- ----------------------------
INSERT INTO `t_auth` VALUES (1, '', '用户模块', NULL);
INSERT INTO `t_auth` VALUES (2, 'user:delete', '删除', 1);
INSERT INTO `t_auth` VALUES (3, 'user:get', '查询', 1);
INSERT INTO `t_auth` VALUES (4, '', '角色模块', NULL);
INSERT INTO `t_auth` VALUES (5, 'role:delete', '删除', 4);
INSERT INTO `t_auth` VALUES (6, 'role:get', '查询', 4);
INSERT INTO `t_auth` VALUES (7, 'role:add', '新增', 4);
INSERT INTO `t_auth` VALUES (8, 'user:save', '保存', 1);

-- ----------------------------
-- Table structure for t_member
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginacct` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `userpswd` char(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authstatus` int(4) NULL DEFAULT NULL COMMENT '实名认证状态 0 - 未实名认证， 1 - 实名认证申 请中， 2 - 已实名认证',
  `usertype` int(4) NULL DEFAULT NULL COMMENT ' 0 - 个人， 1 - 企业',
  `realname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cardnum` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `accttype` int(4) NULL DEFAULT NULL COMMENT '0 - 企业， 1 - 个体， 2 - 个人， 3 - 政府',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `loginacct`(`loginacct`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_member
-- ----------------------------
INSERT INTO `t_member` VALUES (2, 'jack', '$2a$10$9vpV1QX122D2rATs43Mz0.uWJBhHaJJB8eI6IzP6o2cCB.lXpeFHq', ' 杰 克 ', 'jack@qq.com', 1, 1, '杰克', '123123', 2);
INSERT INTO `t_member` VALUES (6, '1234', '$2a$10$avbZAhHzcSiSONgHsrSOM.3DjTTqu.b3N8pc7XdBCkv/9SCD3D.BO', 'admin', '123@qq.com', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for t_member_confirm_info
-- ----------------------------
DROP TABLE IF EXISTS `t_member_confirm_info`;
CREATE TABLE `t_member_confirm_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberid` int(11) NULL DEFAULT NULL COMMENT '会员 id',
  `paynum` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '易付宝企业账号',
  `cardnum` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '法人身份证号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_member_confirm_info
-- ----------------------------
INSERT INTO `t_member_confirm_info` VALUES (1, 6, '111111', '2222222222222222');
INSERT INTO `t_member_confirm_info` VALUES (2, 6, '111111', '11111111111');

-- ----------------------------
-- Table structure for t_member_launch_info
-- ----------------------------
DROP TABLE IF EXISTS `t_member_launch_info`;
CREATE TABLE `t_member_launch_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberid` int(11) NULL DEFAULT NULL COMMENT '会员 id',
  `description_simple` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简单介绍',
  `description_detail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细介绍',
  `phone_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `service_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客服电话',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_member_launch_info
-- ----------------------------
INSERT INTO `t_member_launch_info` VALUES (1, 6, 'i am mao', '我是猫哥', '123456', '654321');
INSERT INTO `t_member_launch_info` VALUES (2, 6, 'i am mao', '我是猫哥', '123456', '654321');

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NULL DEFAULT NULL,
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `icon` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES (1, NULL, '系统权限菜单', NULL, 'glyphicon glyphicon-th-list');
INSERT INTO `t_menu` VALUES (2, 1, ' 控 制 面 板 ', 'main.htm', 'glyphicon glyphicon-dashboard');
INSERT INTO `t_menu` VALUES (3, 1, '权限管理', NULL, 'glyphicon glyphicon glyphicon-tasks');
INSERT INTO `t_menu` VALUES (4, 3, ' 用 户 维 护 ', 'user/index.htm', 'glyphicon glyphicon-user');
INSERT INTO `t_menu` VALUES (5, 3, ' 角 色 维 护 ', 'role/index.htm', 'glyphicon glyphicon-king');
INSERT INTO `t_menu` VALUES (6, 3, ' 菜 单 维 护 ', 'permission/index.htm', 'glyphicon glyphicon-lock');
INSERT INTO `t_menu` VALUES (7, 1, ' 业 务 审 核 ', NULL, 'glyphicon glyphicon-ok');
INSERT INTO `t_menu` VALUES (8, 7, ' 实 名 认 证 审 核 ', 'auth_cert/index.htm', 'glyphicon glyphicon-check');
INSERT INTO `t_menu` VALUES (9, 7, ' 广 告 审 核 ', 'auth_adv/index.htm', 'glyphicon glyphicon-check');
INSERT INTO `t_menu` VALUES (10, 7, ' 项 目 审 核 ', 'auth_project/index.htm', 'glyphicon glyphicon-check');
INSERT INTO `t_menu` VALUES (11, 1, ' 业 务 管 理 ', NULL, 'glyphicon glyphicon-th-large');
INSERT INTO `t_menu` VALUES (12, 11, ' 资 质 维 护 ', 'cert/index.htm', 'glyphicon glyphicon-picture');
INSERT INTO `t_menu` VALUES (13, 11, ' 分 类 管 理 ', 'certtype/index.htm', 'glyphicon glyphicon-equalizer');
INSERT INTO `t_menu` VALUES (14, 11, ' 流 程 管 理 ', 'process/index.htm', 'glyphicon glyphicon-random');
INSERT INTO `t_menu` VALUES (15, 11, ' 广 告 管 理 ', 'advert/index.htm', 'glyphicon glyphicon-hdd');
INSERT INTO `t_menu` VALUES (16, 11, ' 消 息 模 板 ', 'message/index.htm', 'glyphicon glyphicon-comment');
INSERT INTO `t_menu` VALUES (17, 11, ' 项 目 分 类 ', 'projectType/index.htm', 'glyphicon glyphicon-list');
INSERT INTO `t_menu` VALUES (18, 11, ' 项 目 标 签 ', 'tag/index.htm', 'glyphicon glyphicon-tags');
INSERT INTO `t_menu` VALUES (19, 1, ' 参 数 管 理 ', 'param/index.htm', 'glyphicon glyphicon-list-alt');

-- ----------------------------
-- Table structure for t_project
-- ----------------------------
DROP TABLE IF EXISTS `t_project`;
CREATE TABLE `t_project`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `project_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目描述',
  `money` bigint(11) NULL DEFAULT NULL COMMENT '筹集金额',
  `day` int(11) NULL DEFAULT NULL COMMENT '筹集天数',
  `status` int(4) NULL DEFAULT NULL COMMENT '0-即将开始，1-众筹中，2-众筹成功，3-众筹失败 ',
  `deploydate` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目发起时间',
  `supportmoney` bigint(11) NULL DEFAULT NULL COMMENT '已筹集到的金额',
  `supporter` int(11) NULL DEFAULT NULL COMMENT '支持人数',
  `completion` int(3) NULL DEFAULT NULL COMMENT '百分比完成度',
  `memberid` int(11) NULL DEFAULT NULL COMMENT '发起人的会员 id',
  `createdate` varchar(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目创建时间',
  `follower` int(11) NULL DEFAULT NULL COMMENT '关注人数',
  `header_picture_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头图路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_project
-- ----------------------------
INSERT INTO `t_project` VALUES (3, 'brotherMao', '就是帅！冲啊', 40000, 54, 0, '2021-01-20', 6578, 22, 56, 6, '2021-01-23', 999, 'http://jw2021.oss-cn-hangzhou.aliyuncs.com/20210123/9f08d8c6c6f145209d108c8fc66df3a5.jpg');
INSERT INTO `t_project` VALUES (4, 'brotherMao', '就是帅！抽签', 77777, 31, 1, '2021-01-18', 658980, 34, 33, 6, '2021-01-23', 345, 'http://jw2021.oss-cn-hangzhou.aliyuncs.com/20210123/f39a5d10a1c441d085380965e72f6919.jpg');

-- ----------------------------
-- Table structure for t_project_item_pic
-- ----------------------------
DROP TABLE IF EXISTS `t_project_item_pic`;
CREATE TABLE `t_project_item_pic`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectid` int(11) NULL DEFAULT NULL,
  `item_pic_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_project_item_pic
-- ----------------------------
INSERT INTO `t_project_item_pic` VALUES (1, 3, 'http://jw2021.oss-cn-hangzhou.aliyuncs.com/20210123/33ca156685c54989b433ff1821a8d22d.jpg');
INSERT INTO `t_project_item_pic` VALUES (2, 4, 'http://jw2021.oss-cn-hangzhou.aliyuncs.com/20210123/1a77aab6ca0948b0aab693a58af91ac9.jpg');

-- ----------------------------
-- Table structure for t_project_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_project_tag`;
CREATE TABLE `t_project_tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectid` int(11) NULL DEFAULT NULL,
  `tagid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_project_tag
-- ----------------------------
INSERT INTO `t_project_tag` VALUES (1, 3, 4);
INSERT INTO `t_project_tag` VALUES (2, 3, 7);
INSERT INTO `t_project_tag` VALUES (3, 3, 9);
INSERT INTO `t_project_tag` VALUES (4, 4, 4);
INSERT INTO `t_project_tag` VALUES (5, 4, 7);
INSERT INTO `t_project_tag` VALUES (6, 4, 9);

-- ----------------------------
-- Table structure for t_project_type
-- ----------------------------
DROP TABLE IF EXISTS `t_project_type`;
CREATE TABLE `t_project_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectid` int(11) NULL DEFAULT NULL,
  `typeid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_project_type
-- ----------------------------
INSERT INTO `t_project_type` VALUES (1, 3, 1);
INSERT INTO `t_project_type` VALUES (2, 3, 2);
INSERT INTO `t_project_type` VALUES (3, 3, 4);
INSERT INTO `t_project_type` VALUES (4, 4, 1);
INSERT INTO `t_project_type` VALUES (5, 4, 2);
INSERT INTO `t_project_type` VALUES (6, 4, 4);

-- ----------------------------
-- Table structure for t_return
-- ----------------------------
DROP TABLE IF EXISTS `t_return`;
CREATE TABLE `t_return`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectid` int(11) NULL DEFAULT NULL,
  `type` int(4) NULL DEFAULT NULL COMMENT '0 - 实物回报， 1 虚拟物品回报',
  `supportmoney` int(11) NULL DEFAULT NULL COMMENT '支持金额',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回报内容',
  `count` int(11) NULL DEFAULT NULL COMMENT '回报产品限额，“0”为不限回报数量',
  `signalpurchase` int(11) NULL DEFAULT NULL COMMENT '是否设置单笔限购',
  `purchase` int(11) NULL DEFAULT NULL COMMENT '具体限购数量',
  `freight` int(11) NULL DEFAULT NULL COMMENT '运费，“0”为包邮',
  `invoice` int(4) NULL DEFAULT NULL COMMENT '0 - 不开发票， 1 - 开发票',
  `returndate` int(11) NULL DEFAULT NULL COMMENT '项目结束后多少天向支持者发送回报',
  `describ_pic_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '说明图片路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_return
-- ----------------------------
INSERT INTO `t_return` VALUES (1, 3, 0, 10, '以身相许', 5, 1, 8, 0, 1, 15, 'http://jw2021.oss-cn-hangzhou.aliyuncs.com/20210123/78010cd56d3243bdb9db6548395bc480.jpg');
INSERT INTO `t_return` VALUES (2, 4, 0, 10, '以身相许', 5, 1, 8, 0, 0, 15, 'http://jw2021.oss-cn-hangzhou.aliyuncs.com/20210123/2edc75dae35d41519f0a28f7808f83fd.jpg');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 95 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (90, '经理');
INSERT INTO `t_role` VALUES (91, '经理操作者');
INSERT INTO `t_role` VALUES (92, '部长');
INSERT INTO `t_role` VALUES (93, '部长操作者');
INSERT INTO `t_role` VALUES (94, 'role5');

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_tag
-- ----------------------------
INSERT INTO `t_tag` VALUES (1, NULL, '手机');
INSERT INTO `t_tag` VALUES (2, NULL, '数码');
INSERT INTO `t_tag` VALUES (3, NULL, '电脑');
INSERT INTO `t_tag` VALUES (4, 1, '大屏');
INSERT INTO `t_tag` VALUES (5, 1, '美颜');
INSERT INTO `t_tag` VALUES (6, 1, '续航');
INSERT INTO `t_tag` VALUES (7, 2, '高解析度');
INSERT INTO `t_tag` VALUES (8, 2, '高清');
INSERT INTO `t_tag` VALUES (9, 3, '大内存');
INSERT INTO `t_tag` VALUES (10, 3, '固态');

-- ----------------------------
-- Table structure for t_type
-- ----------------------------
DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类介绍',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_type
-- ----------------------------
INSERT INTO `t_type` VALUES (1, '科技', '开启智慧未来');
INSERT INTO `t_type` VALUES (2, '设计', '创意改变生活');
INSERT INTO `t_type` VALUES (3, '公益', '网络天下肥美');
INSERT INTO `t_type` VALUES (4, '农业', '发现更多惊喜');

SET FOREIGN_KEY_CHECKS = 1;
