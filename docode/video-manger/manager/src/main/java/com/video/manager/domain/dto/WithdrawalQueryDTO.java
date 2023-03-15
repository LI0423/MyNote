package com.video.manager.domain.dto;

import com.video.entity.WithdrawalStatusEnum;
import com.video.entity.WithdrawalTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: video-manger
 * @description: Withdrawal
 * @author: laojiang
 * @create: 2020-09-03 14:38
 **/
@Data
public class WithdrawalQueryDTO implements Serializable {
    private static final long serialVersionUID = -2516804762795051993L;

    private Long appId;
    private Integer status;
    /**
     * 红包0 金豆1
     */
    private Integer cashType;
    private String startDate;
    private String endDate;
    private Long userId;
}
