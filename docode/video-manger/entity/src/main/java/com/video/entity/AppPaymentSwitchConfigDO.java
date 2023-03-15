package com.video.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("app_payment_switch_config")
public class AppPaymentSwitchConfigDO {

    /**
     * 应用id
     */
    @TableId
    private Long appId;

    /**
     * 微信提现开关
     */
    private AppPaymentSwitchEnum weChatWithdrawalSwitch;

    /**
     * 支付提现开关
     */
    private AppPaymentSwitchEnum aliWithdrawalSwitch;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 最后一次更新时间
     */
    private Date lastModifyTime;

    /**
     * 最后一次更新人
     */
    private String lastModifyBy;
}
