package com.video.manager.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: mango
 * @description: 市
 * @author: laojiang
 * @create: 2020-10-20 18:07
 **/
@Data
@TableName("city")
public class CityDO implements Serializable {

    private static final long serialVersionUID = -5658617522411855984L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String code;
    private String name;
    private Long provinceId;
}
