CREATE TABLE `user_ad_diverse_config` (
  `id` bigint(20) NOT NULL,
  `app` varchar(255) DEFAULT NULL,
  `aid` varchar(255) DEFAULT NULL,
  `interval_time` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(100) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  `last_modify_by` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_uni` (`app`,`aid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

alter table `user_ad_risk_config` add column `aid` varchar(100) DEFAULT NULL;
alter table `user_ad_risk_config` add column `config_type` int(11) DEFAULT NULL;
alter table `user_ad_risk_config` add column `fill_value` int(11) DEFAULT NULL;