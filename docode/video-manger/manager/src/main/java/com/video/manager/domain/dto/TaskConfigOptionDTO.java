package com.video.manager.domain.dto;

import com.video.entity.TaskGroupEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskConfigOptionDTO {
    private Map<String, String> group;
    private Map<String, String> code;
    private Map<String, String> type;

}
