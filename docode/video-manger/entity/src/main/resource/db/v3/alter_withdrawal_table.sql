ALTER TABLE `withdrawal` ADD COLUMN `gold_bean` bigint DEFAULT 0 COMMENT '金豆数量';
ALTER TABLE `withdrawal` ADD COLUMN `gold_bean_amount` bigint DEFAULT 0 COMMENT '金豆提现现金数';
ALTER TABLE `withdrawal` ADD KEY `USER_ID_TYPE_ONE_TIME_UNIQUE_ID_STATUS_INDEX` (`user_id`, `type`, `one_time_unique_id`, `status`);
ALTER TABLE `withdrawal` ADD KEY `USER_ID_TYPE_ONE_TIME_UNIQUE_ID_STATUS_CREATE_TIME_INDEX` (`user_id`, `type`, `one_time_unique_id`, `status`, `create_time`);

alter table withdrawal add column anid varchar(255);
alter table withdrawal add column oaid varchar(255);