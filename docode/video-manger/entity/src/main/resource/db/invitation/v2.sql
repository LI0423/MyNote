CREATE TABLE `u_invitation_event` (
                                      `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                      `app_id` bigint(20) NOT NULL COMMENT 'appid',
                                      `invitation_id` bigint(20) NOT NULL COMMENT '邀请记录id',
                                      `invitation_event_key` varchar(30) DEFAULT NULL COMMENT '事件',
                                      `receive_price_status` int(11) DEFAULT '0' COMMENT '领取状态, 0:未达成, 1:待领取， 2。已领取',
                                      `to_receive_price` bigint(20) DEFAULT NULL COMMENT '待领取金额, 实时计算',
                                      `already_receive_price` bigint(20) DEFAULT NULL COMMENT '已领取金额',
                                      `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      PRIMARY KEY (`id`),
                                      KEY `idx_invitation_id` (`invitation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='邀请好友事件表';

CREATE TABLE `u_invitation_user` (
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                     `app_id` bigint(20) NOT NULL COMMENT 'appid',
                                     `user_id` bigint(20) NOT NULL COMMENT '用户id',
                                     `invitation_user_id` bigint(20) DEFAULT NULL COMMENT '被邀请者用户id',
                                     `invitation_price` bigint(20) DEFAULT '0' COMMENT '邀请累积获得的金豆',
                                     `invitation_status` bigint(20) NOT NULL DEFAULT '0' COMMENT '邀请事件状态',
                                     `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     PRIMARY KEY (`id`),
                                     KEY `idx_user_id` (`user_id`),
                                     KEY `idx_invitation_user_id` (`invitation_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='邀请好友记录表';



ALTER TABLE `video_task`.`u_invitation_user`
    ADD COLUMN `open_id` varchar(200) NOT NULL DEFAULT '' COMMENT '微信id' ;

ALTER TABLE `video_task`.`u_invitation_user`
    ADD INDEX `id_open_id`(`open_id`);