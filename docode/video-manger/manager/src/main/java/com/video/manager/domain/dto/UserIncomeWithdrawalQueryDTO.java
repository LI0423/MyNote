package com.video.manager.domain.dto;

import lombok.Data;

@Data
public class UserIncomeWithdrawalQueryDTO {

    private String appId;

    private String token;

    private String createTime;

    private String beginTime;

    private String endTime;

}
