/*
 Navicat Premium Data Transfer

 Source Server         : zkh
 Source Server Type    : MySQL
 Source Server Version : 80024
 Source Host           : localhost:3306
 Source Schema         : smelldetector

 Target Server Type    : MySQL
 Target Server Version : 80024
 File Encoding         : 65001

 Date: 24/05/2023 15:26:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for classlink
-- ----------------------------
DROP TABLE IF EXISTS `classlink`;
CREATE TABLE `classlink`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `source` int NULL DEFAULT NULL,
  `target` int NULL DEFAULT NULL,
  `linkName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `linkType` int NULL DEFAULT NULL,
  `projectName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1978 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for classnode
-- ----------------------------
DROP TABLE IF EXISTS `classnode`;
CREATE TABLE `classnode`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `projectName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `packageName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `className` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `type` int NULL DEFAULT NULL,
  `isJavadoc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `author` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `since` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `version` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `loc` int NULL DEFAULT NULL,
  `NMNOPARAM` int NULL DEFAULT NULL,
  `wmc` int NULL DEFAULT NULL,
  `norm` int NULL DEFAULT NULL,
  `superType` int NULL DEFAULT NULL,
  `isSC` int NULL DEFAULT NULL,
  `isLC` int NULL DEFAULT NULL,
  `isCC` int NULL DEFAULT NULL,
  `isRB` int NULL DEFAULT NULL,
  `nprom` int NULL DEFAULT NULL,
  `bur` float NULL DEFAULT NULL,
  `nom` int NULL DEFAULT NULL,
  `nof` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1040 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for classtree
-- ----------------------------
DROP TABLE IF EXISTS `classtree`;
CREATE TABLE `classtree`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `projectName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `className` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `treeDepth` int NULL DEFAULT NULL,
  `treeWidth` int NULL DEFAULT NULL,
  `nodeSum` int NULL DEFAULT NULL,
  `maxChildren` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 897 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for complexclass
-- ----------------------------
DROP TABLE IF EXISTS `complexclass`;
CREATE TABLE `complexclass`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `projectName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `className` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `wmc` int NULL DEFAULT NULL,
  `possibility` double NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for featureenvy
-- ----------------------------
DROP TABLE IF EXISTS `featureenvy`;
CREATE TABLE `featureenvy`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `projectName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `className` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `methodName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `atfd` int NULL DEFAULT NULL,
  `possibility` double NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for largeclass
-- ----------------------------
DROP TABLE IF EXISTS `largeclass`;
CREATE TABLE `largeclass`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `projectName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `className` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `col` int NULL DEFAULT NULL,
  `nof` int NULL DEFAULT NULL,
  `nom` int NULL DEFAULT NULL,
  `treeDepth` int NULL DEFAULT NULL,
  `treeWidth` int NULL DEFAULT NULL,
  `nodeSum` int NULL DEFAULT NULL,
  `maxChildren` int NULL DEFAULT NULL,
  `possibility` double NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for longmethod
-- ----------------------------
DROP TABLE IF EXISTS `longmethod`;
CREATE TABLE `longmethod`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `projectName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `className` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `methodName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `mloc` int NULL DEFAULT NULL,
  `nop` int NULL DEFAULT NULL,
  `nos` int NULL DEFAULT NULL,
  `treeDepth` int NULL DEFAULT NULL,
  `treeWidth` int NULL DEFAULT NULL,
  `nodeSum` int NULL DEFAULT NULL,
  `maxChildren` int NULL DEFAULT NULL,
  `possibility` double NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 71 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for messagechain
-- ----------------------------
DROP TABLE IF EXISTS `messagechain`;
CREATE TABLE `messagechain`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `projectName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `className` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `methodName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `mcc` int NULL DEFAULT NULL,
  `possibility` double NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for methodlink
-- ----------------------------
DROP TABLE IF EXISTS `methodlink`;
CREATE TABLE `methodlink`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `source` int NULL DEFAULT NULL,
  `target` int NULL DEFAULT NULL,
  `projectName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `linkType` int NULL DEFAULT NULL,
  `timeCount` int NULL DEFAULT NULL,
  `mcc` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6511 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for methodnode
-- ----------------------------
DROP TABLE IF EXISTS `methodnode`;
CREATE TABLE `methodnode`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `classNodeId` int NULL DEFAULT NULL,
  `className` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `methodName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `methodType` int NULL DEFAULT NULL,
  `returnType` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `parameter` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `arguementCount` int NULL DEFAULT NULL,
  `nos` int NULL DEFAULT NULL,
  `type` int NULL DEFAULT NULL,
  `projectName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `isJavadoc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `methodLine` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `classNodeId`(`classNodeId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8685 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for methodtree
-- ----------------------------
DROP TABLE IF EXISTS `methodtree`;
CREATE TABLE `methodtree`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `projectName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `className` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `methodName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `treeDepth` int NULL DEFAULT NULL,
  `treeWidth` int NULL DEFAULT NULL,
  `nodeSum` int NULL DEFAULT NULL,
  `maxChildren` int NULL DEFAULT NULL,
  `mloc` int NULL DEFAULT NULL,
  `nop` int NULL DEFAULT NULL,
  `nos` int NULL DEFAULT NULL,
  `nogv` int NULL DEFAULT NULL,
  `mcn` int NULL DEFAULT NULL,
  `atfd` int NULL DEFAULT NULL,
  `isLM` int NULL DEFAULT NULL,
  `isMC` int NULL DEFAULT NULL,
  `isFE` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8331 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for node
-- ----------------------------
DROP TABLE IF EXISTS `node`;
CREATE TABLE `node`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `nodeType` int NULL DEFAULT NULL,
  `packageName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `projectName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 897 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for projectinfo
-- ----------------------------
DROP TABLE IF EXISTS `projectinfo`;
CREATE TABLE `projectinfo`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `projectName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `classSum` int NULL DEFAULT NULL,
  `methodSum` int NULL DEFAULT NULL,
  `lineSum` int NULL DEFAULT NULL,
  `authorSum` int NULL DEFAULT NULL,
  `methodJavadoc` int NULL DEFAULT NULL,
  `classJavadoc` int NULL DEFAULT NULL,
  `classLinkSum` int NULL DEFAULT NULL,
  `createTime` date NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for refusedbequest
-- ----------------------------
DROP TABLE IF EXISTS `refusedbequest`;
CREATE TABLE `refusedbequest`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `projectName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `className` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `norm` int NULL DEFAULT NULL,
  `nprotm` int NULL DEFAULT NULL,
  `bur` float NULL DEFAULT NULL,
  `possibility` double NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for source
-- ----------------------------
DROP TABLE IF EXISTS `source`;
CREATE TABLE `source`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `names` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `methods` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `fields` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `fieldSum` int NULL DEFAULT NULL,
  `methodNames` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `methodSum` int NULL DEFAULT NULL,
  `importClassMap` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `superClassName` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `interfaceName` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `projectName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `dateCreateTime` date NULL DEFAULT NULL,
  `dateChangeLastTime` date NULL DEFAULT NULL,
  `lineSum` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 897 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for spaghetticode
-- ----------------------------
DROP TABLE IF EXISTS `spaghetticode`;
CREATE TABLE `spaghetticode`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `projectName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `className` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `nmnoparam` int NULL DEFAULT NULL,
  `loc` int NULL DEFAULT NULL,
  `nogv` int NULL DEFAULT NULL,
  `possibility` double NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
