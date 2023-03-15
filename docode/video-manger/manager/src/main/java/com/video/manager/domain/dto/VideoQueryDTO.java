package com.video.manager.domain.dto;

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
public class VideoQueryDTO implements Serializable {
    private static final long serialVersionUID = -3669871515210832562L;

    private Long id;
    private String title;
    private String types;
    private ContentLevelEnum contentLevel;
    private Long categoryId;
    /**
     * 状态
     */
    private CrabVideoSandboxStatusEnum status;
    private Date startDate;
    private Date endDate;
}
