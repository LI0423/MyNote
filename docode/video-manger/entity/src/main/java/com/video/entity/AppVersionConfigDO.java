package com.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author lh
 * @date 2022/1/14 3:37 下午
 */
@Data
@TableName("app_version_config")
public class AppVersionConfigDO {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long appId;

    private String versionCode;

    private Integer createAnonymousUser;

    private Date createdAt;

    private Date updatedAt;
}
