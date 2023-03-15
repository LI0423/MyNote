CREATE TABLE `app_version_config` (
                                      `id` bigint(20) NOT NULL,
                                      `app_id` bigint(20) DEFAULT NULL COMMENT 'app Id',
                                      `version_code` varchar(200) DEFAULT NULL COMMENT '版本',
                                      `create_anonymous_user` tinyint(4) DEFAULT '0' COMMENT '创建匿名用户标签0否 1是',
                                      `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;