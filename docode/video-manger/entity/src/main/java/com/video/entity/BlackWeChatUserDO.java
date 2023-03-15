package com.video.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 黑名单微信用户
 */
@Data
@TableName("black_we_chat_user")
public class BlackWeChatUserDO {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 微信open id
     */
    private String openId;

    /**
     * 包名
     */
    private String pkg;

    /**
     * 拉黑原因
     */
    @TableField("`desc`")
    private String desc;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后一次修改时间
     */
    private Date lastModifyTime;
}
