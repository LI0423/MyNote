package com.video.manager.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.video.manager.domain.common.ContentLevelEnum;
import com.video.manager.domain.common.CrabVideoSandboxStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: video-manger
 * @description: CrabVideoSandbox
 * @author: laojiang
 * @create: 2020-09-03 16:09
 **/
@Data
public class CrabVideoSandboxQueryDTO implements Serializable {
    private static final long serialVersionUID = -3669871515210832562L;
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
    private String types;
}
