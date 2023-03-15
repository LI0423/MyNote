package com.video.manager.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.video.entity.AppAdConfigStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: mango
 * @description: AppAdConfig
 * @author: laojiang
 * @create: 2020-10-21 15:54
 **/
@Data
public class AppAdConfigDTO implements Serializable {

    private static final long serialVersionUID = -6597235311166988300L;

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using= ToStringSerializer.class)
    private Long appId;
    private String pkg;
    private String name;
    private String provinceName;
    private String provinceCode;
    private String cityName;
    private String cityCode;
    private String configJson;
    private AppAdConfigStatusEnum status;

    private Date createTime;
    private String createBy;
    private Date lastModifyTime;
    private String lastModifyBy;

    private String sids;
    private String appVns;
    private List<AppAdConfigDetailDTO> details;

}
