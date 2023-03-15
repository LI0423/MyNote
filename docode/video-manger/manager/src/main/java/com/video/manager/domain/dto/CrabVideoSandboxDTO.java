package com.video.manager.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.video.manager.domain.common.ContentLevelEnum;
import com.video.manager.domain.common.CrabVideoSandboxStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
public class CrabVideoSandboxDTO implements Serializable {
    private static final long serialVersionUID = -3669871515210832562L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 　原始ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long crabDataOriginId;
    private String source;
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

    private List<Integer> categoryIds;
    /**
     * 状态
     */
    private CrabVideoSandboxStatusEnum status;
    /**
     * 原因
     */
    private String reason;
    /**
     * 抓取的视频的编号
     */
    private String videoId;

    private Date createTime;
    private String createBy;
    private Date lastModifyTime;
    private String lastModifyBy;
}
