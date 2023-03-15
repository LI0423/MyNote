package com.video.manager.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: video-manger
 * @description: app query dto
 * @author: laojiang
 * @create: 2020-09-03 14:12
 **/
@Data
public class AppQueryDTO implements Serializable {
    private static final long serialVersionUID = -6922845064941196900L;

    private String pkg;
}
