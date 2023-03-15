package com.video.manager.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: api
 * @description: 视频查询
 * @author: laojiang
 * @create: 2020-08-18 15:03
 **/
@Data
public class MaterialQueryDTO implements Serializable {


    private static final long serialVersionUID = 7926560798886358019L;

    private Long id;
    private String title;
    private Long categoryId;
    private String types;
}
