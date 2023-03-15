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
public class CrabAddressSaveDTO implements Serializable {
    private static final long serialVersionUID = -2898665474904775919L;

    private String source;
    private String url;
}
