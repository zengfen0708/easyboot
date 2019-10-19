/*
Navicat MySQL Data Transfer

Source Server         : localhost-simple
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : simple_data

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-10-19 20:08:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) NOT NULL COMMENT '部门名称',
  `parent_id` bigint(20) NOT NULL COMMENT '上级部门',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态，正常-1，停用-0',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '软删除标识(0 有效 1 已删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', 'Simple总部', '0', '1', '0', '2019-10-14 10:47:20', '2019-10-14 10:47:20');
INSERT INTO `sys_dept` VALUES ('2', '华南分部', '1', '1', '0', '2019-10-14 10:48:30', '2019-10-15 14:43:17');
INSERT INTO `sys_dept` VALUES ('3', '西南分部', '1', '1', '0', '2019-10-14 10:48:30', '2019-10-14 10:48:30');
INSERT INTO `sys_dept` VALUES ('4', '研发部', '2', '1', '0', '2019-10-14 10:49:00', '2019-10-14 10:49:00');
INSERT INTO `sys_dept` VALUES ('5', '运维部', '2', '1', '0', '2019-10-14 10:49:00', '2019-10-14 10:49:00');
INSERT INTO `sys_dept` VALUES ('6', '测试部', '2', '1', '0', '2019-10-14 10:49:00', '2019-10-14 10:49:00');
INSERT INTO `sys_dept` VALUES ('7', '研发部', '3', '1', '0', '2019-10-14 10:49:08', '2019-10-14 10:49:08');
INSERT INTO `sys_dept` VALUES ('8', '运维部', '3', '1', '0', '2019-10-14 10:49:08', '2019-10-14 10:49:08');
INSERT INTO `sys_dept` VALUES ('9', '测试部', '3', '1', '0', '2019-10-14 10:49:08', '2019-10-14 10:49:08');
INSERT INTO `sys_dept` VALUES ('10', '产品部', '2', '0', '0', '2019-10-14 19:33:16', '2019-10-14 06:35:29');
INSERT INTO `sys_dept` VALUES ('11', '研发一部', '4', '1', '0', '2019-10-14 20:42:50', '2019-10-14 07:43:12');
INSERT INTO `sys_dept` VALUES ('12', '研发二部', '4', '1', '0', '2019-10-15 14:49:02', '2019-10-15 14:49:01');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) NOT NULL COMMENT '字典名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态，正常-1，停用-0',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '软删除标识(0 有效 1 已删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_dix_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', 'user_status', '用户状态', '1', '0', '2019-10-14 14:49:01', '2019-10-14 14:49:01');
INSERT INTO `sys_dict` VALUES ('2', 'dept_status', '部门状态', '1', '0', '2019-10-14 14:49:01', '2019-10-14 14:49:01');
INSERT INTO `sys_dict` VALUES ('3', 'job_status', '岗位状态', '1', '0', '2019-10-14 14:49:01', '2019-10-14 14:49:01');
INSERT INTO `sys_dict` VALUES ('5', 'dict_status', '字典状态', '1', '0', '2019-10-15 15:35:49', '2019-10-16 15:20:56');

-- ----------------------------
-- Table structure for sys_dict_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_detail`;
CREATE TABLE `sys_dict_detail` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `label` varchar(255) NOT NULL COMMENT '字典标签',
  `value` varchar(255) NOT NULL COMMENT '字典值',
  `sort` bigint(10) DEFAULT NULL COMMENT '排序',
  `dict_id` bigint(20) DEFAULT NULL COMMENT '字典id',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '软删除标识(0 有效 1 已删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_dictid` (`dict_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='字典表详情表';

-- ----------------------------
-- Records of sys_dict_detail
-- ----------------------------
INSERT INTO `sys_dict_detail` VALUES ('1', '正常', '1', '1', '2', '0', '2019-10-14 14:54:19', '2019-10-14 14:54:19');
INSERT INTO `sys_dict_detail` VALUES ('2', '禁用', '0', '2', '2', '0', '2019-10-14 14:54:19', '2019-10-14 14:54:19');
INSERT INTO `sys_dict_detail` VALUES ('3', '正常', '1', '1', '5', '0', '2019-10-15 15:37:22', '2019-10-16 15:20:56');
INSERT INTO `sys_dict_detail` VALUES ('4', '失效', '0', '2', '5', '0', '2019-10-15 15:37:22', '2019-10-16 15:20:56');
INSERT INTO `sys_dict_detail` VALUES ('6', '启用', '1', '1', '1', '0', '2019-10-15 17:39:31', '2019-10-15 17:39:30');
INSERT INTO `sys_dict_detail` VALUES ('7', '禁用', '0', '2', '1', '0', '2019-10-15 17:39:44', '2019-10-15 17:39:43');
INSERT INTO `sys_dict_detail` VALUES ('8', '正常', '1', '1', '3', '0', '2019-10-16 09:59:13', '2019-10-16 09:59:12');
INSERT INTO `sys_dict_detail` VALUES ('9', '禁用', '0', '1', '3', '0', '2019-10-16 09:59:30', '2019-10-16 11:33:35');

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) NOT NULL COMMENT '岗位名称',
  `dept_id` bigint(20) NOT NULL COMMENT '部门id',
  `sort` bigint(10) NOT NULL COMMENT '排序',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态，正常-1，停用-0',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '软删除标识(0 有效 1 已删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='岗位表';

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES ('1', '超级boss', '1', '1', '1', '0', '2019-10-16 11:11:12', '2019-10-16 11:11:12');
INSERT INTO `sys_job` VALUES ('2', '产品经理', '10', '2', '1', '0', '2019-10-16 11:11:12', '2019-10-16 11:11:12');
INSERT INTO `sys_job` VALUES ('3', '后端开发', '11', '3', '1', '0', '2019-10-16 11:11:12', '2019-10-16 11:11:12');
INSERT INTO `sys_job` VALUES ('4', '后端开发', '12', '4', '1', '0', '2019-10-16 11:11:12', '2019-10-16 11:11:12');
INSERT INTO `sys_job` VALUES ('5', '人事专员', '3', '4', '1', '0', '2019-10-16 11:11:12', '2019-10-16 11:11:12');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu_name` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `component` varchar(255) DEFAULT NULL COMMENT '组件目录',
  `path` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `parent_id` bigint(20) NOT NULL COMMENT '上级菜单ID(0 为父组件)',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `sort` bigint(20) NOT NULL COMMENT '排序',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '软删除标识(0 有效 1 已删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='菜单';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '系统管理', '', 'system', '0', 'system', '999', '0', '2019-09-29 16:40:37', '2019-09-29 16:40:37');
INSERT INTO `sys_menu` VALUES ('2', '用户管理', 'system/user/index', 'user', '1', 'user', '1', '0', '2019-09-29 17:00:35', '2019-09-29 17:00:35');
INSERT INTO `sys_menu` VALUES ('3', '角色管理', 'system/role/index', 'role', '1', 'role', '2', '0', '2019-09-29 17:01:52', '2019-09-29 17:01:52');
INSERT INTO `sys_menu` VALUES ('4', '系统监控', '', 'monitor', '0', 'monitor', '3', '0', '2019-09-29 17:02:54', '2019-09-29 17:02:54');
INSERT INTO `sys_menu` VALUES ('7', '菜单管理', 'system/menu/index', 'menu', '1', 'menu', '1', '0', '2019-10-09 15:56:17', '2019-10-09 15:56:17');
INSERT INTO `sys_menu` VALUES ('8', '部门管理', 'system/dept/index', 'dept', '1', 'dept', '8', '0', '2019-10-10 19:06:38', '2019-10-10 06:06:55');
INSERT INTO `sys_menu` VALUES ('9', '多级菜单', '', 'nested', '0', 'menu', '1', '0', '2019-10-10 19:20:39', '2019-10-10 06:20:56');
INSERT INTO `sys_menu` VALUES ('10', '二级菜单', 'nested/menu1/index', 'menu1', '9', 'menu', '0', '0', '2019-10-10 19:22:25', '2019-10-10 06:22:41');
INSERT INTO `sys_menu` VALUES ('11', '二级菜单1', 'nested/menu2/index', 'menu2', '9', 'menu', '0', '0', '2019-10-10 19:22:59', '2019-10-10 06:23:15');
INSERT INTO `sys_menu` VALUES ('12', '三级菜单', 'nested/menu1/menu1-1', 'menu1-1', '10', 'menu', '0', '0', '2019-10-10 19:25:14', '2019-10-10 06:25:30');
INSERT INTO `sys_menu` VALUES ('13', '三级菜单1', 'nested/menu1/menu1-2', 'menu1-2', '10', 'menu', '2', '0', '2019-10-10 20:20:50', '2019-10-10 07:21:06');
INSERT INTO `sys_menu` VALUES ('16', 'SQL监控', 'monitor/sql/index', 'sql', '4', 'sqlMonitor', '0', '0', '2019-10-11 16:55:34', '2019-10-11 03:55:52');
INSERT INTO `sys_menu` VALUES ('17', '权限管理', 'system/permission/index', 'permission', '1', 'permission', '2', '0', '2019-10-12 14:57:24', '2019-10-12 01:57:43');
INSERT INTO `sys_menu` VALUES ('18', '岗位管理', 'system/job/index', 'job', '1', 'Steve-Jobs', '7', '0', '2019-10-12 16:53:09', '2019-10-12 03:53:28');
INSERT INTO `sys_menu` VALUES ('19', '字典管理', 'system/dict/index', 'dict', '1', 'dictionary', '10', '0', '2019-10-12 16:54:32', '2019-10-12 03:54:49');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `alias` varchar(255) DEFAULT NULL COMMENT '别名',
  `name` varchar(255) DEFAULT NULL COMMENT '权限名称',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '上级权限(0 无上级 ）',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '软删除标识(0 有效 1 已删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='权限';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', '超级管理员', 'SUPER', '0', '0', '2019-09-29 19:03:46', '2019-09-29 19:03:46');
INSERT INTO `sys_permission` VALUES ('2', '测试', 'TEST', '0', '0', '2019-10-10 11:00:40', '2019-10-10 11:00:40');
INSERT INTO `sys_permission` VALUES ('3', '用户管理', 'USER_ALL', '0', '0', '2019-10-15 20:17:40', '2019-10-15 20:17:40');
INSERT INTO `sys_permission` VALUES ('4', '用户查询', 'USER_SELECT', '3', '0', '2019-10-15 20:18:27', '2019-10-15 20:18:27');
INSERT INTO `sys_permission` VALUES ('5', '用户创建', 'USER_CREATE', '3', '0', '2019-10-15 20:18:27', '2019-10-15 20:18:27');
INSERT INTO `sys_permission` VALUES ('6', '用户编辑', 'USER_EDIT', '3', '0', '2019-10-15 20:18:27', '2019-10-15 20:18:27');
INSERT INTO `sys_permission` VALUES ('7', '用户删除', 'USER_DELETE', '3', '0', '2019-10-15 20:18:27', '2019-10-15 20:18:27');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(100) NOT NULL COMMENT '角色名',
  `role_desc` varchar(100) DEFAULT NULL COMMENT '描述',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '软删除标识(0 有效 1 已删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', '超级管理员权限', '0', '2019-09-19 16:16:01', '2019-10-17 19:51:29');
INSERT INTO `sys_role` VALUES ('2', '普通用户', '普通用户权限', '0', '2019-09-19 16:16:07', '2019-10-17 19:52:23');
INSERT INTO `sys_role` VALUES ('4', '测试', '测试', '0', '2019-10-17 17:25:50', '2019-10-17 17:25:48');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色主键',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单主键',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8 COMMENT='角色与菜单关系表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '1', '1', '2019-09-29 17:14:31', '2019-09-29 17:14:31');
INSERT INTO `sys_role_menu` VALUES ('2', '1', '2', '2019-09-29 17:14:31', '2019-09-29 17:14:31');
INSERT INTO `sys_role_menu` VALUES ('3', '1', '3', '2019-09-29 17:14:31', '2019-09-29 17:14:31');
INSERT INTO `sys_role_menu` VALUES ('4', '1', '4', '2019-09-29 17:14:31', '2019-09-29 17:14:31');
INSERT INTO `sys_role_menu` VALUES ('8', '1', '5', '2019-10-09 15:11:52', '2019-10-09 15:11:52');
INSERT INTO `sys_role_menu` VALUES ('9', '1', '6', '2019-10-09 15:11:56', '2019-10-09 15:11:56');
INSERT INTO `sys_role_menu` VALUES ('10', '1', '7', '2019-10-09 15:57:03', '2019-10-09 15:57:03');
INSERT INTO `sys_role_menu` VALUES ('11', '1', '8', '2019-10-10 19:27:20', '2019-10-10 19:27:20');
INSERT INTO `sys_role_menu` VALUES ('12', '1', '9', '2019-10-10 19:27:23', '2019-10-10 19:27:23');
INSERT INTO `sys_role_menu` VALUES ('13', '1', '10', '2019-10-10 19:27:27', '2019-10-10 19:27:27');
INSERT INTO `sys_role_menu` VALUES ('14', '1', '11', '2019-10-10 19:27:30', '2019-10-10 19:27:30');
INSERT INTO `sys_role_menu` VALUES ('15', '1', '12', '2019-10-10 19:27:34', '2019-10-10 19:27:34');
INSERT INTO `sys_role_menu` VALUES ('16', '1', '13', '2019-10-10 20:22:43', '2019-10-10 20:22:43');
INSERT INTO `sys_role_menu` VALUES ('17', '1', '16', '2019-10-11 16:58:29', '2019-10-11 16:58:29');
INSERT INTO `sys_role_menu` VALUES ('19', '1', '17', '2019-10-12 14:57:32', '2019-10-12 01:57:50');
INSERT INTO `sys_role_menu` VALUES ('20', '1', '18', '2019-10-12 16:53:09', '2019-10-12 03:53:28');
INSERT INTO `sys_role_menu` VALUES ('21', '1', '19', '2019-10-12 16:54:32', '2019-10-12 03:54:50');
INSERT INTO `sys_role_menu` VALUES ('22', '1', '20', '2019-10-17 15:16:39', '2019-10-17 15:16:37');
INSERT INTO `sys_role_menu` VALUES ('87', '2', '1', '2019-10-17 19:55:49', '2019-10-17 19:55:47');
INSERT INTO `sys_role_menu` VALUES ('88', '2', '2', '2019-10-17 19:55:49', '2019-10-17 19:55:47');
INSERT INTO `sys_role_menu` VALUES ('89', '2', '3', '2019-10-17 19:55:49', '2019-10-17 19:55:47');
INSERT INTO `sys_role_menu` VALUES ('90', '4', '9', '2019-10-17 19:56:05', '2019-10-17 19:56:02');
INSERT INTO `sys_role_menu` VALUES ('91', '4', '10', '2019-10-17 19:56:05', '2019-10-17 19:56:02');
INSERT INTO `sys_role_menu` VALUES ('92', '4', '12', '2019-10-17 19:56:05', '2019-10-17 19:56:02');
INSERT INTO `sys_role_menu` VALUES ('93', '4', '13', '2019-10-17 19:56:05', '2019-10-17 19:56:02');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色主键',
  `per_id` bigint(20) NOT NULL COMMENT '权限主键',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='角色与权限关系表';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1', '1', '1', '2019-09-29 19:26:13', '2019-09-29 19:26:13');
INSERT INTO `sys_role_permission` VALUES ('2', '1', '2', '2019-10-10 11:01:09', '2019-10-10 11:01:09');
INSERT INTO `sys_role_permission` VALUES ('35', '4', '4', '2019-10-17 19:56:51', '2019-10-17 19:56:49');
INSERT INTO `sys_role_permission` VALUES ('36', '4', '5', '2019-10-17 19:56:51', '2019-10-17 19:56:49');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(256) NOT NULL COMMENT '用户名',
  `password` varchar(256) NOT NULL COMMENT '密码',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `sex` tinyint(1) DEFAULT '0' COMMENT '性别，未设置-0，男-1，女-2',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `job_id` bigint(20) DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态，启用-1，禁用-0',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '软删除标识(0 有效 1 已删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_dept_id` (`dept_id`),
  KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'super', '$2a$10$EP82BAQFU0g1VKvIpefSJ..ph5fczlVQ8hnZ.DS5X/BQ.GX53QSTe', 'Super管理员', '17300000000', 'zengms0708@gmail.com', '1', '1', '1', '1', '0', '2019-09-20 19:15:45', '2019-09-20 19:15:45');
INSERT INTO `sys_user` VALUES ('2', 'test', '$2a$10$EP82BAQFU0g1VKvIpefSJ..ph5fczlVQ8hnZ.DS5X/BQ.GX53QSTe', '测试用户', null, null, '0', '1', '3', '0', '0', '2019-09-20 23:05:27', '2019-09-20 23:05:27');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色主键',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1', '2019-09-20 19:26:40', '2019-09-20 19:26:40');
INSERT INTO `sys_user_role` VALUES ('2', '2', '2', '2019-09-20 23:06:05', '2019-09-20 23:06:05');
