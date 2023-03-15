package com.video.manager.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.video.entity.TaskGroupEnum;
import com.video.entity.TaskTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: video-manger
 * @description: task dto
 * @author: laojiang
 * @create: 2020-09-03 14:15
 **/
@Data
public class TaskQueryDTO implements Serializable {
    private static final long serialVersionUID = -1459731781481342302L;
    private Long appId;
    private String name;
    private Integer taskGroup;
}
