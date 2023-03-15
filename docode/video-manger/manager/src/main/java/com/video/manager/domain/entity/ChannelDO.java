package com.video.manager.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@TableName("channel")
@Builder
public class ChannelDO {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String pkg;
    private String channelName;
    private Integer sort;
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

    private int source;

    private String sourceChannelId;
}
