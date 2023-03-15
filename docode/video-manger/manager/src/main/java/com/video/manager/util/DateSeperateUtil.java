package com.video.manager.util;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.exception.BusinessException;
import com.video.manager.exception.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Slf4j
public class DateSeperateUtil {
    public static <T> PageQuery<T> pageQueryInit(PageQuery<T> pageQuery) {
        T query = pageQuery.getQuery();
        try {
            Field field = query.getClass().getDeclaredField("createTime");
            Class<?> beginTime = query.getClass().getDeclaredField("beginTime").getType();
            Class<?> endTime = query.getClass().getDeclaredField("endTime").getType();
            field.setAccessible(true);
            String dataDate = (String) field.get(query);
            if (dataDate != null) {
                Method setBeginTime = query.getClass().getDeclaredMethod("setBeginTime",beginTime);
                Method setEndTime = query.getClass().getDeclaredMethod("setEndTime",endTime);
                setBeginTime.invoke(query, dataDate.split(",")[0]);
                setEndTime.invoke(query, dataDate.split(",")[1]);
            } else {
                throw new BusinessException(ErrorCodeEnum.DATE_EMPTY);
            }
            pageQuery.setQuery(query);
        }catch (Exception e){
            log.error(e.toString());
        }
        return pageQuery;
    }

}
