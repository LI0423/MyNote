package com.video.withdrawal.controller;

import com.video.entity.WithdrawalStatusEnum;
import com.video.withdrawal.constant.BusinessHeaderConstants;
import com.video.withdrawal.constant.BusinessParamConstants;
import com.video.withdrawal.domain.common.ResponseResult;
import com.video.withdrawal.domain.dto.app.AppDTO;
import com.video.withdrawal.domain.dto.third.PaymentCheckNameEnum;
import com.video.withdrawal.domain.dto.third.PaymentDTO;
import com.video.withdrawal.domain.dto.third.PaymentResultDTO;
import com.video.withdrawal.domain.dto.third.PaymentReturnCodeEnum;
import com.video.withdrawal.domain.dto.user.UserAuthDTO;
import com.video.withdrawal.domain.dto.withdrawal.WithdrawalQueryDTO;
import com.video.withdrawal.domain.dto.withdrawal.WithdrawalResultDTO;
import com.video.withdrawal.exception.ErrorCodeEnum;
import com.video.withdrawal.service.WithdrawalService;
import com.video.withdrawal.service.third.ThirdPaymentFactoryService;
import com.video.withdrawal.service.third.ThirdPaymentService;
import com.video.withdrawal.util.IdentifierGeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

        Long userId = withdrawalQueryDTO.getUserId();
        String pkg = withdrawalQueryDTO.getPkg();

        return ResponseResult.success(
                withdrawalService.withdrawal(userId, pkg,
                        withdrawalQueryDTO.getAmount(), withdrawalQueryDTO.getPartnerTradeNo()));
    }
}
