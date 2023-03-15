package com.video.manager.domain.dto;

import lombok.Data;

@Data
public class BlackUserDTO {

    /**
     * 用户token
     */
    private String token;

    /**
     * 封禁原因
     */
    private String desc;
}
