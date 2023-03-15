package com.video.manager.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.video.manager.domain.common.CrabAddressStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: video-manger
 * @description: CrabAddress
 * @author: laojiang
 * @create: 2020-09-03 15:35
 **/
@Data
public class CrabAddressDTO implements Serializable {
    private static final long serialVersionUID = -2898665474904775919L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String source;
    private String url;
    private CrabAddressStatusEnum status;
    private String reason;
    private Integer attemptNum;
    private Date createTime;
    private String createBy;
    private Date lastModifyTime;
    private String lastModifyBy;
}
