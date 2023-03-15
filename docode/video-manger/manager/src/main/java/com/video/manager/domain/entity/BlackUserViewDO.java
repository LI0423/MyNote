package com.video.manager.domain.entity;

import lombok.Data;

@Data
public class BlackUserViewDO {

    private Long userId;

    private String token;

    private String name;

    private String pkg;

    private String pkgName;

    /**
     * 黑名单记录创建时间
     */
    private String blackCreateTime;
}
