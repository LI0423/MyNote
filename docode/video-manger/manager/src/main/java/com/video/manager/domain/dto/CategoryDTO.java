package com.video.manager.domain.dto;

import com.video.manager.domain.common.CategoryHiddenEnum;
import lombok.Data;

/**
 * @program: api
 * @description: category
 * @author: laojiang
 * @create: 2020-08-18 14:50
 **/
@Data
public class CategoryDTO {

    private Long id;
    private String name;
    private CategoryHiddenEnum hidden;
}
