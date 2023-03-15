package com.video.manager.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: api
 * @description: 管理员
 * @author: laojiang
 * @create: 2020-08-18 15:37
 **/
@Data
@TableName("manager")
public class ManagerDO implements Serializable {

    private static final long serialVersionUID = 8991960476343941008L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;
    private String email;
    private String role;
    /**
     * 添加时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 最近修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date lastModifyTime;
    /**
     * 最近修改人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String lastModifyBy;

    private String permission;

    private String appPkgs;

}
