package com.video.payment.domain.dto.third;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 * @author wangwei
 */
@Data
public class WeChatPaymentResponseDTO {

    @JsonAlias("prepay_id")
    private String prepayId;
}
