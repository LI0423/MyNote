insert overwrite table 外表名 PARTITION(day='$time_day')
(select 
    from_unixtime(unix_timestamp('$time_day','yyyyMMdd'),'yyyy-MM-dd') datadate ,
    get_json_object(request_body,'$.id') image_id , 

    count(distinct case when get_json_object(request_body,'$.key') = 'download' then a.tk else null end) download_uv,
    sum(case when get_json_object(request_body,'$.key') = 'download' then 1 else 0 end) download_pv,

    count(distinct case when get_json_object(request_body,'$.key') = 'collect' then a.tk else null end) collect_uv,
    sum(case when get_json_object(request_body,'$.key') = 'collect' then 1 else 0 end) collect_pv,

    count(distinct case when get_json_object(request_body,'$.key') = 'image_click' then a.tk else null end) click_uv,
    sum(case when get_json_object(request_body,'$.key') = 'click' then 1 else 0 end) click_pv,

    sum(case when get_json_object(request_body,'$.key') = 'view' then 1 else 0 end) view_pv,
    count(distinct case when get_json_object(request_body,'$.key') = 'view' then a.tk else null end) view_uv,

    sum(case when get_json_object(request_body,'$.key') = 'detail' then 1 else 0 end) detail_pv,
    count(distinct case when get_json_object(request_body,'$.key') = 'detail' then a.tk else null end) detail_uv,
    country_code
from xxx a
where day='$time_day' and pkg in ('...')
and get_json_object(request_body,'$.id') is not null
group by get_json_object(request_body,'$.id'),day,country_code)
union all 
(select 
    from_unixtime(unix_timestamp('$time_day','yyyyMMdd'),'yyyy-MM-dd') datadate ,
    get_json_object(request_body,'$.id') image_id , 

    count(distinct case when get_json_object(request_body,'$.key') = 'download' then a.tk else null end) download_uv,
    sum(case when get_json_object(request_body,'$.key') = 'download' then 1 else 0 end) download_pv,

    count(distinct case when get_json_object(request_body,'$.key') = 'collect' then a.tk else null end) collect_uv,
    sum(case when get_json_object(request_body,'$.key') = 'collect' then 1 else 0 end) collect_pv,

    count(distinct case when get_json_object(request_body,'$.key') = 'click' then a.tk else null end) click_uv,
    sum(case when get_json_object(request_body,'$.key') = 'click' then 1 else 0 end) click_pv,

    sum(case when get_json_object(request_body,'$.key') = 'view' then 1 else 0 end) view_pv,
    count(distinct case when get_json_object(request_body,'$.key') = 'view' then a.tk else null end) view_uv,

    sum(case when get_json_object(request_body,'$.key') = 'detail' then 1 else 0 end) detail_pv,
    count(distinct case when get_json_object(request_body,'$.key') = 'detail' then a.tk else null end) detail_uv,
    'ALL' country_code
from xxx a
where day='$time_day' and pkg in ('....')
and get_json_object(request_body,'$.id') is not null
group by get_json_object(request_body,'$.id'),day)