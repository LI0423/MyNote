package com.video.manager.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: video-manger
 * @description: manger
 * @author: laojiang
 * @create: 2020-09-03 18:09
 **/
@Data
public class ManagerDTO implements Serializable {

    private static final long serialVersionUID = 7577250426932994041L;
    private Long id;
    private String name;
    private String role;
    private List<String> permission;
    private List<String> apps;
    private Date lastModifyTime;
    /**
     * 最近修改人
     */
    private String lastModifyBy;
}
