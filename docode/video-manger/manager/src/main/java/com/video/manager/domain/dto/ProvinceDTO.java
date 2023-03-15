package com.video.manager.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: mango
 * @description:
 * @author: laojiang
 * @create: 2020-10-21 15:47
 **/
@Data
public class ProvinceDTO implements Serializable {

    private static final long serialVersionUID = -1315058955311250626L;

    private String code;
    private String name;

    private List<CityDTO> children;
}
