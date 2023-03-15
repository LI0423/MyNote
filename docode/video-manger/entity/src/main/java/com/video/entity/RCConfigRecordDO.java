package com.video.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("rc_config_record")
public class RCConfigRecordDO {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String pkg;
    private String token;
    private RCConfigTypeEnum type;
    private String target;
    private LocalDateTime createTime;

    @AllArgsConstructor
    @Getter
    public enum RCConfigTypeEnum {

        WITHDRAWAL_ADD(101, "提现配置增加"),
        WITHDRAWAL_UPDATE(102, "提现配置更新"),
        REWARD_ADD(201, "奖励配置增加"),
        REWARD_UPDATE(202, "奖励配置更新");

        @EnumValue
        private Integer code;
        private String desc;
    }

}
