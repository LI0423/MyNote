package com.video.payment.domain.dto.third;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author wangwei
 */
@Builder
@Data
public class AliPayTradeQueryRequestDTO {

    /**
     * 订单号
     */
    @JsonProperty("out_trade_no")
    private String outTradeNo;
}
