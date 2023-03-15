CREATE TABLE `user_ibu_info` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                 `pkg` varchar(100) DEFAULT '' COMMENT '必填 包名',
                                 `tk` varchar(100) DEFAULT '' COMMENT '必填 tk',
                                 `ibu_tag` tinyint COMMENT 'ibu标识 1 是 0 否',
                                 `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `last_modify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `PKG_TK_INDEX` (`pkg`, `tk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='ibu 信息';