package com.video.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: video-manger
 * @description: 任务表
 * @author: laojiang
 * @create: 2020-08-26 15:43
 **/
@Data
@TableName("task")
public class TaskDO implements Serializable {
    private static final long serialVersionUID = -4294268064914596091L;

    @TableId(type= IdType.ASSIGN_ID)
    private Long id;

    /**
     * 应用编号
     */
    private Long appId;

    /**
     * 任务编码
     */
    private String code;
    /**
     * 任务名字
     */
    private String name;
    /**
     * 任务说明
     */
    private String remark;
    /**
     * 最小金币
     */
    private Long minGoldCoin;
    /**
     * 最大金币
     */
    private Long maxGoldCoin;
    /**
     * 任务执行前按钮文字
     */
    private String buttonTextBefore;
    /**
     * 任务执行后按钮文字
     */
    private String buttonTextAfter;

    /**
     * 任务排序
     */
    private Integer sort;
    /**
     * 任务分组
     */
    private TaskGroupEnum taskGroup;

    /**
     * 任务类型
     */
    private TaskTypeEnum taskType;

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

    private String supportAppMinVersion;
}
