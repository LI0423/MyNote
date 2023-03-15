package com.video.payment.domain.dto.third.refund;

import lombok.Data;

/**
 * 资金账户及金额
 */
@Data
public class AccountAndAmountDTO {

    /**
     * 出资账户类型
     */
    private AccountEnum account;

    /**
     * 出资金额
     */
    private Integer amount;
}
