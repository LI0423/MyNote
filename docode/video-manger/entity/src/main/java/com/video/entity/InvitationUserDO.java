package com.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lh
 * @date 2022/2/18 2:08 下午
 */
@Data
@TableName("u_invitation_user")
public class InvitationUserDO implements Serializable {

    private static final long serialVersionUID = -6110003192698675857L;

    @TableId(type= IdType.ASSIGN_ID)
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 商品id
     */
    private Long appId;
    /**
     * 被邀请用户id
     */
    private Long invitationUserId;
    /**
     * 邀请累积获取的金豆数目
     */
    private Long invitationPrice;

    /**
     * 邀请事件的状态 0未完成 1完成
     */
    private Integer invitationStatus;

    private Date createdAt;

    private Date updatedAt;

    private String openId;
}
