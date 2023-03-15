package com.video.manager.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: mango
 * @description: AppAdConfigDetail
 * @author: laojiang
 * @create: 2020-10-21 16:11
 **/
@Data
public class AppAdConfigDetailDTO implements Serializable  {

    private static final long serialVersionUID = -1647560643321528714L;

    private String key;
    private String value;
}
