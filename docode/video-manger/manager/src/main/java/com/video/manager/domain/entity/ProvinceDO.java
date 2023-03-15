package com.video.manager.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: mango
 * @description: 省
 * @author: laojiang
 * @create: 2020-10-20 18:05
 **/
@Data
@TableName("province")
public class ProvinceDO implements Serializable {

    private static final long serialVersionUID = -5588931375336678000L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String name;
    private String code;
}
