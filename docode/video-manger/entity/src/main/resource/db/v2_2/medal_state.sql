CREATE TABLE `medal_state` (
  `id` bigint NOT NULL,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `type` int NOT NULL COMMENT '勋章类型',
  `condition_value` int NOT NULL COMMENT '获取奖励的条件值',
  `state` int NOT NULL COMMENT '勋章状态',
  `modify_time` datetime DEFAULT now() COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `UID_TYPE_CONDITION_STATE_INDEX` (`user_id`, `type`, `condition_value`, `state`),
  KEY `UID_TYPE_CONDITION_INDEX` (`user_id`, `type`, `condition_value`),
  KEY `UID_TYPE_INDEX` (`user_id`, `type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8