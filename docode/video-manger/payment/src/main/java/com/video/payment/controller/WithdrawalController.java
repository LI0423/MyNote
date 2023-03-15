package com.video.payment.controller;

import com.video.entity.PayOrderChannelEnum;
import com.video.payment.domain.common.ResponseResult;
import com.video.payment.domain.dto.withdrawal.WithdrawalQueryDTO;
import com.video.payment.domain.dto.withdrawal.WithdrawalResultDTO;
import com.video.payment.exception.ErrorCodeEnum;
import com.video.payment.service.WithdrawalService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: mango
 * @description: Withdrawal controller
 * @author: laojiang
 * @create: 2020-09-10 13:08
 **/
@Slf4j
@RestController
@RequestMapping("/api/v1/withdrawal")
public class WithdrawalController extends BaseController {

    @Autowired
    private WithdrawalService withdrawalService;

    @PostMapping
    public ResponseResult<WithdrawalResultDTO> withdrawal(
            @RequestBody WithdrawalQueryDTO withdrawalQueryDTO) {

        if (withdrawalQueryDTO.getUserId() == null
                || StringUtils.isBlank(withdrawalQueryDTO.getPkg())
                || withdrawalQueryDTO.getAmount() == null
                || withdrawalQueryDTO.getAmount() <= 0
                || withdrawalQueryDTO.getPartnerTradeNo() == null) {
            return ResponseResult.failure(ErrorCodeEnum.PARAM_ERROR);
        }

        if (ObjectUtils.isEmpty(withdrawalQueryDTO.getChannel())
                || withdrawalQueryDTO.getChannel() == PayOrderChannelEnum.WE_CHAT.getCode()) {
            return ResponseResult.success(withdrawalService.withdrawal(withdrawalQueryDTO));
        } else if (withdrawalQueryDTO.getChannel() == PayOrderChannelEnum.ALI_PAY.getCode()){
            return ResponseResult.success(withdrawalService.aliWithdrawal(withdrawalQueryDTO));
        }

        return ResponseResult.failure(ErrorCodeEnum.PARAM_ERROR);
    }
}
