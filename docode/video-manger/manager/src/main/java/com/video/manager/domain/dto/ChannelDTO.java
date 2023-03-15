package com.video.manager.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelDTO {
    private Integer id;
    private String channelName;
    private Integer sort;
    private List<Integer> categoryIds;
    private Date createTime;
    private String createBy;
    private Date lastModifyTime;
    private String lastModifyBy;
    private String appId;
    private int source;
    private String sourceChannelId;

}
