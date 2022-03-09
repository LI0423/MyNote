insert overwrite table 外表名 PARTITION(day='$time_day')
select 
    from_unixtime(unix_timestamp('$time_day','yyyyMMdd'),'yyyy-MM-dd') datadate ,
    get_json_object(request_body,'$.id') image_id , 
    get_json_object(request_body,'$.key') event_key, 
    max(get_json_object(request_body,'$.type')) type,
    count(*) pv, 
    count(distinct tk) uv,
    country_code
from duapps_mv.mv_report_log
where day='$time_day' and pkg in ('。。。')
and get_json_object(request_body,'$.id') is not null
group by get_json_object(request_body,'$.id'), get_json_object(request_body,'$.key'),day,country_code