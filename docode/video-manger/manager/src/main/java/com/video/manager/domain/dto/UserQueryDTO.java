package com.video.manager.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: video-manger
 * @description: user
 * @author: laojiang
 * @create: 2020-09-03 14:51
 **/
@Data
public class UserQueryDTO implements Serializable {
    private static final long serialVersionUID = 5245040936670284254L;

    private Long appId;
    private String startDate;
    private String endDate;
}
