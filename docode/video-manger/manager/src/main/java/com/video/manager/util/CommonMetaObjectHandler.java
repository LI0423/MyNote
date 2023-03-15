package com.video.manager.util;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 类名称：CommonMetaObjectHandler
 * ********************************
 * <p>
 * 类描述：公共元数据处理器
 *
 * @author
 * @date 下午2:57
 */
@Component
@Slf4j
public class CommonMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("新建时，开始填充系统字段！");

        Subject subject = SecurityUtils.getSubject();
        String email=subject.getPrincipal().toString();

        this.strictInsertFill(metaObject, "createTime",
                Date.class,new Date());
        this.strictInsertFill(metaObject, "lastModifyTime",
                Date.class,new Date());

        this.strictInsertFill(metaObject, "createBy",
                String.class, email);
        this.strictInsertFill(metaObject, "lastModifyBy",
                String.class, email);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("更新时，开始填充系统字段！");

        Subject subject = SecurityUtils.getSubject();
        String email = subject.getPrincipal().toString();

        this.strictUpdateFill(metaObject, "lastModifyTime",
                Date.class, new Date());

        this.strictUpdateFill(metaObject, "lastModifyBy",
                String.class, email);
    }
}
