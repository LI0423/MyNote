CREATE EXTERNAL TABLE `外表名`(
  `data_date` date ,
  `image_id` bigint ,
  `download_uv` bigint ,
  `download_pv` bigint ,
  `collect_uv` bigint ,
  `collect_pv` bigint ,
  `click_uv` bigint ,
  `click_pv` bigint ,
  `view_pv` bigint ,
  `view_uv` bigint ,
  `detail_pv` bigint ,
  `detail_uv` bigint ,
  `country` string
) PARTITIONED BY (
  `day` string )
ROW FORMAT DELIMITED 
  FIELDS TERMINATED BY '\t' 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION
  '在数据仓库里的存储位置'

