package com.video.manager.controller;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.ResponseResult;
import com.video.manager.domain.dto.DataConfigRecordQueryDTO;
import com.video.manager.service.DataConfigRecordService;
import com.video.manager.util.DateSeperateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/system/dict/record")
public class DataConfigRecordController {

    @Autowired
    DataConfigRecordService dataConfigRecordService;

    @GetMapping("/list")
    public ResponseResult getAllInfo(@RequestParam(value = "operator",required = false)String operator,
                                     @RequestParam(value = "sItem",required = false)String sItem,
                                     @RequestParam(value = "tItem",required = false)String tItem,
                                     @RequestParam(value = "createTiem",required = false)String createTime,
                                     @RequestParam(value = "tStatus",required = false)Integer tStatus,
                                     @RequestParam("pageNum")Integer pageNum,
                                     @RequestParam("pageSize")Integer pageSize,
                                     @RequestParam("orderBy")String orderBy,
                                     @RequestParam("sequence")String sequence){

        DataConfigRecordQueryDTO dataConfigRecordQueryDTO = new DataConfigRecordQueryDTO();
        dataConfigRecordQueryDTO.setOperator(operator);
        dataConfigRecordQueryDTO.setSItem(sItem);
        dataConfigRecordQueryDTO.setTItem(tItem);
        dataConfigRecordQueryDTO.setTStatus(tStatus);
        dataConfigRecordQueryDTO.setCreateTime(createTime);

        return ResponseResult.success(dataConfigRecordService.getAllInfo(getResult(dataConfigRecordQueryDTO,pageNum,pageSize,orderBy,sequence)));

    }

    private PageQuery<DataConfigRecordQueryDTO> getResult(DataConfigRecordQueryDTO query,
                                                          Integer pageNum,
                                                          Integer pageSize,
                                                          String orderBy,
                                                          String sequence
    ) {
        PageQuery<DataConfigRecordQueryDTO> pageQuery = new PageQuery<>();
        pageQuery.setPageNo(pageNum);
        pageQuery.setPageSize(pageSize);
        pageQuery.setQuery(query);
        pageQuery.setSequence(sequence);
        pageQuery.setOrderBy(orderBy);

        if (pageQuery.getQuery().getCreateTime()!=null){
            return DateSeperateUtil.pageQueryInit(pageQuery);
        }else {
            return pageQuery;
        }
    }

}
