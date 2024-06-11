SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hotel
-- ----------------------------
DROP TABLE IF EXISTS `hotel`;
CREATE TABLE `hotel`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `city` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '城市',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '地址',
  `price` decimal(10, 2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '价格',
  `score` float(5, 2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '分数',
  `longitude` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '经度',
  `latitude` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '纬度',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最近更新时间',
  `data_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0:正常; -100:删除;',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'hotel' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hotel
-- ----------------------------
INSERT INTO `hotel` VALUES (1, '宣城市经开区希尔顿酒店', '宣城市', '安徽省宣城市宣州区经开区', 488.00, 4.70, '118.729148', '30.937385', '2024-06-10 22:02:12', '2024-06-10 22:22:48', 0);
INSERT INTO `hotel` VALUES (2, '7天连锁酒店', '上海市', '上海市静安交通路40号', 248.00, 4.10, '121.474888', '31.251560', '2024-06-10 21:22:34', '2024-06-10 22:22:46', 0);
INSERT INTO `hotel` VALUES (3, '速8酒店', '上海市', '上海市广灵二路126号', 300.00, 4.80, '121.478377', '31.282779', '2024-06-10 21:23:32', '2024-06-10 22:22:44', 0);
INSERT INTO `hotel` VALUES (4, '上海西藏大厦万怡酒店', '上海市', '上海虹桥路100号', 200.00, 4.30, '121.434468', '31.192353', '2024-06-10 21:24:08', '2024-06-10 22:22:43', 0);
INSERT INTO `hotel` VALUES (5, '上海浦西万怡酒店', '上海市', '上海市恒丰路338号', 180.00, 4.90, '121.455255', '31.242969', '2024-06-10 21:25:11', '2024-06-10 22:22:42', 0);
INSERT INTO `hotel` VALUES (6, '上海浦东华美达大酒店', '上海市', '上海市新金桥路18号', 251.00, 4.40, '121.590865', '31.244723', '2024-06-10 21:25:54', '2024-06-10 22:22:40', 0);

SET FOREIGN_KEY_CHECKS = 1;
