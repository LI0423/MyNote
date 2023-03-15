package com.video.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("black_user")
public class BlackUserDO {

    /**
     * 用户token
     */
    @TableId
    private String token;

    /**
     * 封禁原因
     */
    private String bDesc;

    /**
     * 创建时间
     */
    protected Date createTime;
}
