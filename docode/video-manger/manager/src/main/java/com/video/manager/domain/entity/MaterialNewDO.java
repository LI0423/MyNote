package com.video.manager.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.video.manager.domain.common.ContentLevelEnum;
import com.video.manager.domain.common.CrabVideoSandboxStatusEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zsy
 */
@Data
@TableName("material_new")
public class MaterialNewDO implements Serializable {
    private static final long serialVersionUID = -1976117294490362795L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 标题
     */
    private String title;
    /**
     * 视频相对地址
     */
    private String bsyUrl;
    /**
     * 视频封面相对地址
     */
    private String bsyImgUrl;
    /**
     * 喜爱值
     */
    private String loveCount;
    /**
     * 播放次数
     */
    private String watchCount;
    /**
     * 视频作者
     */
    private String videoAuthor;
    /**
     * 分类icon
     */
    private String bsyHeadUrl;
    /**
     * 视频时长
     */
    private String videoTime;
    /**
     * 视频大小
     */
    private String videoSize;
    /**
     * 横屏竖屏
     */
    private String types;
    /**
     * 来源
     */
    private String source;
    /**
     * 更新时间
     */
    private Long utime;

    /**
     * 内容级别
     */
    private ContentLevelEnum contentLevel;
    /**
     * 分类编号
     */
    private Long catId;
    /**
     * 发布状态
     */
    private CrabVideoSandboxStatusEnum status;

}
