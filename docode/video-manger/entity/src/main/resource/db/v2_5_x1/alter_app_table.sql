ALTER TABLE app ADD `sms_region_id` varchar(63) COMMENT '短信服务区域id';
ALTER TABLE app ADD `sms_access_key_id` varchar(63) COMMENT '短信服务sdk身份验证用key';
ALTER TABLE app ADD `sms_access_secret` varchar(63) COMMENT '短信服务sdk身份验证用key对应密码';
ALTER TABLE app ADD `sms_sign_name` varchar(63) COMMENT '短信服务短信上的app签名';
ALTER TABLE app ADD `sms_template_code` varchar(63) COMMENT '短信服务短信模板代码';