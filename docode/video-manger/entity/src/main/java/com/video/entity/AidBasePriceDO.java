package com.video.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author yifan 2021/12/22
 */
@Data
@TableName("aid_base_price")
public class AidBasePriceDO {
    private String aid;
    private Double ecpm;
    private Date dataTime;
}
