package com.video.manager.domain.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataConfigDTO {

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    private String item;

    private String value;

    private Integer status;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private String creator;

    private String modifier;

}
