package com.video.payment.service;


import com.video.payment.domain.dto.withdrawal.WithdrawalQueryDTO;
import com.video.payment.domain.dto.withdrawal.WithdrawalResultDTO;

/**
 * @program: mango
 * @description: Withdrawal service
 * @author: laojiang
 * @create: 2020-09-10 12:25
 **/
public interface WithdrawalService {

    WithdrawalResultDTO withdrawal(WithdrawalQueryDTO withdrawalQueryDTO);

    /**
     * 支付宝转账
     * @param withdrawalQueryDTO
     * @return
     */
    WithdrawalResultDTO aliWithdrawal(WithdrawalQueryDTO withdrawalQueryDTO);
}
