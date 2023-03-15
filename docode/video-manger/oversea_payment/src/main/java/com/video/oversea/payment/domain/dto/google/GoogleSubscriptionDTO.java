package com.video.oversea.payment.domain.dto.google;

import lombok.Data;

/**
 * @author sunjiahui
 * @date 2022/1/24 7:25 下午
 */
@Data
public class GoogleSubscriptionDTO {

    private String orderId;

    private Long priceAmountMicros;

    private String countryCode;

    private Integer paymentState;

    private String priceCurrencyCode;

    private Long expiryTimeMillis;

    private Long startTimeMillis;

    private Integer cancelReason;

    private Long userCancellationTimeMillis;
}
