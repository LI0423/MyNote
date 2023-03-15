package com.video.manager.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: video-manger
 * @description: CrabAddressDTO
 * @author: laojiang
 * @create: 2020-09-07 14:26
 **/
@Data
public class CrabAddressQueryDTO implements Serializable {

    private static final long serialVersionUID = 3232738201885846457L;

    private String source;
    private String url;
    private Date startDate;
    private Date endDate;
}
