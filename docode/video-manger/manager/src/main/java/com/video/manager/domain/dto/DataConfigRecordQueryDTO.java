package com.video.manager.domain.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class DataConfigRecordQueryDTO {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String sItem;

    private String tItem;

    private Integer sStatus;

    private Integer tStatus;

    /**
     * 操作时间
     */
    private String createTime;

    /**
     * 操作人
     */
    private String operator;

    private String beginTime;

    private String endTime;

}
