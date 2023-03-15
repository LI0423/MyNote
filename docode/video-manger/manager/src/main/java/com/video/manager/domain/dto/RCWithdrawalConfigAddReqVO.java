package com.video.manager.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class RCWithdrawalConfigAddReqVO {
    private String pkg;
    private List<String> token;
    private Long dayCeiling;
}
