ALTER TABLE task_log ADD `doubled` bit COMMENT '是否已经双倍了';
ALTER TABLE task_log ADD `app_version` varchar(63) COMMENT '完成任务时的版本';

ALTER TABLE task_log ADD `invit_reward_gived_count` int(63) DEFAULT 0 COMMENT '邀请好友任务已经给予奖励的次数';
ALTER TABLE task_log ADD `invit_task_completed` bit DEFAULT b'0' COMMENT '邀请好友任务奖励是否全都发放完成了';