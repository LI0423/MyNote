package com.video.manager.controller;

import com.video.manager.domain.common.ResponseResult;
import com.video.manager.domain.dto.*;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.service.RCWithdrawalConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/riskcontrol/withdrawal")
public class RCWithdrawalConfigController {

    @Autowired
    RCWithdrawalConfigService rcWithdrawalConfigService;

    @PostMapping("/query")
    public ResponseResult query(@RequestBody RCWithdrawalConfigQueryReqVO body) {
        String pkg = body.getPkg();
        List<String> tokenList = body.getToken();
        RCWithdrawalConfigQueryRespDTO query = rcWithdrawalConfigService.query(pkg, tokenList);
        RCWithdrawalConfigQueryRespDTO.Status status = query.getStatus();
        if (RCWithdrawalConfigQueryRespDTO.Status.SUCCESS.equals(status)) {
            List<RCWithdrawalConfigQueryRespVO> resultList = new ArrayList<>();
            List<RCWithdrawalConfigQueryRespConfigDTO> configList = query.getConfigList();
            for (RCWithdrawalConfigQueryRespConfigDTO config : configList) {
                RCWithdrawalConfigQueryRespVO rcRewardConfigQueryRespVO = RCWithdrawalConfigQueryRespVO.builder()
                        .pkg(config.getPkg())
                        .token(config.getToken())
                        .dayCeiling(config.getCeiling())
                        .build();
                resultList.add(rcRewardConfigQueryRespVO);
            }
            return ResponseResult.success(resultList);
        } else {
            return ResponseResult.failure(ErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseResult add(@RequestBody RCWithdrawalConfigAddReqVO body) {
        List<String> token = body.getToken();
        Long ceiling = body.getDayCeiling();
        String pkg = body.getPkg();
        RCWithdrawalConfigAddRespDTO rcWithdrawalConfigAddRespDTO = rcWithdrawalConfigService.add(pkg, token, ceiling);
        RCWithdrawalConfigAddRespDTO.Status status = rcWithdrawalConfigAddRespDTO.getStatus();
        if (status.equals(RCWithdrawalConfigAddRespDTO.Status.SUCCESS)) {
            return ResponseResult.success(null);
        } else if (status.equals(RCWithdrawalConfigAddRespDTO.Status.PARTIAl_SUCCESS)) {
            List<String> failToken = rcWithdrawalConfigAddRespDTO.getFailToken();
            return ResponseResult.response(Boolean.TRUE, ErrorCodeEnum.SUCCESS_PARTIAL_FAIL, failToken);
        } else {
            return ResponseResult.failure(ErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    @PostMapping("/update")
    public ResponseResult update(@RequestBody RCWithdrawalConfigUpdateReqVO body) {
        List<String> token = body.getToken();
        Long ceiling = body.getDayCeiling();
        String pkg = body.getPkg();
        RCWithdrawalConfigUpdateRespDTO update = rcWithdrawalConfigService.update(pkg, token, ceiling);
        RCWithdrawalConfigUpdateRespDTO.Status status = update.getStatus();
        if (status.equals(RCWithdrawalConfigUpdateRespDTO.Status.SUCCESS)) {
            return ResponseResult.success(null);
        } else if (status.equals(RCWithdrawalConfigUpdateRespDTO.Status.PARTIAl_SUCCESS)) {
            List<String> failToken = update.getFailToken();
            return ResponseResult.response(Boolean.TRUE, ErrorCodeEnum.SUCCESS_PARTIAL_FAIL, failToken);
        } else {
            return ResponseResult.failure(ErrorCodeEnum.UNKNOWN_ERROR);
        }
    }
}
