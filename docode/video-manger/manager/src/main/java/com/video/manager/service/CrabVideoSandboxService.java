package com.video.manager.service;

import com.video.manager.domain.common.CrabVideoSandboxStatusEnum;
import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.CrabVideoSandboxDTO;
import com.video.manager.domain.dto.CrabVideoSandboxQueryDTO;
import com.video.manager.domain.dto.MaterialNewDTO;
import com.video.manager.domain.entity.MaterialNewDO;

import java.util.List;

/**
 * @program: video-manger
 * @description: CrabVideoSandbox  service
 * @author: laojiang
 * @create: 2020-09-03 16:13
 **/
public interface CrabVideoSandboxService {

    /**
     * 查询列表
     * @param pageQuery
     * @return
     */
    PageResult<List<MaterialNewDTO>> find(PageQuery<MaterialNewDTO> pageQuery);

    /**
     * 添加分类
     * @param categoryId
     * @param videoId
     * @return
     */
    Boolean addCategory(Integer categoryId, Long videoId);
    /**
     * 删除分类
     * @param categoryId
     * @param videoId
     * @return
     */
    Boolean deleteCategory(Integer categoryId, Long videoId);

    /**
     * 修改青少年
     * @param id
     * @param contentLevel
     * @return
     */
    Boolean updateContentLevel(Long id, Integer contentLevel);

    /**
     * 发布到线上表
     * @param
     * @return
     */
    void publish(MaterialNewDO materialNewDO);
    /**
     * 发布到线上表
     * @param id,status
     * @return
     */
    MaterialNewDO findMaterialNewById(Long id);
    boolean updateMaterialNewById(Long id, CrabVideoSandboxStatusEnum status);
    List<Integer> getCategoryIdList(Long videoId);
 }
