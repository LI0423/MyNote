package com.video.manager.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

@Data
@TableName("data_config_record")
public class DataConfigRecordDO {

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @JsonProperty(value = "sItem")
    private String sItem;

    @JsonProperty(value = "tItem")
    private String tItem;

    @JsonProperty(value = "sValue")
    private String sValue;

    @JsonProperty(value = "tValue")
    private String tValue;

    @JsonProperty(value = "sRemark")
    private String sRemark;

    @JsonProperty(value = "tRemark")
    private String tRemark;

    @JsonProperty(value = "sStatus")
    private Integer sStatus;

    @JsonProperty(value = "tStatus")
    private Integer tStatus;

    /**
     * 操作时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 操作人
     */
    private String operator;

}
