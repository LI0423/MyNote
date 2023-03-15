package com.video.withdrawal.domain.dto.withdrawal;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WithdrawalQueryDTO {

    private String partnerTradeNo;

    private Long userId;

    private String pkg;

    private Long amount;
}
