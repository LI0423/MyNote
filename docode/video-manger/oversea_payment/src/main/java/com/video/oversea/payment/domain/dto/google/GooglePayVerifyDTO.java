package com.video.oversea.payment.domain.dto.google;

import lombok.Data;

@Data
public class GooglePayVerifyDTO {

    private String pkg;
    private String subscriptionId;
    private String purchaseToken;
}
