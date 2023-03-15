CREATE TABLE `app` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT 'app名字',
  `pkg` varchar(127) DEFAULT NULL COMMENT 'app包名',
  `we_chat_mchid` varchar(63) DEFAULT NULL COMMENT 'app在微信上注册后生成的商户id',
  `we_chat_app_id` varchar(63) DEFAULT NULL COMMENT '微信认证appid',
  `we_chat_app_secret` varchar(63) DEFAULT NULL COMMENT '微信登录认证app密钥',
  `we_chat_api_key` varchar (63) DEFAULT NULL COMMENT '微信支付api密钥',
  `we_chat_api_cert` varchar (4094) DEFAULT NULL COMMENT '微信支付api证书',
  `gold_coin_ratio` bigint(20) DEFAULT NULL COMMENT '金币数与现金的比值(金币/现金)',
  `has_wechat_withdrawal` bit(1) DEFAULT b'0' COMMENT 'app能不能提现',
  `has_content_level` bit(1) DEFAULT b'0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(127) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_modify_by` varchar(127) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `PKG_INDEX` (`pkg`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

ALTER TABLE `app` ADD COLUMN `is_debug_model` bit(1) DEFAULT b'0' COMMENT '是否开启debug模式';