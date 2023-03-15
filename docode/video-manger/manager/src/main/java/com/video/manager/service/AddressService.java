package com.video.manager.service;

import com.video.manager.domain.dto.ProvinceDTO;

import java.util.List;

/**
 * @program: mango
 * @description: address service
 * @author: laojiang
 * @create: 2020-10-21 15:47
 **/
public interface AddressService {

    /**
     * 返回省和城市和县的列表
     * @return
     */
    List<ProvinceDTO> findProvince();
}
