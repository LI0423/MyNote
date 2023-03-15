package com.video.manager.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.video.manager.excel.ExcelCell;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: video-manger
 * @description: user
 * @author: laojiang
 * @create: 2020-09-03 14:52
 **/
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 6816004710467454566L;

    @JsonSerialize(using = ToStringSerializer.class)
    @ExcelCell(index = 0)
    private String id;
    @ExcelCell(index = 1)
    private String name;
    @ExcelCell(index = 2)
    private String sex;
    @ExcelCell(index = 3)
    private String avatar;
    @ExcelCell(index = 4)
    private Long number;
    @ExcelCell(index = 5)
    private Long score;
    @ExcelCell(index = 6)
    private Long withdrawalTimes;
    @ExcelCell(index = 7)
    private Long withdrawalAmount;
}
