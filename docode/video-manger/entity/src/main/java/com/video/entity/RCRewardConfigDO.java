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
@TableName("rc_reward_config")
public class RCRewardConfigDO {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String pkg;
    private String token;
    private Float coefficient;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
    private CacheStatusEnum cacheStatus;


    @AllArgsConstructor
    @Getter
    public enum CacheStatusEnum {
        ALREADY_SYNC(1, "已同步"),
        WAIT_ADD(2, "待添加"),
        WAIT_UPDATE(3, "待更新");

        @EnumValue
        private Integer code;
        private String desc;
    }
}
