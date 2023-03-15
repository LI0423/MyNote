package com.video.manager.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.video.manager.domain.common.ContentLevelEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: video-manger
 * @description: video
 * @author: laojiang
 * @create: 2020-09-05 10:23
 **/
@Data
@TableName("video")
@Accessors(chain = true)
public class VideoDO implements Serializable {

    private static final long serialVersionUID = -6121482224059104198L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;
    private String fileUrl;
    private String coverUrl;
    private Long praiseNum;
    private Long views;
    private String author;
    private String authorHeadUrl;
    private Long duration;
    private Long size;
    private String types;
    private ContentLevelEnum contentLevel;
    /**
     * 来源的视频编号
     */
    private String sourceVideoId;
    /**
     * 来源
     */
    private String source;

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

    private Integer redisStatus;

    private String categoryIds;

}
