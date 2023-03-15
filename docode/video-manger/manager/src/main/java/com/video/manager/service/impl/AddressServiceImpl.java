package com.video.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.video.manager.domain.dto.CityDTO;
import com.video.manager.domain.dto.ProvinceDTO;
import com.video.manager.domain.entity.CityDO;
import com.video.manager.domain.entity.ProvinceDO;
import com.video.manager.mapper.CityMapper;
import com.video.manager.mapper.CountryMapper;
import com.video.manager.mapper.ProvinceMapper;
import com.video.manager.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: mango
 * @description:
 * @author: laojiang
 * @create: 2020-10-22 14:37
 **/
@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    private ProvinceMapper provinceMapper;
    private CityMapper cityMapper;
    private CountryMapper countryMapper;

    @Autowired
    public AddressServiceImpl(ProvinceMapper provinceMapper,
                              CityMapper cityMapper,
                              CountryMapper countryMapper) {
        this.provinceMapper = provinceMapper;
        this.cityMapper = cityMapper;
        this.countryMapper = countryMapper;
    }

    @Override
    public List<ProvinceDTO> findProvince() {

        List<ProvinceDO> provinceDOList=provinceMapper.selectList(new QueryWrapper<ProvinceDO>()
                .orderByAsc("code"));

        List<ProvinceDTO> provinceDTOList= Optional
                .ofNullable(provinceDOList)
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(provinceDO -> {
                    ProvinceDTO provinceDTO=new ProvinceDTO();
                    BeanUtils.copyProperties(provinceDO,provinceDTO);

                    List<CityDO> cityDOList=cityMapper.selectList(new QueryWrapper<CityDO>()
                            .eq("province_id",provinceDO.getId()));
                    List<CityDTO> cityDTOList=new ArrayList<>();
                    for(CityDO cityDO:cityDOList){
                        CityDTO cityDTO=new CityDTO();
                        BeanUtils.copyProperties(cityDO,cityDTO);
                        cityDTOList.add(cityDTO);
                    }
                    provinceDTO.setChildren(cityDTOList);

                    return provinceDTO;
                })
                .collect(Collectors.toList());

        return provinceDTOList;
    }
}
