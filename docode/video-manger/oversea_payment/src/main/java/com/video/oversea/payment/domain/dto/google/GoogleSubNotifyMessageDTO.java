package com.video.oversea.payment.domain.dto.google;

import lombok.Data;

@Data
public class GoogleSubNotifyMessageDTO {

    private Long userId;

    private Integer businessType;

    private String pkg;

    private Long oldOrderId;

    private Long newOrderId;

    private Long expiryTimeMillis;

    private Long startTimeMillis;

}
