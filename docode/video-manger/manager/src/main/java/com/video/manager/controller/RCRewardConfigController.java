package com.video.manager.controller;

import com.video.manager.domain.common.ResponseResult;
import com.video.manager.domain.dto.*;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.service.RCRewardConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1/riskcontrol/reward")
public class RCRewardConfigController {

    @Autowired
    RCRewardConfigService rcRewardConfigService;

    @PostMapping("/query")
    public ResponseResult query(@RequestBody RCRewardConfigQueryReqVO body) {
        String pkg = body.getPkg();
        List<String> tokenList = body.getToken();
        RCRewardConfigQueryRespDTO query = rcRewardConfigService.query(pkg, tokenList);
        RCRewardConfigQueryRespDTO.Status status = query.getStatus();
        if (RCRewardConfigQueryRespDTO.Status.SUCCESS.equals(status)) {
            List<RCRewardConfigQueryRespVO> resultList = new ArrayList<>();
            List<RCRewardConfigQueryRespConfigDTO> configList = query.getConfigList();
            for (RCRewardConfigQueryRespConfigDTO config : configList) {
                RCRewardConfigQueryRespVO rcRewardConfigQueryRespVO = RCRewardConfigQueryRespVO.builder()
                        .pkg(config.getPkg())
                        .token(config.getToken())
                        .coefficient(config.getCoefficient())
                        .build();
                resultList.add(rcRewardConfigQueryRespVO);
            }
            return ResponseResult.success(resultList);
        } else {
            return ResponseResult.failure(ErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseResult add(@RequestBody RCRewardConfigAddReqVO body) {
        List<String> token = body.getToken();
        Float coefficient = body.getCoefficient();
        String pkg = body.getPkg();
        RCRewardConfigAddRespDTO rewardConfigAddRespDTO = rcRewardConfigService.add(pkg, token, coefficient);
        RCRewardConfigAddRespDTO.Status status = rewardConfigAddRespDTO.getStatus();
        if (status.equals(RCRewardConfigAddRespDTO.Status.SUCCESS)) {
            return ResponseResult.success(null);
        } else if (status.equals(RCRewardConfigAddRespDTO.Status.PARTIAl_SUCCESS)) {
            List<String> failToken = rewardConfigAddRespDTO.getFailToken();
            return ResponseResult.response(Boolean.TRUE, ErrorCodeEnum.SUCCESS_PARTIAL_FAIL, failToken);
        } else {
            return ResponseResult.failure(ErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    @PostMapping("/update")
    public ResponseResult update(@RequestBody RCRewardConfigUpdateReqVO body) {
        List<String> token = body.getToken();
        Float coefficient = body.getCoefficient();
        String pkg = body.getPkg();
        RCRewardConfigUpdateRespDTO update = rcRewardConfigService.update(pkg, token, coefficient);
        RCRewardConfigUpdateRespDTO.Status status = update.getStatus();
        if (status.equals(RCRewardConfigUpdateRespDTO.Status.SUCCESS)) {
            return ResponseResult.success(null);
        } else if (status.equals(RCRewardConfigUpdateRespDTO.Status.PARTIAl_SUCCESS)) {
            List<String> failToken = update.getFailToken();
            return ResponseResult.response(Boolean.TRUE, ErrorCodeEnum.SUCCESS_PARTIAL_FAIL, failToken);
        } else {
            return ResponseResult.failure(ErrorCodeEnum.UNKNOWN_ERROR);
        }
    }
}
