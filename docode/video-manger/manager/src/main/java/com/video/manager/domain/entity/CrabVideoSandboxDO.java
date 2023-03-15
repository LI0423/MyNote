package com.video.manager.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.video.manager.domain.common.ContentLevelEnum;
import com.video.manager.domain.common.CrabVideoSandboxStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: api
 * @description: 解析后的视频数据暂存架
 * @author: laojiang
 * @create: 2020-08-22 14:54
 **/
@Data
@TableName("crab_video_sandbox")
public class CrabVideoSandboxDO implements Serializable {

    private static final long serialVersionUID = 9213966864667272426L;


    @TableId(type= IdType.AUTO)
    private Long id;
    /**
     * 　原始ID
     */
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
