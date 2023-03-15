CREATE TABLE `app_payment_config_mapping` (
                                              `id` bigint(20) NOT NULL,
                                              `app_id` bigint(20) DEFAULT NULL COMMENT 'app的id',
                                              `merchant_type` int(2) DEFAULT NULL COMMENT '商户类型；0:微信，1:支付宝',
                                              `merchant_id` bigint(20) DEFAULT NULL COMMENT '商户id，对应微信和支付宝商户信息表中的id',
                                              `mapping_type` int(2) DEFAULT NULL COMMENT '0:支付；1:提现',
                                              `status` int(2) DEFAULT NULL COMMENT '0:上线；1:下线',
                                              `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                              `create_by` varchar(127) DEFAULT NULL COMMENT '创建者',
                                              `last_modify_time` datetime DEFAULT NULL COMMENT '最近修改时间',
                                              `last_modify_by` varchar(127) DEFAULT NULL COMMENT '最近修改者',
                                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='app和支付配置信息的映射关系表';