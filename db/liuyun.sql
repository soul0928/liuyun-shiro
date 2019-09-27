/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : liuyun-shiro

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 27/09/2019 12:04:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for liuyun_permission
-- ----------------------------
DROP TABLE IF EXISTS `liuyun_permission`;
CREATE TABLE `liuyun_permission`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源名称',
  `per_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限代码字符串',
  `create_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `version` int(11) NULL DEFAULT NULL COMMENT '版本',
  `del_flag` int(1) NULL DEFAULT NULL COMMENT '删除标志:（0：正常；1：删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `per_code`(`per_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 97396929444446209 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of liuyun_permission
-- ----------------------------
INSERT INTO `liuyun_permission` VALUES (97396492859342848, '查询', 'test.delete', '1', '2019-09-26 18:18:53', NULL, NULL, NULL, 0, 0);
INSERT INTO `liuyun_permission` VALUES (97396527013560320, '查询', 'test.query', '1', '2019-09-26 18:19:02', NULL, NULL, NULL, 0, 0);
INSERT INTO `liuyun_permission` VALUES (97396561432018944, '查询', 'test.put', '1', '2019-09-26 18:19:10', NULL, NULL, NULL, 0, 0);
INSERT INTO `liuyun_permission` VALUES (97396929444446208, '查询', 'test.add', '1', '2019-09-26 18:20:38', NULL, NULL, NULL, 0, 0);

-- ----------------------------
-- Table structure for liuyun_role
-- ----------------------------
DROP TABLE IF EXISTS `liuyun_role`;
CREATE TABLE `liuyun_role`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `per_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限代码字符串',
  `create_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `version` int(11) NULL DEFAULT NULL COMMENT '版本',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '删除标志:（0：正常；1：删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 97396387611672577 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of liuyun_role
-- ----------------------------
INSERT INTO `liuyun_role` VALUES (97395806268555264, '超级管理员', 'role.superadmin', '1', '2019-09-26 18:16:10', NULL, NULL, NULL, 0, '0');
INSERT INTO `liuyun_role` VALUES (97396387611672576, '管理员', 'role.admin', '1', '2019-09-26 18:18:28', NULL, NULL, NULL, 0, '0');

-- ----------------------------
-- Table structure for liuyun_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `liuyun_role_permission`;
CREATE TABLE `liuyun_role_permission`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint(64) NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` bigint(64) NULL DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 97396387611672582 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of liuyun_role_permission
-- ----------------------------
INSERT INTO `liuyun_role_permission` VALUES (97396387611672576, 97395806268555264, 97396492859342848);
INSERT INTO `liuyun_role_permission` VALUES (97396387611672577, 97395806268555264, 97396527013560320);
INSERT INTO `liuyun_role_permission` VALUES (97396387611672578, 97395806268555264, 97396561432018944);
INSERT INTO `liuyun_role_permission` VALUES (97396387611672579, 97395806268555264, 97396929444446208);
INSERT INTO `liuyun_role_permission` VALUES (97396387611672580, 97396387611672576, 97396929444446208);

-- ----------------------------
-- Table structure for liuyun_user
-- ----------------------------
DROP TABLE IF EXISTS `liuyun_user`;
CREATE TABLE `liuyun_user`  (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '盐',
  `create_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `version` int(11) NULL DEFAULT NULL COMMENT '版本',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '删除标志:（0：正常；1：删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of liuyun_user
-- ----------------------------
INSERT INTO `liuyun_user` VALUES (96676664074829823, '小明', '18811112222', 'admin', 'NUFGMzUzMkJCRDYwMEU3OEQ3MEVGRkZERTNDN0REQTM=', 'admin', '1', '2019-09-23 11:15:42', NULL, NULL, NULL, 0, '0');
INSERT INTO `liuyun_user` VALUES (96676664074829824, '小黑', 'demoData', 'demoData', 'REYxMTMwMEFDNTQzQzFDNzU5NTVENEU5QTQ5NURCNzAxQ0Y3MEMwNkM3QUY3RTVCRDQwOUNFQTNCQzZCQzlCRA==', 'demoData', '1', '2019-09-24 18:38:33', NULL, NULL, NULL, 0, '0');

-- ----------------------------
-- Table structure for liuyun_user_role
-- ----------------------------
DROP TABLE IF EXISTS `liuyun_user_role`;
CREATE TABLE `liuyun_user_role`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(64) NULL DEFAULT NULL COMMENT '用户id',
  `role_id` bigint(64) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 96676664074829825 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of liuyun_user_role
-- ----------------------------
INSERT INTO `liuyun_user_role` VALUES (96676664074829823, 96676664074829823, 97395806268555264);
INSERT INTO `liuyun_user_role` VALUES (96676664074829824, 96676664074829824, 97396387611672576);

-- ----------------------------
-- Table structure for t_test
-- ----------------------------
DROP TABLE IF EXISTS `t_test`;
CREATE TABLE `t_test`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `birthday` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_test
-- ----------------------------
INSERT INTO `t_test` VALUES (1, '小明', '2019-09-23 10:10:39');
INSERT INTO `t_test` VALUES (2, '小白', '2019-09-23 10:10:41');
INSERT INTO `t_test` VALUES (3, '小黑', '2019-09-23 10:10:45');

SET FOREIGN_KEY_CHECKS = 1;
