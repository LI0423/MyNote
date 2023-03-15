package com.video.manager.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.video.manager.domain.common.CategoryHiddenEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: api
 * @description: 分类
 * @author: laojiang
 * @create: 2020-08-18 14:25
 **/
@Data
@TableName("category")
public class CategoryDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;
    private Integer sort;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date lastModifyTime;

    private CategoryHiddenEnum hidden;


}
