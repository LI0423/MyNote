package com.video.manager.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: video-manger
 * @description: task config dto
 * @author: laojiang
 * @create: 2020-09-03 14:27
 **/
@Data
public class TaskConfigDTO implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long taskId;
    @JsonProperty("cKey")
    private String cKey;
    private String value;
    private Date createTime;
    private String createBy;
    private Date lastModifyTime;
    private String lastModifyBy;

}
