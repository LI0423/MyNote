package com.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@TableName("medal_state")
public class MedalStateDO {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long userId;

    private MedalTypeEnum type;

    private MedalStateEnum state;

    private Integer conditionValue;

    private Date modifyTime;
}
