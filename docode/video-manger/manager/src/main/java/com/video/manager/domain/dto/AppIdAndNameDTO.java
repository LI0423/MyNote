package com.video.manager.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: video-manger
 * @description: app id and name dto
 * @author: laojiang
 * @create: 2020-09-03 13:55
 **/
@Data
public class AppIdAndNameDTO implements Serializable {

    private static final long serialVersionUID = -3860295576056024835L;

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;
    private String name;
    private String pkg;
}
