CREATE TABLE `material` (
  `id` bigint(20) NOT NULL COMMENT '视频封面相对地址',
  `title` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '标题',
  `bsy_url` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '视频相对地址',
  `bsy_img_url` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '视频封面相对地址',
  `love_count` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '喜爱值',
  `watch_count` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '播放次数',
  `video_author` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '视频分类',
  `bsy_head_url` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分类icon',
  `video_time` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '视频时长',
  `video_size` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '视频大小',
  `types` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '横屏竖屏',
  `source` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '来源',
  `utime` bigint(20) DEFAULT NULL COMMENT '修改时间',
  category_id  bigint(20) DEFAULT NULL COMMENT '分类编号',
  content_level int DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;




CREATE TABLE `category` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '视频封面相对地址',
   name varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '名字',
   sort int,
   create_time date,
   last_modify_time date,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;



CREATE TABLE `manager` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '视频封面相对地址' ,
   name varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '名字',
   email varchar(100) COLLATE utf8mb4_bin,
   role varchar(100) COLLATE utf8mb4_bin,
   create_time date,
   last_modify_time date,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;




CREATE TABLE `video_category` (
  `id` bigint(20) NOT NULL,
   video_id bigint,
   category_id bigint,
   create_time date,
   create_by varchar(20),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


CREATE TABLE `video` (
  `id` bigint(20) NOT NULL  auto_increment COMMENT '视频封面相对地址',
  `title` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '标题',
  `file_url` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '视频相对地址',
  `cover_url` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '视频封面相对地址',
  `praise_num` bigint default 0 COMMENT '喜爱值',
  `views` bigint default 0,
  `author` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '视频分类',
  `author_head_url` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分类icon',
  `duration` bigint default 0 COMMENT '视频时长',
  `size` bigint default 0 COMMENT '视频大小',
  `types` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '横屏竖屏',
  `source` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '来源',
   content_level int DEFAULT 0,
   source_video_id varchar(100),
   `create_time` datetime,
   create_by varchar(100),
   last_modify_time datetime,
   last_modify_by varchar(100),
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

---------



CREATE TABLE `manager` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '名字',
  `email` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `role` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `last_modify_time` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin


--广告配置需求相关的表

CREATE TABLE `province` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '名字',
  `name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


CREATE TABLE `city` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '',
  `name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
   `province_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;



CREATE TABLE `country` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '',
  `name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `city_id` bigint(20) NOT NULL,
  `province_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


CREATE TABLE `app_ad_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` bigint(20) NOT NULL,
  `pkg` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `province_code` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '',
  `province_name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `city_code` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '',
  `city_name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
   province_and_city_name varchar(100),
   config_json text,
   status int,
  `create_time` datetime,
   create_by varchar(100),
   last_modify_time datetime,
   last_modify_by varchar(100),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `app_ad_config_app_vn` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_ad_config_id` bigint(20) NOT NULL,
  `vn` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


CREATE TABLE `app_ad_config_app_sid` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_ad_config_id` bigint(20) NOT NULL,
  `sid` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


CREATE TABLE `app_ad_config_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_ad_config_id` bigint(20) NOT NULL,
  `data_key` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `value` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;



alter table app add column sids varchar(2000);
alter table app add column app_vns varchar(2000);

