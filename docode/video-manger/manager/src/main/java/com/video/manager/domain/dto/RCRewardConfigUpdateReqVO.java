package com.video.manager.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class RCRewardConfigUpdateReqVO {
    private String pkg;
    private List<String> token;
    private Float coefficient;
}
