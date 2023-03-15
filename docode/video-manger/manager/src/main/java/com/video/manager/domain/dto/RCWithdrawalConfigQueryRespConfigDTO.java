package com.video.manager.domain.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class RCWithdrawalConfigQueryRespConfigDTO {
    private String pkg;
    private String token;
    private Long ceiling;
}
