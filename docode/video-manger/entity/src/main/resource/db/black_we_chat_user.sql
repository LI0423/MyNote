CREATE TABLE `black_we_chat_user` (
    `id` bigint COMMENT '主键',
    `open_id` varchar(127) NOT NULL COMMENT '微信open id',
    `pkg` varchar(127) DEFAULT NULL COMMENT 'app包名',
    `desc` varchar(1023) NOT NULL COMMENT '封禁原因',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `last_modify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',

    PRIMARY KEY (`id`),
    KEY `PKG_OPEN_ID_INDEX` (`pkg`, `open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;