package com.video.manager.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * @program: mango
 * @description: AppAdConfig
 * @author: zsy
 * @create: 2020-11-18 15:54
 **/
@Data
public class GetStatusFromMaterialFeedDTO {
    private Integer ret ;
    private String message;
    private String result;
    private Date serverTime;
}
