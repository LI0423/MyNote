package com.video.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("aid_xmall")
@Data
public class AidXmallDO {

    private String aid;

    private Double ecpm;

    private Double cpc;

    private Date dataTime;

    private Double originEcpm;

    private Double originCpc;
}
