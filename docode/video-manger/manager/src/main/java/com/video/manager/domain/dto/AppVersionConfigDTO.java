package com.video.manager.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @author lh
 * @date 2022/1/14 3:40 下午
 */
@Data
public class AppVersionConfigDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long appId;

    private String versionCode;

    private Integer createAnonymousUser;

    private Date createdAt;

    private Date updatedAt;
}
