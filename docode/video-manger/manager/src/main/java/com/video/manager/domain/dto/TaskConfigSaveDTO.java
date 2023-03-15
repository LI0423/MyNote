package com.video.manager.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: video-manger
 * @description: task config dto
 * @author: laojiang
 * @create: 2020-09-03 14:27
 **/
@Data
public class TaskConfigSaveDTO implements Serializable {

    @JsonDeserialize(using = NumberDeserializers.LongDeserializer.class)
    private Long taskId;
    private String key;
    private String value;

}
