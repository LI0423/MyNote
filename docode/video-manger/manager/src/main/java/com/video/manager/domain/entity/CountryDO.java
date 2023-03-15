package com.video.manager.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: mango
 * @description: 区县
 * @author: laojiang
 * @create: 2020-10-20 18:07
 **/
@Data
@TableName("country")
public class CountryDO implements Serializable {

    private static final long serialVersionUID = 3393666326466590645L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String code;
    private String name;
    private Long cityId;
    private Long provinceId;
}
