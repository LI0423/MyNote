package com.video.manager.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.video.entity.WithdrawalStatusEnum;
import com.video.entity.WithdrawalTypeEnum;
import com.video.manager.excel.ExcelCell;
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
public class WithdrawalDTO implements Serializable {
    private static final long serialVersionUID = -2516804762795051993L;
    @ExcelCell(index = 0)
    private String id;
    @ExcelCell(index = 1)
    private String appId;
    @ExcelCell(index = 4)
    private String method;
    @ExcelCell(index = 3)
    private Date createTime;
    @ExcelCell(index = 5)
    private Long amount;
    private Long number;
    @ExcelCell(index = 6)
    private WithdrawalStatusEnum status;
    @ExcelCell(index = 2)
    private String userId;
    @ExcelCell(index = 7)
    private Date userCreateTime;
    @ExcelCell(index = 8)
    private Double diffHour;
    @ExcelCell(index = 9)
    private String token;
    @ExcelCell(index = 10)
    private Double arpu;
    @ExcelCell(index = 11)
    private Integer cashType;

}
