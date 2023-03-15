package com.video.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: video-manger
 * @description: 任务配置表
 * @author: laojiang
 * @create: 2020-08-26 15:45
 **/
@Data
@TableName("task_config")
public class TaskConfigDO implements Serializable {
    private static final long serialVersionUID = -4693873453920293961L;

    @TableId(type= IdType.ASSIGN_ID)
    private Long id;

    private Long taskId;

    @JsonProperty("cKey")
    private String cKey;
    private String value;

    /**
     * 添加时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 添加人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
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

}
