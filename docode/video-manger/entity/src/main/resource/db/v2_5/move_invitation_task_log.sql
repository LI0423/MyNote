INSERT INTO `invitation_task_log` (`id`, `user_id`, `task_code`, `create_time`, `number`, `amount`, `task_type`,
`app_id`, `invit_rewards`, `amount_exch_ratio`, `invited_user_id`, `modify_time`, `app_version`,
`invit_reward_gived_count`, `invit_task_completed`)
SELECT `id`, `user_id`, `task_code`, `create_time`, `number`, `amount`, `task_type`,
`app_id`, `invit_rewards`, `amount_exch_ratio`, `invited_user_id`, `modify_time`, `app_version`,
`invit_reward_gived_count`, `invit_task_completed`
FROM `task_log`
WHERE task_code = 'D00006';
