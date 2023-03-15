package com.video.manager.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program: mango
 * @description: AppAdConfig
 * @author: zsy
 * @create: 2020-11-18 15:54
 **/
@Data
@Accessors(chain = true)
public class RequestMaterialFeedDTO {
    private String crawl_url;
    private String channel;
}
