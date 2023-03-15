ALTER TABLE TASK_LOG ADD KEY `USER_ID_TASK_CODE_ACTIVE_DAYS_INDEX` (`user_id`, `task_code`, `active_days`);
ALTER TABLE TASK_LOG ADD KEY `USER_ID_TASK_CODE_CREATE_TIME_NUMBER` (`user_id`, `task_code`, `create_time`, `number`);

ALTER TABLE TASK_LOG ADD KEY `USER_ID_INVIT_UID_CREATE_TIME_INDEX` (`user_id`, `invited_user_id`, `create_time`);
ALTER TABLE TASK_LOG ADD KEY `USER_ID_INVIT_UID_TCODE_MTIME_INDEX` (`user_id`, `invited_user_id`, `task_code`, `modify_time`);