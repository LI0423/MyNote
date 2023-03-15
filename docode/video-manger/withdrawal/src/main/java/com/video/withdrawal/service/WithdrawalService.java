package com.video.withdrawal.service;

import com.video.withdrawal.domain.dto.withdrawal.WithdrawalResultDTO;

import java.util.List;

/**
 * @program: mango
 * @description: Withdrawal service
 * @author: laojiang
 * @create: 2020-09-10 12:25
 **/
public interface WithdrawalService {

    WithdrawalResultDTO withdrawal(Long userId, String pkg, Long amount, String partnerTradeNo);
}
