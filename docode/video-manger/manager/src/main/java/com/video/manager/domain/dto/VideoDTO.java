package com.video.manager.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.video.manager.domain.common.ContentLevelEnum;
import com.video.manager.domain.common.CrabVideoSandboxStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: video-manger
 * @description: CrabVideoSandbox
 * @author: laojiang
 * @create: 2020-09-03 16:09
 **/
@Data
public class VideoDTO implements Serializable {
    private static final long serialVersionUID = -3669871515210832562L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 　原始ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long crabDataOriginId;

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
    private String videoId;
    private List<Long> categoryIds;
    private String source;

    private Date createTime;
    private String createBy;
    private Date lastModifyTime;
    private String lastModifyBy;
}
