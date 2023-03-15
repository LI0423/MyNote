package com.video.manager.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: mango
 * @description:
 * @author: laojiang
 * @create: 2020-10-21 16:20
 **/
@Data
public class AppAdConfigQueryDTO implements Serializable {
    private static final long serialVersionUID = 8624141867421034479L;

    private String pkg;
    private String appVn;
    private String sid;
    private String appId;
}
