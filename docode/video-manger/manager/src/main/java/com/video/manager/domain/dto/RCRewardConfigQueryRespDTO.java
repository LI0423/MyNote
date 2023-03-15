package com.video.manager.domain.dto;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.*;

import java.util.List;

@Data
@Builder
public class RCRewardConfigQueryRespDTO {
    private RCRewardConfigQueryRespDTO.Status status;
    private List<RCRewardConfigQueryRespConfigDTO> configList;

    @Getter
    @AllArgsConstructor
    public enum Status {
        SUCCESS(1, "成功"),
        PARTIAl_SUCCESS(2, "部分成功"),
        FAIL(0, "失败");

        @EnumValue
        private Integer code;
        private String desc;
    }

}
