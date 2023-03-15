package com.video.manager.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: video-manger
 * @description: 视频分类表
 * @author: laojiang
 * @create: 2020-09-04 11:21
 **/
@Data
@TableName("video_task.video_category")
public class VideoCategoryDO implements Serializable {

    private static final long serialVersionUID = -665479510996740550L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long videoId;
    private Long categoryId;

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
}
