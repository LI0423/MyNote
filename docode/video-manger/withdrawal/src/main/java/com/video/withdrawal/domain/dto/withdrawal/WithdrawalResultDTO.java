package com.video.withdrawal.domain.dto.withdrawal;

import lombok.Data;

@Data
public class WithdrawalResultDTO {

    private Long amount;

    private String partnerTradeNo;

    private String errorCode;

    private String errorDesc;
}
