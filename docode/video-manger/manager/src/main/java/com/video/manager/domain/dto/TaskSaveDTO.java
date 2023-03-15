package com.video.manager.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.video.entity.TaskGroupEnum;
import com.video.entity.TaskTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: video-manger
 * @description: task save
 * @author: laojiang
 * @create: 2020-09-03 14:18
 **/
@Data
public class TaskSaveDTO implements Serializable {

    @JsonDeserialize(using = NumberDeserializers.LongDeserializer.class)
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
}
