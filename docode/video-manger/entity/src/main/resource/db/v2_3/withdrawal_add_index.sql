-- 提现时要查询一段时间的总number
ALTER TABLE withdrawal ADD KEY `APP_ID_EC_ED_CREATE_TIME_INDEX` (`err_code`, `create_time`, `app_id`, `err_desc`);