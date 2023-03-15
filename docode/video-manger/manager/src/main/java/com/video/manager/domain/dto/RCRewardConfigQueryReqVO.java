package com.video.manager.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class RCRewardConfigQueryReqVO {
    private String pkg;
    private List<String> token;
}
