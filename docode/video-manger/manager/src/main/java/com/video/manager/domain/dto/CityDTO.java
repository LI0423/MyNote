package com.video.manager.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: mango
 * @description: 城市的孩子节点
 * @author: laojiang
 * @create: 2020-10-21 15:49
 **/
@Data
public class CityDTO implements Serializable {
    private static final long serialVersionUID = 6569040153464359460L;

    private String code;
    private String name;
}
