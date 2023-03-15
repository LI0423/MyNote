package com.video.manager.domain.dto;

import com.video.manager.domain.common.ContentLevelEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: api
 * @description: 视频
 * @author: laojiang
 * @create: 2020-08-18 15:09
 **/
@Data
public class MaterialDTO implements Serializable {

    private static final long serialVersionUID = -113800275857463377L;

    private ContentLevelEnum contentLevel;
    private String bsyImgUrl;
    private String bsyUrl;
    private Long id;
    private String loveCount;
    private String source;
    private String title;
    private Long utime;
    private String videoTime;
    private String watchCount;
}
