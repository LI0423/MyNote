package com.video.manager.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: video-manger
 * @description: gold coin query dto
 * @author: laojiang
 * @create: 2020-09-03 14:46
 **/
@Data
public class GoldCoinQueryDTO implements Serializable {

    private static final long serialVersionUID = -9014819355837609375L;

    private Long appId;
    private String startDate;
    private String endDate;
    private Long userId;
}
