package com.video.manager.controller;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.common.ResponseResult;
import com.video.manager.domain.dto.VideoCategoryDTO;
import com.video.manager.domain.dto.MaterialDTO;
import com.video.manager.domain.dto.MaterialQueryDTO;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.service.MaterialService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: api
 * @description: material controller
 * @author: laojiang
 * @create: 2020-08-19 13:48
 **/
@RestController
@RequestMapping("/materials")
public class MaterialController {

    private MaterialService materialService;

    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }


    @PostMapping("/refresh/{videoId}")
    public ResponseResult refreshTime(@PathVariable Long videoId){
        boolean result=materialService.updateTime(videoId);

        if(result){
            return ResponseResult.success("更新时间成功");
        }else{
            return ResponseResult.failure(ErrorCodeEnum.UPDATE_FAILURE);
        }
    }



    @GetMapping("/portrait")
    public ResponseResult searchPortrait(@NotNull MaterialQueryDTO query,
                                         @NotNull Integer pageNum,
                                         @NotNull Integer pageSize,
                                         @Param("orderBy") String orderBy,
                                         @Param("sequence") String sequence){

        query.setTypes("20");
        PageResult<List<MaterialDTO>> result = getResult(query, pageNum, pageSize,orderBy,sequence);

        return ResponseResult.success(result);
    }

    @GetMapping("/landscape")
    public ResponseResult searchLandscape(@NotNull MaterialQueryDTO query,
                                          @NotNull Integer pageNum,
                                          @NotNull Integer pageSize,
                                          @Param("orderBy") String orderBy,
                                          @Param("sequence") String sequence){

        query.setTypes("10");
        PageResult<List<MaterialDTO>> result = getResult(query, pageNum, pageSize,orderBy,sequence);

        return ResponseResult.success(result);
    }

    private PageResult<List<MaterialDTO>> getResult(MaterialQueryDTO query,
                                                    Integer pageNum,
                                                    Integer pageSize,
                                                    String orderBy,
                                                    String sequence
                                                    ) {
        PageQuery<MaterialQueryDTO> pageQuery = new PageQuery<>();
        pageQuery.setPageNo(pageNum);
        pageQuery.setPageSize(pageSize);
        pageQuery.setQuery(query);
        pageQuery.setSequence(sequence);
        pageQuery.setOrderBy(orderBy);

        return materialService.query(pageQuery);
    }

}
