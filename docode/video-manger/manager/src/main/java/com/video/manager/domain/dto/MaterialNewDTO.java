package com.video.manager.domain.dto;

import com.video.manager.domain.common.ContentLevelEnum;
import com.video.manager.domain.common.CrabVideoSandboxStatusEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class MaterialNewDTO implements Serializable {
    private static final long serialVersionUID = -113800275857463377L;

    private ContentLevelEnum contentLevel;
    private String bsyImgUrl;
    private String bsyUrl;
    private Long id;
    private String loveCount;
    private String source;
    private String title;
    private Long utime;
    private String videoAuthor;
    private String bsyHeadUrl;
    private String videoTime;
    private String videoSize;
    private String watchCount;
    private String types;
    private String catId;
    private Integer status;
    private Integer categoryId;
}
