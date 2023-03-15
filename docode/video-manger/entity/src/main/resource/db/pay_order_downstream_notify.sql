CREATE TABLE `pay_order_downstream_notify` (
    `id` bigint COMMENT '主键',
    `pay_order_id` bigint not null COMMENT '支付订单id',
    `notify_url` varchar(255) not null COMMENT '通知url',
    `create_time` datetime not null DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modify_time` datetime not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `PAY_ORDER_ID_INDEX` (`pay_order_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '支付订单下游通知信息表';