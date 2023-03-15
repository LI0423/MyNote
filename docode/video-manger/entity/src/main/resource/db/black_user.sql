CREATE TABLE `black_user` (
    `token` varchar(127) NOT NULL,
    `desc` varchar(1023) NOT NULL COMMENT '封禁原因',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '黑名单用户';