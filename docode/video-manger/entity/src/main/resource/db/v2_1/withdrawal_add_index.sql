-- 提现时要查询一段时间的总number
ALTER TABLE gold_coin ADD KEY `USER_ID_CREATE_TIME_NUMBER_INDEX` (`user_id`, `create_time`, `number`);