CREATE TABLE app_payment_config (
    `app_id` bigint COMMENT '应用id',
    `ali_pay_app_id` varchar(64) COMMENT '支付宝appid',
    `ali_pay_merchant_private_key` varchar(4094) COMMENT '支付宝应用私钥',
    `ali_pay_public_key` varchar(4094) COMMENT '支付宝公钥',
    `ali_pay_app_cert` varchar(4094) COMMENT '应用公钥证书',
    `ali_pay_public_cert` varchar(4094) COMMENT '支付宝公钥证书',
    `ali_pay_root_cert` varchar(5120) COMMENT '支付宝根证书',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by` varchar(127) DEFAULT NULL,
    `last_modify_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `last_modify_by` varchar(127) DEFAULT NULL,
    PRIMARY KEY (`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '应用支付配置';

alter table app_payment_config add column `deleted` int(3) DEFAULT null COMMENT '逻辑删除字段；0:已删除，1:未删除';
alter table app_payment_config add column `frozen_status` int(3) DEFAULT null COMMENT '冻结状态；0:未冻结，1:冻结';
alter table app_payment_config add column `merchant_name` varchar(30) DEFAULT null COMMENT '主体名称，商户名称';
alter table app_payment_config add column `pkg` varchar(255) DEFAULT null COMMENT 'pkg';

alter table app_payment_config MODIFY `ali_pay_merchant_private_key` text COMMENT '支付宝应用私钥';
alter table app_payment_config MODIFY `ali_pay_public_key` text COMMENT '支付宝公钥';
alter table app_payment_config MODIFY `ali_pay_app_cert` text COMMENT '应用公钥证书';
alter table app_payment_config MODIFY `ali_pay_public_cert` text COMMENT '支付宝公钥证书';
alter table app_payment_config MODIFY `ali_pay_root_cert` text COMMENT '支付宝根证书';
