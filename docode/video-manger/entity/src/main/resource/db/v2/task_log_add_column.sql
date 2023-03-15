ALTER TABLE TASK_LOG ADD COLUMN `active_days` int DEFAULT -1;
ALTER TABLE TASK_LOG ADD COLUMN `spin_mult` double(5,2) DEFAULT 0;
ALTER TABLE TASK_LOG ADD COLUMN `link_id` bigint COMMENT '关联的task_log_id';
ALTER TABLE TASK_LOG ADD COLUMN `invit_rewards` varchar(255) COMMENT '开始任务时的奖励配置列表';
ALTER TABLE TASK_LOG ADD COLUMN `amount_exch_ratio` bigint COMMENT '开始任务时的金币与现金的兑换率';
ALTER TABLE TASK_LOG ADD COLUMN `invited_user_id` bigint COMMENT '被邀请人的id';

ALTER TABLE TASK_LOG ADD COLUMN `modify_time` datetime default now();