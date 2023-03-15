package com.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: video-manger
 * @description: 提现
 * @author: laojiang
 * @create: 2020-08-26 17:54
 **/
@Data
@TableName("withdrawal")
public class WithdrawalDO implements Serializable {

    private static final long serialVersionUID = 5101798909091179422L;

    @TableId(type= IdType.ASSIGN_ID)
    private Long id;
    private Long appId;

    private String method;
    private Date createTime;
    private Long amount;
    private Long number;
    private Long goldBean;
    private Long goldBeanAmount;
    private WithdrawalStatusEnum status;

    // 提现项类型
    private WithdrawalTypeEnum type;

    private String oneTimeUniqueId;

    private Long userId;

    // 微信付款订单号
    private String weChatPaymentNo;

    private String errCode;

    private String errDesc;

    private String token;

    public Double arpu;

    private String anid;

    private String oaid;

}
