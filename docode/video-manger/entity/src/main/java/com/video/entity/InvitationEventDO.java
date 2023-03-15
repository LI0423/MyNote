package com.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lh
 * @date 2022/2/18 2:10 下午
 */
@Data
@TableName("u_invitation_event")
public class InvitationEventDO implements Serializable {

    private static final long serialVersionUID = -6110003192698675857L;

    @TableId(type= IdType.ASSIGN_ID)
    private Long id;

    private Long appId;
    /**
     * 邀请记录id
     */
    private Long invitationId;
    /**
     * 事件类型
     */
    private String invitationEventKey;
    /**
     * 金豆领取状态
     */
    private Integer receivePriceStatus;

    /**
     * 待领取金额
     */
    private Long toReceivePrice;
    /**
     * 已领取金额
     */
    private Long alreadyReceivePrice;

    private Date createdAt;

    private Date updatedAt;
}
