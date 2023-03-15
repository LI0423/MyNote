package com.video.manager.service;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.ManagerDTO;
import com.video.manager.domain.entity.ManagerDO;
import com.video.manager.domain.entity.RolePermissonDO;

import java.util.List;

/**
 * @program: api
 * @description: manager
 * @author: laojiang
 * @create: 2020-08-18 19:03
 **/
public interface ManagerService {

    /**
     * 查询管理信息
     *
     * @param email
     * @return
     */
    ManagerDTO getByEmail(String email);

    List<String> listApp();

    void update(String pkg);

    void update(ManagerDTO managerDTO);

    void add(ManagerDTO managerDTO);

    void delete(Long id);

    List<RolePermissonDO> listAuth();

    PageResult<List<ManagerDTO>> list(PageQuery<ManagerDTO> pageQuery);

}
