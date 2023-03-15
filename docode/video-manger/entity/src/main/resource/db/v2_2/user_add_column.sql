-- ALTER TABLE user ADD COLUMN `max_continuous_sign_in_days` bigint DEFAULT 0 COMMENT '用户最大连续签到数';
ALTER TABLE user ADD COLUMN `current_medal_continuous_sign_in_days` int DEFAULT 0 COMMENT '用户当前连续签到数';
-- ALTER TABLE user ADD COLUMN `max_sign_in_day_end_time` date COMMENT '用户最大签到天数的开始时间';