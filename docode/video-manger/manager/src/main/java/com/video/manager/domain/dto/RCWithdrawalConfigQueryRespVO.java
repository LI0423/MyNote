package com.video.manager.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RCWithdrawalConfigQueryRespVO {
    private String pkg;
    private String token;
    private Long dayCeiling;
}
