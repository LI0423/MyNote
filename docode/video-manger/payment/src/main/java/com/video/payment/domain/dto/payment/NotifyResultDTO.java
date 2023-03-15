package com.video.payment.domain.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通知应答包体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotifyResultDTO {

    private static NotifyResultDTO SUCCESS = new NotifyResultDTO("SUCCESS", "成功");

    private static NotifyResultDTO FAIL = new NotifyResultDTO("FAIL", "系统错误");

    private String code;

    private String message;

    public static NotifyResultDTO success() {
        return SUCCESS;
    }

    public static NotifyResultDTO fail() {
        return FAIL;
    }
}
