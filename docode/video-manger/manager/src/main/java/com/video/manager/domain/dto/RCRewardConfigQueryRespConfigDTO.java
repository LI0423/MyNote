package com.video.manager.domain.dto;

import lombok.*;


@Data
@Builder
public class RCRewardConfigQueryRespConfigDTO {
    private String pkg;
    private String token;
    private Float coefficient;
}
