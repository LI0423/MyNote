package com.video.manager.domain.dto;

import lombok.Data;
import org.omg.PortableInterceptor.INACTIVE;

import java.io.Serializable;
import java.util.List;

/**
 * @program: video-manger
 * @description: ids
 * @author: laojiang
 * @create: 2020-09-04 11:51
 **/
@Data
public class VideoCategoryDTO implements Serializable {
    private static final long serialVersionUID = 528412114288397826L;

    private Integer categoryId;
    private List<Integer> categoryIds;
}
