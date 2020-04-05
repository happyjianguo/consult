/*
 Navicat Premium Data Transfer

 Source Server         : mysql8_local
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : consult

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 03/04/2020 17:58:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for consult_state_machine_context
-- ----------------------------
DROP TABLE IF EXISTS `consult_state_machine_context`;
CREATE TABLE `consult_state_machine_context`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `biz_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `current_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `context_str` varbinary(1000) NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of consult_state_machine_context
-- ----------------------------
INSERT INTO `consult_state_machine_context` VALUES (11, '2', '待咨询', 0x4151414241474E766253357A61336C696248566C4C6E4E305958526C6257466A61476C755A53356A6232357A645778304C6D56756457317A4C6B4E76626E4E316248526864476C76626C4E3059585231387745434141454262334A6E4C6E4E77636D6C755A325A795957316C643239796179357A644746305A57316859326870626D5575633356776347397964433550596E4E6C636E5A68596D786C5457487741514142416D7068646D45756458527062433542636E4A6865557870632F514241414544616D46325953353164476C734C6B68686332684E5966414241414D4259323975633356736447463061573975553352686448567A5457466A61476C755A556E6B, '2020-03-29 23:02:15', '2020-03-29 23:02:15');

-- ----------------------------
-- Table structure for doctor_advisory_price
-- ----------------------------
DROP TABLE IF EXISTS `doctor_advisory_price`;
CREATE TABLE `doctor_advisory_price`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `doctor_id` bigint(20) NOT NULL COMMENT '医生ID',
  `advisory_price` int(10) NOT NULL COMMENT '咨询价格：分',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `gmt_modify` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 250 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医生咨询自定义价格表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of doctor_advisory_price
-- ----------------------------
INSERT INTO `doctor_advisory_price` VALUES (1, 123456, 2000, '2018-09-05 16:34:09', '2018-09-05 16:37:44');
INSERT INTO `doctor_advisory_price` VALUES (2, 1823784, 100, '2018-09-11 11:25:34', '2019-07-02 15:44:24');
INSERT INTO `doctor_advisory_price` VALUES (3, 1000247, 0, '2018-09-11 11:41:31', '2018-12-18 17:44:54');
INSERT INTO `doctor_advisory_price` VALUES (4, 44612299, 100, '2018-09-11 13:55:33', '2019-12-26 15:40:27');
INSERT INTO `doctor_advisory_price` VALUES (5, 44612298, 300, '2018-09-11 14:33:48', '2019-01-18 14:32:31');
INSERT INTO `doctor_advisory_price` VALUES (6, 61823709, 10000, '2018-09-11 16:28:57', '2018-09-11 16:28:57');
INSERT INTO `doctor_advisory_price` VALUES (7, 1899974, 10000, '2018-09-11 16:30:03', '2018-09-11 16:30:03');
INSERT INTO `doctor_advisory_price` VALUES (8, 61823711, 10000, '2018-09-11 17:16:07', '2018-09-11 17:16:07');
INSERT INTO `doctor_advisory_price` VALUES (9, 1610524, 0, '2018-09-11 17:31:50', '2019-08-22 14:16:45');
INSERT INTO `doctor_advisory_price` VALUES (10, 61823712, 2500, '2018-09-11 17:32:51', '2018-09-12 11:29:54');
INSERT INTO `doctor_advisory_price` VALUES (11, 61913713, 10000, '2018-09-12 11:17:38', '2018-09-12 11:17:38');
INSERT INTO `doctor_advisory_price` VALUES (12, 1610440, 100, '2018-09-12 14:47:32', '2018-09-12 14:51:36');
INSERT INTO `doctor_advisory_price` VALUES (13, 1902393, 500, '2018-09-13 11:46:29', '2018-09-13 11:46:29');
INSERT INTO `doctor_advisory_price` VALUES (14, 1587827, 100, '2018-09-13 14:45:26', '2018-09-15 10:40:00');
INSERT INTO `doctor_advisory_price` VALUES (15, 55013052, 0, '2018-09-13 16:06:28', '2019-01-21 14:19:06');
INSERT INTO `doctor_advisory_price` VALUES (16, 62023721, 100, '2018-09-13 17:44:24', '2018-09-13 17:44:24');
INSERT INTO `doctor_advisory_price` VALUES (18, 1902331, 1200, '2018-09-13 17:51:11', '2018-09-18 15:39:06');
INSERT INTO `doctor_advisory_price` VALUES (19, 49222766, 0, '2018-09-13 17:52:29', '2019-11-07 15:58:51');
INSERT INTO `doctor_advisory_price` VALUES (21, 62023723, 2500, '2018-09-13 17:56:35', '2018-09-13 17:56:35');
INSERT INTO `doctor_advisory_price` VALUES (22, 1587934, 6000, '2018-09-13 17:57:04', '2018-09-18 16:10:22');
INSERT INTO `doctor_advisory_price` VALUES (23, 32321043, 10000, '2018-09-13 18:20:19', '2018-09-13 18:20:19');
INSERT INTO `doctor_advisory_price` VALUES (24, 62023722, 0, '2018-09-14 10:28:00', '2018-09-14 10:28:00');
INSERT INTO `doctor_advisory_price` VALUES (25, 49522775, 0, '2018-09-14 11:00:23', '2019-05-28 11:47:12');
INSERT INTO `doctor_advisory_price` VALUES (26, 50622828, 100, '2018-09-14 14:08:55', '2019-12-27 14:41:14');
INSERT INTO `doctor_advisory_price` VALUES (27, 31210890, 100, '2018-09-14 14:27:48', '2019-01-15 14:24:37');
INSERT INTO `doctor_advisory_price` VALUES (28, 45822396, 200, '2018-09-14 14:59:14', '2019-01-18 10:25:16');
INSERT INTO `doctor_advisory_price` VALUES (29, 47322576, 100, '2018-09-15 10:01:26', '2018-09-15 10:01:26');
INSERT INTO `doctor_advisory_price` VALUES (30, 50632833, 0, '2018-09-15 10:09:24', '2018-10-10 16:58:38');
INSERT INTO `doctor_advisory_price` VALUES (31, 53623010, 10000, '2018-09-15 10:43:22', '2018-09-15 10:43:22');
INSERT INTO `doctor_advisory_price` VALUES (32, 61823707, 1000, '2018-09-15 10:51:27', '2018-09-15 10:51:27');
INSERT INTO `doctor_advisory_price` VALUES (33, 62213749, 10000, '2018-09-15 11:03:15', '2018-09-15 11:03:15');
INSERT INTO `doctor_advisory_price` VALUES (34, 55023053, 100, '2018-09-15 16:16:10', '2018-09-15 16:16:10');
INSERT INTO `doctor_advisory_price` VALUES (35, 58923322, 100, '2018-09-17 13:59:33', '2018-09-17 13:59:47');
INSERT INTO `doctor_advisory_price` VALUES (36, 1000921, 0, '2018-09-17 14:13:18', '2018-09-17 14:53:08');
INSERT INTO `doctor_advisory_price` VALUES (37, 1590251, 5000, '2018-09-17 15:04:25', '2018-09-17 15:04:25');
INSERT INTO `doctor_advisory_price` VALUES (38, 62423761, 100, '2018-09-17 15:40:48', '2018-09-17 15:40:48');
INSERT INTO `doctor_advisory_price` VALUES (39, 62423760, 30000, '2018-09-17 15:50:56', '2018-09-17 15:51:01');
INSERT INTO `doctor_advisory_price` VALUES (40, 130, 100, '2018-09-17 16:04:33', '2018-09-17 16:27:21');
INSERT INTO `doctor_advisory_price` VALUES (41, 43622246, 100, '2018-09-17 16:06:39', '2018-09-17 16:26:37');
INSERT INTO `doctor_advisory_price` VALUES (42, 58323279, 0, '2018-09-17 16:32:03', '2019-03-04 14:04:07');
INSERT INTO `doctor_advisory_price` VALUES (43, 62423762, 0, '2018-09-17 16:34:23', '2018-09-17 16:34:23');
INSERT INTO `doctor_advisory_price` VALUES (44, 1903409, 600, '2018-09-17 17:54:13', '2018-09-20 17:50:43');
INSERT INTO `doctor_advisory_price` VALUES (45, 1905272, 300, '2018-09-17 18:33:54', '2018-09-17 18:33:54');
INSERT INTO `doctor_advisory_price` VALUES (46, 62513764, 10000, '2018-09-18 10:23:42', '2018-09-18 10:23:42');
INSERT INTO `doctor_advisory_price` VALUES (47, 1902487, 0, '2018-09-18 11:02:38', '2018-09-21 11:41:27');
INSERT INTO `doctor_advisory_price` VALUES (48, 1610611, 12300, '2018-09-18 14:47:28', '2018-09-18 14:47:28');
INSERT INTO `doctor_advisory_price` VALUES (49, 62523770, 0, '2018-09-18 15:40:05', '2018-09-18 15:40:05');
INSERT INTO `doctor_advisory_price` VALUES (50, 1587813, 500, '2018-09-18 16:46:35', '2018-09-20 17:16:05');
INSERT INTO `doctor_advisory_price` VALUES (51, 30510666, 300, '2018-09-19 11:54:34', '2018-09-19 14:29:46');
INSERT INTO `doctor_advisory_price` VALUES (52, 117, 100, '2018-09-19 14:56:32', '2019-12-28 15:48:53');
INSERT INTO `doctor_advisory_price` VALUES (53, 1610439, 100, '2018-09-19 17:46:34', '2018-09-19 17:46:34');
INSERT INTO `doctor_advisory_price` VALUES (54, 48022621, 100, '2018-09-20 11:40:09', '2018-09-20 11:40:09');
INSERT INTO `doctor_advisory_price` VALUES (55, 61313688, 0, '2018-09-20 15:00:38', '2018-11-01 16:23:59');
INSERT INTO `doctor_advisory_price` VALUES (56, 51922914, 100, '2018-09-20 16:07:12', '2020-03-05 16:30:34');
INSERT INTO `doctor_advisory_price` VALUES (57, 62723782, 5000, '2018-09-20 16:36:38', '2018-09-21 16:11:08');
INSERT INTO `doctor_advisory_price` VALUES (58, 30510664, 0, '2018-09-20 16:57:47', '2018-09-20 16:57:47');
INSERT INTO `doctor_advisory_price` VALUES (59, 1610668, 0, '2018-09-20 17:38:17', '2018-09-21 11:28:55');
INSERT INTO `doctor_advisory_price` VALUES (60, 47712586, 300, '2018-09-21 14:06:32', '2018-09-21 14:06:47');
INSERT INTO `doctor_advisory_price` VALUES (61, 62823787, 10100, '2018-09-21 18:04:18', '2018-09-25 15:41:44');
INSERT INTO `doctor_advisory_price` VALUES (62, 55023054, 0, '2018-09-27 10:50:19', '2018-09-27 10:50:19');
INSERT INTO `doctor_advisory_price` VALUES (63, 47422580, 0, '2018-10-09 14:25:47', '2018-10-09 14:25:47');
INSERT INTO `doctor_advisory_price` VALUES (64, 62223751, 1000, '2018-10-10 15:06:17', '2018-10-10 15:06:17');
INSERT INTO `doctor_advisory_price` VALUES (65, 61023629, 20000, '2018-10-11 10:56:16', '2018-10-11 10:56:16');
INSERT INTO `doctor_advisory_price` VALUES (66, 49622780, 0, '2018-10-11 11:16:06', '2018-10-11 11:16:06');
INSERT INTO `doctor_advisory_price` VALUES (67, 49622781, 0, '2018-10-11 11:16:43', '2018-10-11 11:16:43');
INSERT INTO `doctor_advisory_price` VALUES (68, 1610330, 0, '2018-10-15 15:53:05', '2019-08-03 16:27:10');
INSERT INTO `doctor_advisory_price` VALUES (69, 1610613, 100, '2018-10-17 13:59:51', '2018-10-17 13:59:51');
INSERT INTO `doctor_advisory_price` VALUES (70, 65523857, 0, '2018-10-18 14:42:35', '2018-10-18 14:42:35');
INSERT INTO `doctor_advisory_price` VALUES (71, 65523859, 10000, '2018-10-18 16:51:29', '2018-10-18 16:51:29');
INSERT INTO `doctor_advisory_price` VALUES (72, 65313846, 100, '2018-10-19 14:25:32', '2018-10-19 14:25:32');
INSERT INTO `doctor_advisory_price` VALUES (73, 65613863, 10000, '2018-10-19 14:43:57', '2018-10-19 14:43:57');
INSERT INTO `doctor_advisory_price` VALUES (74, 1904058, 200, '2018-10-22 14:19:02', '2018-10-22 14:19:02');
INSERT INTO `doctor_advisory_price` VALUES (75, 65923873, 1000, '2018-10-22 16:52:54', '2018-10-22 16:52:54');
INSERT INTO `doctor_advisory_price` VALUES (76, 66013877, 1000, '2018-10-23 10:35:32', '2018-10-23 10:35:32');
INSERT INTO `doctor_advisory_price` VALUES (77, 66113881, 200, '2018-10-24 11:16:43', '2018-10-24 11:16:43');
INSERT INTO `doctor_advisory_price` VALUES (78, 1901292, 0, '2018-10-25 16:16:55', '2018-10-25 16:16:55');
INSERT INTO `doctor_advisory_price` VALUES (79, 66223901, 0, '2018-10-25 16:28:31', '2018-10-25 16:28:31');
INSERT INTO `doctor_advisory_price` VALUES (80, 66223902, 0, '2018-10-25 17:17:50', '2018-10-25 17:17:50');
INSERT INTO `doctor_advisory_price` VALUES (81, 66223908, 0, '2018-10-25 17:53:37', '2018-10-25 17:53:37');
INSERT INTO `doctor_advisory_price` VALUES (82, 66213884, 0, '2018-10-26 10:27:31', '2018-10-26 10:27:31');
INSERT INTO `doctor_advisory_price` VALUES (83, 66213888, 0, '2018-10-26 10:54:18', '2018-10-26 10:54:18');
INSERT INTO `doctor_advisory_price` VALUES (84, 66223900, 200, '2018-10-26 11:23:19', '2018-10-26 11:23:19');
INSERT INTO `doctor_advisory_price` VALUES (85, 66613964, 100, '2018-10-29 11:24:33', '2018-10-29 11:24:33');
INSERT INTO `doctor_advisory_price` VALUES (86, 66724001, 0, '2018-10-31 11:18:21', '2018-10-31 11:18:21');
INSERT INTO `doctor_advisory_price` VALUES (87, 66734003, 0, '2018-10-31 14:50:32', '2018-10-31 14:50:32');
INSERT INTO `doctor_advisory_price` VALUES (88, 1903323, 1100, '2018-11-01 15:22:37', '2018-11-01 15:23:18');
INSERT INTO `doctor_advisory_price` VALUES (89, 66924037, 200, '2018-11-01 17:07:52', '2018-11-01 17:07:52');
INSERT INTO `doctor_advisory_price` VALUES (90, 66924036, 0, '2018-11-01 17:12:39', '2018-11-01 17:12:39');
INSERT INTO `doctor_advisory_price` VALUES (91, 67124122, 0, '2018-11-03 17:57:23', '2018-11-03 17:57:23');
INSERT INTO `doctor_advisory_price` VALUES (92, 67124123, 0, '2018-11-03 18:02:27', '2018-11-03 18:02:27');
INSERT INTO `doctor_advisory_price` VALUES (93, 67324195, 0, '2018-11-05 14:38:31', '2018-11-05 14:38:31');
INSERT INTO `doctor_advisory_price` VALUES (94, 67324196, 0, '2018-11-05 14:46:48', '2018-11-05 14:46:48');
INSERT INTO `doctor_advisory_price` VALUES (95, 67324199, 0, '2018-11-05 16:42:37', '2018-11-05 16:42:37');
INSERT INTO `doctor_advisory_price` VALUES (96, 67404222, 0, '2018-11-06 11:43:51', '2018-11-06 11:43:51');
INSERT INTO `doctor_advisory_price` VALUES (97, 66723989, 0, '2018-11-06 14:30:05', '2018-11-06 14:30:05');
INSERT INTO `doctor_advisory_price` VALUES (98, 1904987, 100, '2018-11-07 11:20:23', '2018-11-07 11:20:23');
INSERT INTO `doctor_advisory_price` VALUES (99, 1900116, 0, '2018-11-08 19:32:53', '2018-11-08 19:32:53');
INSERT INTO `doctor_advisory_price` VALUES (100, 67514270, 0, '2018-11-09 15:04:13', '2018-11-09 15:04:13');
INSERT INTO `doctor_advisory_price` VALUES (101, 67714276, 0, '2018-11-09 16:45:42', '2019-11-20 13:46:43');
INSERT INTO `doctor_advisory_price` VALUES (102, 67714278, 0, '2018-11-09 18:55:58', '2019-01-15 17:01:09');
INSERT INTO `doctor_advisory_price` VALUES (103, 67714280, 0, '2018-11-09 19:14:44', '2018-11-09 19:14:44');
INSERT INTO `doctor_advisory_price` VALUES (104, 54823048, 0, '2018-11-11 16:10:22', '2018-11-11 16:10:22');
INSERT INTO `doctor_advisory_price` VALUES (105, 67914281, 0, '2018-11-11 20:09:21', '2018-11-11 20:09:21');
INSERT INTO `doctor_advisory_price` VALUES (106, 67314169, 100, '2018-11-11 20:41:36', '2018-11-11 20:41:36');
INSERT INTO `doctor_advisory_price` VALUES (107, 67314168, 100, '2018-11-11 20:49:20', '2018-11-11 20:49:20');
INSERT INTO `doctor_advisory_price` VALUES (108, 68004284, 0, '2018-11-12 10:43:58', '2018-11-12 10:43:58');
INSERT INTO `doctor_advisory_price` VALUES (109, 66713982, 0, '2018-11-12 11:11:23', '2018-11-12 11:11:23');
INSERT INTO `doctor_advisory_price` VALUES (110, 1610314, 100, '2018-11-12 11:29:20', '2018-11-12 11:29:20');
INSERT INTO `doctor_advisory_price` VALUES (111, 1525246, 100, '2018-11-12 11:32:20', '2018-11-12 11:32:20');
INSERT INTO `doctor_advisory_price` VALUES (112, 68004287, 100, '2018-11-12 11:37:16', '2018-11-12 11:37:16');
INSERT INTO `doctor_advisory_price` VALUES (113, 67404245, 200, '2018-11-12 14:02:57', '2018-11-12 14:02:57');
INSERT INTO `doctor_advisory_price` VALUES (114, 37911809, 200, '2018-11-12 17:50:46', '2018-11-12 17:50:46');
INSERT INTO `doctor_advisory_price` VALUES (115, 67314165, 0, '2018-11-12 18:32:54', '2018-11-12 18:32:54');
INSERT INTO `doctor_advisory_price` VALUES (116, 518, 0, '2018-11-12 19:13:47', '2018-11-12 19:13:47');
INSERT INTO `doctor_advisory_price` VALUES (117, 521, 0, '2018-11-12 19:20:16', '2018-11-12 19:20:16');
INSERT INTO `doctor_advisory_price` VALUES (118, 1901370, 0, '2018-11-12 19:29:50', '2018-11-12 19:29:50');
INSERT INTO `doctor_advisory_price` VALUES (119, 68024302, 0, '2018-11-13 10:14:11', '2018-11-13 10:14:11');
INSERT INTO `doctor_advisory_price` VALUES (120, 37921828, 100, '2018-11-13 10:31:23', '2018-11-13 10:31:23');
INSERT INTO `doctor_advisory_price` VALUES (121, 37921826, 200, '2018-11-13 10:35:06', '2018-11-13 10:35:06');
INSERT INTO `doctor_advisory_price` VALUES (122, 1900661, 0, '2018-11-13 10:38:52', '2018-11-13 10:38:52');
INSERT INTO `doctor_advisory_price` VALUES (123, 1901739, 0, '2018-11-13 10:40:04', '2018-11-13 10:40:04');
INSERT INTO `doctor_advisory_price` VALUES (124, 68104304, 0, '2018-11-13 10:52:33', '2018-11-13 10:52:33');
INSERT INTO `doctor_advisory_price` VALUES (125, 68104305, 0, '2018-11-13 10:57:30', '2018-11-13 10:57:30');
INSERT INTO `doctor_advisory_price` VALUES (126, 1000984, 0, '2018-11-13 11:02:51', '2018-11-13 11:02:51');
INSERT INTO `doctor_advisory_price` VALUES (127, 67714275, 0, '2018-11-13 11:18:36', '2018-11-13 11:18:36');
INSERT INTO `doctor_advisory_price` VALUES (128, 51112847, 0, '2018-11-13 14:13:04', '2018-11-13 14:13:04');
INSERT INTO `doctor_advisory_price` VALUES (129, 1900670, 0, '2018-11-13 14:22:36', '2018-11-13 14:22:36');
INSERT INTO `doctor_advisory_price` VALUES (130, 1901740, 0, '2018-11-13 14:28:50', '2018-11-13 14:28:50');
INSERT INTO `doctor_advisory_price` VALUES (131, 68114307, 0, '2018-11-13 15:26:25', '2018-11-13 15:26:25');
INSERT INTO `doctor_advisory_price` VALUES (132, 68114310, 0, '2018-11-13 17:14:09', '2018-11-13 17:14:09');
INSERT INTO `doctor_advisory_price` VALUES (133, 66623965, 0, '2018-11-13 17:51:54', '2018-11-13 17:51:54');
INSERT INTO `doctor_advisory_price` VALUES (134, 51922912, 0, '2018-11-13 19:38:13', '2018-11-13 19:38:13');
INSERT INTO `doctor_advisory_price` VALUES (135, 50712835, 0, '2018-11-13 19:55:03', '2018-11-13 19:55:03');
INSERT INTO `doctor_advisory_price` VALUES (136, 68124319, 0, '2018-11-13 20:57:42', '2018-11-13 20:57:42');
INSERT INTO `doctor_advisory_price` VALUES (137, 1002336, 0, '2018-11-13 23:01:07', '2018-11-13 23:01:07');
INSERT INTO `doctor_advisory_price` VALUES (138, 1201736, 0, '2018-11-13 23:02:49', '2018-11-13 23:02:49');
INSERT INTO `doctor_advisory_price` VALUES (139, 68124321, 0, '2018-11-13 23:35:38', '2018-11-13 23:35:38');
INSERT INTO `doctor_advisory_price` VALUES (140, 68214323, 0, '2018-11-14 16:03:34', '2018-11-14 16:03:34');
INSERT INTO `doctor_advisory_price` VALUES (141, 69514442, 200, '2018-11-27 10:03:53', '2018-11-28 16:21:25');
INSERT INTO `doctor_advisory_price` VALUES (142, 57623161, 200, '2018-12-05 14:16:24', '2019-01-21 16:01:35');
INSERT INTO `doctor_advisory_price` VALUES (143, 70414468, 100, '2018-12-06 10:25:31', '2018-12-06 10:25:31');
INSERT INTO `doctor_advisory_price` VALUES (144, 1903992, 0, '2018-12-06 17:50:00', '2018-12-06 17:50:00');
INSERT INTO `doctor_advisory_price` VALUES (145, 67714279, 0, '2018-12-25 17:53:49', '2019-02-01 16:20:25');
INSERT INTO `doctor_advisory_price` VALUES (146, 1524320, 100, '2018-12-27 11:12:44', '2018-12-27 11:12:44');
INSERT INTO `doctor_advisory_price` VALUES (147, 1610535, 0, '2019-01-10 17:08:25', '2019-01-10 17:08:25');
INSERT INTO `doctor_advisory_price` VALUES (148, 127, 0, '2019-01-15 14:50:57', '2019-01-15 14:50:57');
INSERT INTO `doctor_advisory_price` VALUES (149, 131, 200, '2019-01-18 14:37:55', '2019-12-25 19:38:04');
INSERT INTO `doctor_advisory_price` VALUES (150, 74825481, 100, '2019-01-19 16:31:59', '2019-01-19 16:31:59');
INSERT INTO `doctor_advisory_price` VALUES (151, 74825480, 100, '2019-01-21 13:50:08', '2019-01-21 13:50:08');
INSERT INTO `doctor_advisory_price` VALUES (152, 112, 0, '2019-01-24 12:42:41', '2019-01-24 12:42:41');
INSERT INTO `doctor_advisory_price` VALUES (153, 1610436, 0, '2019-01-28 14:45:19', '2019-01-28 14:45:19');
INSERT INTO `doctor_advisory_price` VALUES (154, 52322926, 0, '2019-01-28 15:11:03', '2019-01-28 15:11:03');
INSERT INTO `doctor_advisory_price` VALUES (155, 75825496, 0, '2019-01-29 15:21:06', '2019-01-29 15:21:06');
INSERT INTO `doctor_advisory_price` VALUES (156, 1902149, 0, '2019-02-28 14:22:53', '2019-02-28 14:22:53');
INSERT INTO `doctor_advisory_price` VALUES (157, 79225718, 0, '2019-03-04 14:14:23', '2019-03-04 14:14:23');
INSERT INTO `doctor_advisory_price` VALUES (158, 79435741, 0, '2019-04-26 16:39:26', '2019-04-26 16:39:26');
INSERT INTO `doctor_advisory_price` VALUES (159, 85414273, 0, '2019-05-05 13:43:36', '2019-05-05 13:43:36');
INSERT INTO `doctor_advisory_price` VALUES (160, 86224333, 0, '2019-05-13 16:01:40', '2019-06-13 14:37:22');
INSERT INTO `doctor_advisory_price` VALUES (161, 86314344, 100, '2019-05-14 10:28:01', '2019-05-14 11:16:57');
INSERT INTO `doctor_advisory_price` VALUES (162, 86314345, 0, '2019-05-14 13:54:33', '2019-05-14 13:54:33');
INSERT INTO `doctor_advisory_price` VALUES (163, 79525760, 0, '2019-05-22 14:44:42', '2019-05-22 14:44:42');
INSERT INTO `doctor_advisory_price` VALUES (164, 79435740, 0, '2019-05-23 18:27:10', '2019-05-23 18:27:10');
INSERT INTO `doctor_advisory_price` VALUES (165, 87324459, 100, '2019-05-24 13:09:25', '2019-05-24 13:09:25');
INSERT INTO `doctor_advisory_price` VALUES (166, 87714478, 0, '2019-05-28 11:46:11', '2019-05-28 11:46:11');
INSERT INTO `doctor_advisory_price` VALUES (167, 1520265, 0, '2019-05-31 19:03:34', '2019-05-31 19:03:34');
INSERT INTO `doctor_advisory_price` VALUES (168, 129, 0, '2019-06-10 17:19:08', '2019-06-10 17:19:08');
INSERT INTO `doctor_advisory_price` VALUES (169, 89114621, 10000, '2019-06-11 10:49:34', '2019-06-11 10:49:34');
INSERT INTO `doctor_advisory_price` VALUES (170, 89314678, 1000, '2019-06-13 09:41:30', '2019-06-13 09:41:30');
INSERT INTO `doctor_advisory_price` VALUES (171, 89314681, 400, '2019-06-13 11:19:04', '2019-06-13 11:19:04');
INSERT INTO `doctor_advisory_price` VALUES (172, 89324696, 30000, '2019-06-13 16:21:16', '2019-06-13 16:21:16');
INSERT INTO `doctor_advisory_price` VALUES (173, 89424707, 5500, '2019-06-14 16:17:47', '2019-06-14 16:17:47');
INSERT INTO `doctor_advisory_price` VALUES (174, 1017268, 0, '2019-06-20 19:13:18', '2019-06-20 19:13:18');
INSERT INTO `doctor_advisory_price` VALUES (175, 87324463, 100, '2019-06-26 16:11:11', '2019-06-26 16:11:11');
INSERT INTO `doctor_advisory_price` VALUES (176, 90714808, 0, '2019-06-27 10:12:26', '2019-06-27 10:12:26');
INSERT INTO `doctor_advisory_price` VALUES (177, 91824897, 100, '2019-07-08 15:13:13', '2019-07-08 15:13:13');
INSERT INTO `doctor_advisory_price` VALUES (178, 500055, 0, '2019-07-15 18:19:22', '2019-07-15 18:19:22');
INSERT INTO `doctor_advisory_price` VALUES (179, 1900338, 0, '2019-08-05 20:48:06', '2019-08-05 20:48:06');
INSERT INTO `doctor_advisory_price` VALUES (180, 95535192, 0, '2019-08-14 19:12:15', '2019-08-14 19:12:15');
INSERT INTO `doctor_advisory_price` VALUES (181, 96035283, 200, '2019-08-19 18:54:48', '2020-02-19 10:09:33');
INSERT INTO `doctor_advisory_price` VALUES (182, 97415424, 0, '2019-09-08 09:47:47', '2019-11-19 17:08:28');
INSERT INTO `doctor_advisory_price` VALUES (183, 97625443, 20000, '2019-09-23 10:56:49', '2019-09-23 10:56:49');
INSERT INTO `doctor_advisory_price` VALUES (184, 99915713, 100, '2019-09-27 11:50:43', '2019-11-29 09:59:38');
INSERT INTO `doctor_advisory_price` VALUES (185, 98015479, 0, '2019-11-29 09:48:40', '2019-11-29 09:48:40');
INSERT INTO `doctor_advisory_price` VALUES (186, 30420636, 0, '2019-12-05 18:10:54', '2019-12-05 18:10:54');
INSERT INTO `doctor_advisory_price` VALUES (187, 106910284, 0, '2019-12-09 09:25:34', '2019-12-09 09:25:34');
INSERT INTO `doctor_advisory_price` VALUES (188, 1610642, 500, '2019-12-09 17:44:25', '2019-12-09 17:44:25');
INSERT INTO `doctor_advisory_price` VALUES (189, 107320506, 0, '2019-12-11 14:44:15', '2019-12-16 17:22:13');
INSERT INTO `doctor_advisory_price` VALUES (190, 1901149, 0, '2019-12-11 18:55:40', '2019-12-11 18:55:40');
INSERT INTO `doctor_advisory_price` VALUES (191, 108220748, 0, '2019-12-19 17:12:08', '2019-12-19 17:12:08');
INSERT INTO `doctor_advisory_price` VALUES (192, 105310109, 100, '2019-12-20 15:48:27', '2019-12-24 10:03:19');
INSERT INTO `doctor_advisory_price` VALUES (193, 92129335, 0, '2019-12-20 16:25:27', '2019-12-20 16:25:27');
INSERT INTO `doctor_advisory_price` VALUES (194, 104420046, 0, '2019-12-20 18:11:08', '2019-12-20 18:11:08');
INSERT INTO `doctor_advisory_price` VALUES (195, 108620826, 100, '2019-12-23 17:30:17', '2019-12-23 17:30:17');
INSERT INTO `doctor_advisory_price` VALUES (196, 1001542, 0, '2019-12-24 10:56:33', '2019-12-24 10:56:33');
INSERT INTO `doctor_advisory_price` VALUES (197, 108820897, 100, '2019-12-25 14:54:50', '2019-12-25 14:54:50');
INSERT INTO `doctor_advisory_price` VALUES (198, 108820899, 200, '2019-12-25 15:26:45', '2019-12-25 15:26:45');
INSERT INTO `doctor_advisory_price` VALUES (199, 108920931, 0, '2019-12-26 14:31:07', '2019-12-26 14:31:07');
INSERT INTO `doctor_advisory_price` VALUES (200, 108920940, 0, '2019-12-26 14:32:25', '2019-12-26 14:32:25');
INSERT INTO `doctor_advisory_price` VALUES (201, 108920932, 0, '2019-12-26 14:34:18', '2019-12-26 14:34:18');
INSERT INTO `doctor_advisory_price` VALUES (202, 108920934, 0, '2019-12-26 14:35:29', '2019-12-26 14:35:29');
INSERT INTO `doctor_advisory_price` VALUES (203, 108920921, 30000, '2019-12-26 14:36:42', '2019-12-26 14:36:42');
INSERT INTO `doctor_advisory_price` VALUES (204, 108920941, 1000, '2019-12-26 14:49:12', '2019-12-26 14:49:12');
INSERT INTO `doctor_advisory_price` VALUES (205, 44222259, 300, '2019-12-27 14:49:19', '2019-12-27 14:49:19');
INSERT INTO `doctor_advisory_price` VALUES (206, 109611191, 0, '2020-01-02 10:40:36', '2020-01-02 10:40:36');
INSERT INTO `doctor_advisory_price` VALUES (207, 109713533, 0, '2020-01-03 11:31:57', '2020-01-03 11:31:57');
INSERT INTO `doctor_advisory_price` VALUES (208, 108920923, 0, '2020-01-06 10:57:19', '2020-01-06 10:57:19');
INSERT INTO `doctor_advisory_price` VALUES (209, 110824050, 0, '2020-01-14 15:05:02', '2020-01-14 15:05:02');
INSERT INTO `doctor_advisory_price` VALUES (210, 110724042, 0, '2020-01-15 15:07:53', '2020-01-15 15:07:53');
INSERT INTO `doctor_advisory_price` VALUES (211, 110714035, 0, '2020-01-16 13:11:01', '2020-01-16 13:11:01');
INSERT INTO `doctor_advisory_price` VALUES (212, 108020665, 100, '2020-01-16 13:44:15', '2020-01-16 13:44:15');
INSERT INTO `doctor_advisory_price` VALUES (213, 111024093, 0, '2020-01-16 14:52:17', '2020-01-16 14:52:17');
INSERT INTO `doctor_advisory_price` VALUES (214, 107910626, 100, '2020-01-16 15:06:29', '2020-01-16 15:06:29');
INSERT INTO `doctor_advisory_price` VALUES (215, 111124128, 100, '2020-01-17 14:11:07', '2020-01-17 14:11:07');
INSERT INTO `doctor_advisory_price` VALUES (216, 152, 3000, '2020-01-19 13:13:59', '2020-01-19 13:13:59');
INSERT INTO `doctor_advisory_price` VALUES (217, 111314173, 0, '2020-01-19 14:37:10', '2020-01-19 14:53:05');
INSERT INTO `doctor_advisory_price` VALUES (218, 111614201, 100, '2020-01-22 11:35:46', '2020-02-07 21:58:27');
INSERT INTO `doctor_advisory_price` VALUES (219, 111624203, 1100, '2020-01-22 16:38:17', '2020-01-22 16:38:17');
INSERT INTO `doctor_advisory_price` VALUES (220, 81025906, 100, '2020-02-21 12:08:22', '2020-02-21 12:08:22');
INSERT INTO `doctor_advisory_price` VALUES (221, 71624605, 0, '2020-02-26 11:52:38', '2020-02-26 11:52:38');
INSERT INTO `doctor_advisory_price` VALUES (222, 89324699, 100, '2020-02-26 17:37:35', '2020-02-26 17:37:35');
INSERT INTO `doctor_advisory_price` VALUES (223, 115624921, 100, '2020-03-02 14:25:59', '2020-03-02 14:25:59');
INSERT INTO `doctor_advisory_price` VALUES (224, 114414606, 0, '2020-03-03 10:03:44', '2020-03-03 10:03:44');
INSERT INTO `doctor_advisory_price` VALUES (225, 113124327, 0, '2020-03-03 16:25:50', '2020-03-03 16:25:50');
INSERT INTO `doctor_advisory_price` VALUES (226, 115724985, 100, '2020-03-03 16:42:48', '2020-03-06 10:24:59');
INSERT INTO `doctor_advisory_price` VALUES (227, 115724991, 100, '2020-03-04 10:15:37', '2020-03-04 10:15:37');
INSERT INTO `doctor_advisory_price` VALUES (228, 115314827, 100, '2020-03-04 10:53:55', '2020-03-04 10:53:55');
INSERT INTO `doctor_advisory_price` VALUES (229, 112934285, 1000, '2020-03-04 11:40:12', '2020-03-04 11:40:12');
INSERT INTO `doctor_advisory_price` VALUES (230, 34511540, 0, '2020-03-04 15:47:14', '2020-03-04 15:47:14');
INSERT INTO `doctor_advisory_price` VALUES (231, 71224592, 500, '2020-03-04 17:08:24', '2020-03-04 17:08:24');
INSERT INTO `doctor_advisory_price` VALUES (232, 115915056, 0, '2020-03-05 16:00:20', '2020-03-05 16:00:20');
INSERT INTO `doctor_advisory_price` VALUES (233, 116025091, 100, '2020-03-06 12:02:49', '2020-03-06 12:02:49');
INSERT INTO `doctor_advisory_price` VALUES (234, 116325160, 100, '2020-03-09 15:39:03', '2020-03-09 15:39:03');
INSERT INTO `doctor_advisory_price` VALUES (235, 116515208, 100, '2020-03-11 10:46:18', '2020-03-11 10:46:18');
INSERT INTO `doctor_advisory_price` VALUES (236, 116325153, 100, '2020-03-11 15:38:37', '2020-03-11 15:42:18');
INSERT INTO `doctor_advisory_price` VALUES (237, 116525214, 100, '2020-03-11 15:52:05', '2020-03-11 15:52:05');
INSERT INTO `doctor_advisory_price` VALUES (238, 116525213, 100, '2020-03-12 15:25:22', '2020-03-12 17:40:55');
INSERT INTO `doctor_advisory_price` VALUES (239, 117025253, 100, '2020-03-16 15:00:58', '2020-03-16 15:16:49');
INSERT INTO `doctor_advisory_price` VALUES (240, 117125279, 30000, '2020-03-17 15:57:50', '2020-03-17 15:57:50');
INSERT INTO `doctor_advisory_price` VALUES (241, 117225298, 0, '2020-03-18 13:26:06', '2020-03-18 13:26:06');
INSERT INTO `doctor_advisory_price` VALUES (242, 116515210, 100, '2020-03-19 10:48:12', '2020-03-19 11:04:46');
INSERT INTO `doctor_advisory_price` VALUES (243, 117225303, 100, '2020-03-19 11:12:20', '2020-03-19 11:12:20');
INSERT INTO `doctor_advisory_price` VALUES (244, 117425329, 0, '2020-03-24 14:34:32', '2020-03-24 14:34:32');
INSERT INTO `doctor_advisory_price` VALUES (245, 117926567, 0, '2020-03-25 14:23:31', '2020-03-25 14:23:31');
INSERT INTO `doctor_advisory_price` VALUES (246, 115915052, 0, '2020-03-26 11:04:30', '2020-03-26 11:04:30');
INSERT INTO `doctor_advisory_price` VALUES (247, 49332773, 0, '2020-03-30 13:07:47', '2020-03-30 13:07:47');
INSERT INTO `doctor_advisory_price` VALUES (248, 107320505, 0, '2020-03-31 11:45:01', '2020-03-31 11:45:01');
INSERT INTO `doctor_advisory_price` VALUES (249, 118522884, 0, '2020-03-31 13:59:30', '2020-03-31 15:42:02');

-- ----------------------------
-- Table structure for order_state_machine_context
-- ----------------------------
DROP TABLE IF EXISTS `order_state_machine_context`;
CREATE TABLE `order_state_machine_context`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `biz_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `current_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `context_str` varbinary(1000) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_state_machine_context
-- ----------------------------
INSERT INTO `order_state_machine_context` VALUES (6, '2', '初始', 0x4151414241474E766253357A61336C696248566C4C6E4E305958526C6257466A61476C755A53356A6232357A645778304C6D56756457317A4C6B39795A475679553352686448587A4151454141514676636D6375633342796157356E5A6E4A686257563362334A724C6E4E305958526C6257466A61476C755A53357A6458427762334A304C6B396963325679646D46696247564E5966414241414543616D46325953353164476C734C6B4679636D463554476C7A3941454141514E7159585A684C6E5630615777755347467A614531683841454141774676636D526C636C4E30595852316330316859326870626D564A35413D3D, '2020-03-29 23:02:14', '2020-03-29 23:02:14');

-- ----------------------------
-- Table structure for patient_advisory_info
-- ----------------------------
DROP TABLE IF EXISTS `patient_advisory_info`;
CREATE TABLE `patient_advisory_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gmt_create` timestamp(0) NULL DEFAULT NULL,
  `gmt_modify` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `doctor_id` bigint(20) NULL DEFAULT NULL COMMENT '医生',
  `patient_id` bigint(20) NULL DEFAULT NULL COMMENT '病人',
  `start_time` timestamp(0) NULL DEFAULT NULL COMMENT '咨询开始时间',
  `expire_time` bigint(20) NULL DEFAULT NULL COMMENT '预计过期时间',
  `end_time` timestamp(0) NULL DEFAULT NULL COMMENT '咨询结束时间',
  `patient_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sex` int(2) NULL DEFAULT NULL COMMENT '性别',
  `height` int(4) NULL DEFAULT NULL COMMENT '身高',
  `weight` double(10, 2) NULL DEFAULT NULL COMMENT '体重',
  `age` int(5) NULL DEFAULT NULL COMMENT '年龄',
  `county` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '区/县',
  `describes` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '病情描述',
  `img_url` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片uri列表, 分号分隔',
  `status` int(2) NULL DEFAULT 1 COMMENT '状态 1初始 2已支付开始 3已结束 4退款 5取消',
  `refund` int(2) NULL DEFAULT 0 COMMENT '是否退款',
  `order_num` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `memo` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `pay_time` timestamp(0) NULL DEFAULT NULL COMMENT '支付成功实践',
  `past_history` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '既往史描述',
  `present_illness` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '现病史描述',
  `allergic_history` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '过敏史过描述',
  `card` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '',
  `district_code` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '区域Code',
  `country` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '国家',
  `province` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '省',
  `city` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '市',
  `diabetes_type` int(11) NULL DEFAULT 0 COMMENT '糖尿病类型',
  `disease_category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '疾病类型',
  `waiting` decimal(6, 2) NULL DEFAULT NULL COMMENT '等待的医生回复时长',
  `repeat_status` int(2) NULL DEFAULT 0 COMMENT '医生回复状态 1 已回复 0未回复',
  `before_info_id` int(11) NULL DEFAULT NULL COMMENT '来源于更换医生，更换前的info id',
  `source` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'IN_APP' COMMENT '咨询单来源 药店咨询STORE     掌糖付费咨询IN_APP',
  `advisory_type` int(3) NULL DEFAULT 1 COMMENT '咨询类型1 正常付费咨询 2 图文 4视频',
  `im_group_id` int(11) NULL DEFAULT NULL COMMENT '咨询单关联的im chatgroup',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ordernum`(`order_num`) USING BTREE,
  INDEX `patientId`(`patient_id`) USING BTREE,
  INDEX `idx_doctorid_advisorytype_status_source`(`doctor_id`, `advisory_type`, `status`, `source`) USING BTREE,
  INDEX `idx_doctorid_imgroupid_status_id`(`doctor_id`, `im_group_id`, `status`, `id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户咨询会话信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of patient_advisory_info
-- ----------------------------

-- ----------------------------
-- Table structure for patient_advisory_order
-- ----------------------------
DROP TABLE IF EXISTS `patient_advisory_order`;
CREATE TABLE `patient_advisory_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gmt_create` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `gmt_modify` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `doctor_id` bigint(20) NULL DEFAULT NULL,
  `patient_id` bigint(20) NULL DEFAULT NULL,
  `biz_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务编号',
  `order_num` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付订单号',
  `status` int(2) NULL DEFAULT 0 COMMENT '订单状态 0 初始，1待支付，2 已支付成功 3支付失败 4退款',
  `amount` int(10) NULL DEFAULT 0 COMMENT '咨询价格（分）',
  `cost_coin` int(10) NULL DEFAULT 0 COMMENT '本次订单支付的云币',
  `charge_coin` int(10) NULL DEFAULT 0 COMMENT '本次发起充值的云币',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单类型：退款记录BACK  支付记录PAY',
  `pay_string` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '充值发起的支付串',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ordernum`(`order_num`) USING BTREE,
  INDEX `patient`(`patient_id`) USING BTREE,
  INDEX `IDX_DOCTOR`(`doctor_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户咨询订单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of patient_advisory_order
-- ----------------------------

-- ----------------------------
-- Table structure for pk_sequence
-- ----------------------------
DROP TABLE IF EXISTS `pk_sequence`;
CREATE TABLE `pk_sequence`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `k` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `v` bigint(20) NOT NULL,
  `step` int(10) UNSIGNED NOT NULL,
  `modify_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `IDX_k_UNIQUE`(`k`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pk_sequence
-- ----------------------------
INSERT INTO `pk_sequence` VALUES (1, 'consult_id', 71, 1, '2020-04-03 17:53:04');

-- ----------------------------
-- Table structure for prescription_state_machine_context
-- ----------------------------
DROP TABLE IF EXISTS `prescription_state_machine_context`;
CREATE TABLE `prescription_state_machine_context`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `biz_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `current_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `context_str` varbinary(1000) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of prescription_state_machine_context
-- ----------------------------
INSERT INTO `prescription_state_machine_context` VALUES (3, '3', '待咨询', 0x4151414241474E766253357A61336C696248566C4C6E4E305958526C6257466A61476C755A53356A6232357A645778304C6D56756457317A4C6B4E76626E4E316248526864476C76626C4E3059585231387745434141454262334A6E4C6E4E77636D6C755A325A795957316C643239796179357A644746305A57316859326870626D5575633356776347397964433550596E4E6C636E5A68596D786C5457487741514142416D7068646D45756458527062433542636E4A6865557870632F514241414544616D46325953353164476C734C6B68686332684E5966414241414D4259323975633356736447463061573975553352686448567A5457466A61476C755A556E6B, NULL, '2020-03-29 18:26:58');
INSERT INTO `prescription_state_machine_context` VALUES (4, '1', '待咨询', 0x4151414241474E766253357A61336C696248566C4C6E4E305958526C6257466A61476C755A53356A6232357A645778304C6D56756457317A4C6B4E76626E4E316248526864476C76626C4E3059585231387745434141454262334A6E4C6E4E77636D6C755A325A795957316C643239796179357A644746305A57316859326870626D5575633356776347397964433550596E4E6C636E5A68596D786C5457487741514142416D7068646D45756458527062433542636E4A6865557870632F514241414544616D46325953353164476C734C6B68686332684E5966414241414D4259323975633356736447463061573975553352686448567A5457466A61476C755A556E6B, '2020-03-29 19:04:39', '2020-03-29 19:04:39');
INSERT INTO `prescription_state_machine_context` VALUES (5, '2', '待咨询', 0x4151414241474E766253357A61336C696248566C4C6E4E305958526C6257466A61476C755A53356A6232357A645778304C6D56756457317A4C6B4E76626E4E316248526864476C76626C4E3059585231387745434141454262334A6E4C6E4E77636D6C755A325A795957316C643239796179357A644746305A57316859326870626D5575633356776347397964433550596E4E6C636E5A68596D786C5457487741514142416D7068646D45756458527062433542636E4A6865557870632F514241414544616D46325953353164476C734C6B68686332684E5966414241414D4259323975633356736447463061573975553352686448567A5457466A61476C755A556E6B, '2020-03-29 19:08:05', '2020-03-29 19:08:05');

-- ----------------------------
-- Table structure for t_consult
-- ----------------------------
DROP TABLE IF EXISTS `t_consult`;
CREATE TABLE `t_consult`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态 1初始 2已支付开始 3已结束 4退款 5取消',
  `consult_id` char(17) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `doctor_id` bigint(20) NULL DEFAULT NULL COMMENT '医生',
  `patient_id` bigint(20) NULL DEFAULT NULL COMMENT '病人',
  `start_time` timestamp(0) NULL DEFAULT NULL COMMENT '咨询开始时间',
  `end_time` timestamp(0) NULL DEFAULT NULL COMMENT '咨询结束时间',
  `waiting` decimal(6, 2) NULL DEFAULT NULL COMMENT '等待的医生回复时长',
  `repeat_status` int(2) NULL DEFAULT 0 COMMENT '医生回复状态 1 已回复 0未回复',
  `gmt_create` timestamp(0) NULL DEFAULT NULL,
  `gmt_modify` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `advisory_type` int(3) NULL DEFAULT 1 COMMENT '咨询类型1 正常付费咨询 2 图文 4视频',
  `im_group_id` int(11) NULL DEFAULT NULL COMMENT '咨询单关联的im chatgroup',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `patientId`(`patient_id`) USING BTREE,
  INDEX `idx_doctorid_advisorytype_status_source`(`doctor_id`, `advisory_type`, `status`) USING BTREE,
  INDEX `idx_doctorid_imgroupid_status_id`(`doctor_id`, `im_group_id`, `status`, `id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 673066 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户咨询会话信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_consult
-- ----------------------------
INSERT INTO `t_consult` VALUES (673064, '已完成', 'WZ202004020000055', 1, 2, NULL, NULL, NULL, 0, '2020-04-02 00:49:02', '2020-04-02 00:49:03', 1, NULL);
INSERT INTO `t_consult` VALUES (673065, '已终止', 'WZ202004020000056', 1, 2, NULL, NULL, NULL, 0, '2020-04-02 00:51:43', '2020-04-02 00:51:44', 1, NULL);
INSERT INTO `t_consult` VALUES (673070, '待咨询', 'WZ202004030000062', 1, 2, NULL, NULL, NULL, 0, '2020-04-03 11:46:22', '2020-04-03 11:47:29', 1, NULL);
INSERT INTO `t_consult` VALUES (673071, '待咨询', 'WZ202004030000063', 1, 2, NULL, NULL, NULL, 0, '2020-04-03 11:48:31', '2020-04-03 11:48:31', 1, NULL);
INSERT INTO `t_consult` VALUES (673072, '待咨询', 'WZ202004030000064', 1, 2, NULL, NULL, NULL, 0, '2020-04-03 11:48:58', '2020-04-03 11:48:58', 1, NULL);
INSERT INTO `t_consult` VALUES (673073, '待咨询', 'WZ202004030000065', 1, 2, NULL, NULL, NULL, 0, '2020-04-03 11:49:26', '2020-04-03 11:49:26', 1, NULL);
INSERT INTO `t_consult` VALUES (673074, '待咨询', 'WZ202004030000066', 1, 2, NULL, NULL, NULL, 0, '2020-04-03 11:52:49', '2020-04-03 11:52:50', 1, NULL);
INSERT INTO `t_consult` VALUES (673077, '已完成', 'WZ202004030000069', 1, 2, NULL, NULL, NULL, 0, '2020-04-03 17:51:30', '2020-04-03 17:51:30', 1, NULL);
INSERT INTO `t_consult` VALUES (673078, '已终止', 'WZ202004030000070', 1, 2, NULL, NULL, NULL, 0, '2020-04-03 17:52:35', '2020-04-03 17:52:35', 1, NULL);
INSERT INTO `t_consult` VALUES (673079, '已取消', 'WZ202004030000071', 1, 2, NULL, NULL, NULL, 0, '2020-04-03 17:53:05', '2020-04-03 17:53:05', 1, NULL);

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态 1初始 2已支付开始 3已结束 4退款 5取消',
  `order_id` char(17) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `consult_id` char(17) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `start_time` timestamp(0) NULL DEFAULT NULL COMMENT '咨询开始时间',
  `end_time` timestamp(0) NULL DEFAULT NULL COMMENT '咨询结束时间',
  `amount` int(10) NULL DEFAULT 0 COMMENT '咨询价格（分）',
  `pay_string` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '充值发起的支付串',
  `coin_pay` int(10) NULL DEFAULT 0 COMMENT '本次支付云币结果 1 成功 0 失败 2余额不足',
  `gmt_create` timestamp(0) NULL DEFAULT NULL,
  `gmt_modify` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_doctorid_advisorytype_status_source`(`status`) USING BTREE,
  INDEX `idx_doctorid_imgroupid_status_id`(`status`, `id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 673045 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户咨询会话信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES (673043, '已支付', 'WZ202004020000055', 'WZ202004020000055', NULL, NULL, 10, NULL, 0, '2020-04-02 00:49:02', '2020-04-02 00:49:03');
INSERT INTO `t_order` VALUES (673044, '已退款', 'WZ202004020000056', 'WZ202004020000056', NULL, NULL, 10, NULL, 0, '2020-04-02 00:51:44', '2020-04-02 00:51:44');
INSERT INTO `t_order` VALUES (673045, '待支付', 'WZ202004030000062', 'WZ202004030000062', NULL, NULL, 10, NULL, 0, '2020-04-03 11:46:50', '2020-04-03 11:46:56');
INSERT INTO `t_order` VALUES (673046, '待支付', 'WZ202004030000063', 'WZ202004030000063', NULL, NULL, 10, NULL, 0, '2020-04-03 11:48:31', '2020-04-03 11:48:31');
INSERT INTO `t_order` VALUES (673047, '待支付', 'WZ202004030000064', 'WZ202004030000064', NULL, NULL, 10, NULL, 0, '2020-04-03 11:48:58', '2020-04-03 11:48:58');
INSERT INTO `t_order` VALUES (673048, '待支付', 'WZ202004030000065', 'WZ202004030000065', NULL, NULL, 10, NULL, 0, '2020-04-03 11:49:26', '2020-04-03 11:49:26');
INSERT INTO `t_order` VALUES (673049, '待支付', 'WZ202004030000066', 'WZ202004030000066', NULL, NULL, 10, NULL, 0, '2020-04-03 11:52:49', '2020-04-03 11:52:50');
INSERT INTO `t_order` VALUES (673052, '已支付', 'WZ202004030000069', 'WZ202004030000069', NULL, NULL, 10, NULL, 0, '2020-04-03 17:51:31', '2020-04-03 17:51:31');
INSERT INTO `t_order` VALUES (673053, '已退款', 'WZ202004030000070', 'WZ202004030000070', NULL, NULL, 10, NULL, 0, '2020-04-03 17:52:35', '2020-04-03 17:52:35');
INSERT INTO `t_order` VALUES (673054, '已取消', 'WZ202004030000071', 'WZ202004030000071', NULL, NULL, 10, NULL, 0, '2020-04-03 17:53:05', '2020-04-03 17:53:05');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1243074037028712450 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'lemon', '1234', NULL, NULL);
INSERT INTO `user` VALUES (7, 'lemon', '123', NULL, NULL);
INSERT INTO `user` VALUES (8, 'lemon1', '123', NULL, NULL);
INSERT INTO `user` VALUES (9, 'lemon2', '123', '2020-03-24 16:16:09', '2020-03-24 16:16:09');
INSERT INTO `user` VALUES (10, 'lemon3', '123', '2020-03-24 16:16:15', '2020-03-24 16:16:15');
INSERT INTO `user` VALUES (11, 'lemon4', '123', '2020-03-24 16:16:24', '2020-03-24 16:16:24');
INSERT INTO `user` VALUES (12, 'lemon5', '123', '2020-03-24 16:16:29', '2020-03-24 16:16:29');
INSERT INTO `user` VALUES (14, '111', '111', '2020-03-24 16:51:43', '2020-03-24 16:51:43');
INSERT INTO `user` VALUES (1243073000150650881, 'lemon', '123', '2020-03-26 15:11:13', '2020-03-26 15:11:13');
INSERT INTO `user` VALUES (1243074037028712449, 'lemon', '123', '2020-03-26 15:15:20', '2020-03-26 15:15:21');

SET FOREIGN_KEY_CHECKS = 1;
