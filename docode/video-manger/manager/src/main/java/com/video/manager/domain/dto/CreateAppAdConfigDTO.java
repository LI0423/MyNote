package com.video.manager.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: mango
 * @description: AppAdConfig
 * @author: laojiang
 * @create: 2020-10-21 15:54
 **/
@Data
public class CreateAppAdConfigDTO implements Serializable {

    private static final long serialVersionUID = -6597235311166988300L;

    private Long id;
    private Long appId;
    private String pkg;
    private String name;
    private String provinceCode;
    private String cityCode;

    private List<String> sids;
    private List<String> appVns;

    private List<AppAdConfigDetailDTO> details;
}
