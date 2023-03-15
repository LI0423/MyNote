package com.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("userid_token_mapping")
public class UserIdTokenMapping implements Serializable {
    private static final long serialVersionUID = 84173015414787980L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String pkg;

    private Long userId;

    private String token;

    private Long createTime;
}
