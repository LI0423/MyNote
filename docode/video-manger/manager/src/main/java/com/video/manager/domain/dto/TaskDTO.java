package com.video.manager.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.video.entity.TaskGroupEnum;
import com.video.entity.TaskTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: video-manger
 * @description: task dto
 * @author: laojiang
 * @create: 2020-09-03 14:15
 **/
@Data
public class TaskDTO implements Serializable {
    private static final long serialVersionUID = -1459731781481342302L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long appId;
    private String code;
    private String name;
    private String remark;
    private Long minGoldCoin;
    private Long maxGoldCoin;
    private String buttonTextBefore;
    private String buttonTextAfter;
    private Integer sort;
    private TaskGroupEnum taskGroup;
    private TaskTypeEnum taskType;
    private Date createTime;
    private String createBy;
    private Date lastModifyTime;
    private String lastModifyBy;
    private String supportAppMinVersion;
}
