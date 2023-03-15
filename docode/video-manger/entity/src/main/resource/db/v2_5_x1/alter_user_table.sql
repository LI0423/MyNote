ALTER TABLE `user` ADD `phone_number` varchar(15) COMMENT '用户移动电话号码';

-- ALTER TABLE `user` ADD UNIQUE KEY `APP_ID_PHONE_NUMBER_INDEX` (`app_id`, `phone_number`) COMMENT '用于限制一个号码在某个app下只能绑定一个用户';
ALTER TABLE `user` ADD KEY `APP_ID_PHONE_NUMBER_INDEX` (`app_id`, `phone_number`);