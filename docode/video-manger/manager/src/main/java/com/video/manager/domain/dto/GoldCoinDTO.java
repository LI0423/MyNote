package com.video.manager.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.video.manager.excel.ExcelCell;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: video-manger
 * @description: Gold coin dto
 * @author: laojiang
 * @create: 2020-09-03 14:48
 **/
@Data
public class GoldCoinDTO implements Serializable {
    private static final long serialVersionUID = -7907371228388708594L;

    @JsonSerialize(using = ToStringSerializer.class)
    @ExcelCell(index = 0)
    private String id;
    @ExcelCell(index = 1)
    private Date createTime;
    @ExcelCell(index = 2)
    private String userId;
    @ExcelCell(index = 3)
    private String taskName;
    @ExcelCell(index = 4)
    private Long number;
}
