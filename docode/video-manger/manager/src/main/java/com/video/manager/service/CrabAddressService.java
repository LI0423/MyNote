package com.video.manager.service;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.CrabAddressDTO;
import com.video.manager.domain.dto.CrabAddressQueryDTO;

import java.util.List;

/**
 * @program: video-manger
 * @description: CrabAddress
 * @author: laojiang
 * @create: 2020-09-03 15:41
 **/
public interface CrabAddressService {

    /**
     * 添加
     * @param crabAddressDTO
     * @return
     */
    Boolean insert(CrabAddressDTO crabAddressDTO);

    /**
     * 修改
     * @param crabAddressDTO
     * @param id
     * @return
     */
    Boolean update(CrabAddressDTO crabAddressDTO,Long id);

    /**
     * 删除
     * @param id
     * @return
     */
    Boolean delete(Long id);

    /**
     * 查询列表
     * @param pageQuery
     * @return
     */
    PageResult<List<CrabAddressDTO>> find(PageQuery<CrabAddressQueryDTO> pageQuery);
}
