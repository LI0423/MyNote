create table `google_pay_order` (
                                    `id` bigint not null,
                                    `user_id` bigint not null,
                                    `order_id` varchar(255) not null comment '我们平台生成的订单id',
                                    `third_order_id` varchar(255) not null comment '第三方平台的订单（谷歌的订单id）',
                                    `status` int(3) not null comment '订单支付状态',
                                    `amount` bigint(20) comment '交易金额',
                                    `currency` varchar(127) comment '汇率',
                                    `country` varchar(127) comment '国家码',
                                    `channel` int(3) comment '支付渠道',
                                    `product_id` varchar(127) '商品id',
                                    `extra` varchar(1000) '订单详情',
                                    `pkg` varchar(127) '包名'
                                    `create_time` datetime not null DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `modify_time` datetime not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    primary key (`id`),
                                    unique `GOOGLE_USER_ID_TASK_CODE_INDEX` (`google_user_id`),
                                    key `ACCESS_TOKEN_INDEX` (`access_token`)
) ENGINE=InnoDB CHARSET=utf8;