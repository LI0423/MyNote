package com.video.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lh
 * @date 2022/1/18 3:24 下午
 */
@Data
@TableName("user_ibu_info")
public class UserIbuInfoDO implements Serializable {

    private static final long serialVersionUID = 84173015414787980L;

    @TableId
    private Long id;

    /**
     * 包名
     */
    private String pkg;

    /**
     * token
     */
    private String tk;

    /**
     * 是否ibu用户
     */
    private Integer ibuTag;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最近更新时间
     */
    private Date lastModifyTime;
}
