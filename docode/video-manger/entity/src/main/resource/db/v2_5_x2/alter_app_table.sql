ALTER TABLE app ADD `we_chat_mch_private_key` text COMMENT '微信支付商户私钥, 商户证书中有对应的公钥';
ALTER TABLE app ADD `we_chat_api_v3_key` varchar(127) COMMENT '微信支付apiv3 key';
ALTER TABLE app ADD `we_chat_mch_cert_serial_no` varchar(127) COMMENT '微信支付商户证书序列号';

ALTER TABLE app ADD `need_anonymous_user` bit(1) DEFAULT b'0' COMMENT 'app下是否需要匿名用户';