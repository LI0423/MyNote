package com.video.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.video.entity.AppDO;
import com.video.manager.domain.common.Constants;
import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.AppDTO;
import com.video.manager.domain.dto.AppQueryDTO;
import com.video.manager.domain.dto.ManagerDTO;
import com.video.manager.domain.dto.UserDTO;
import com.video.manager.domain.entity.ManagerDO;
import com.video.manager.domain.entity.RolePermissonDO;
import com.video.manager.mapper.AppMapper;
import com.video.manager.mapper.ManagerMapper;
import com.video.manager.mapper.RolePermissonMapper;
import com.video.manager.service.ManagerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: api
 * @description: manger service
 * @author: laojiang
 * @create: 2020-08-18 19:28
 **/
@Service
public class ManagerServiceImpl  extends ServiceImpl<ManagerMapper, ManagerDO> implements ManagerService {

    private ManagerMapper managerMapper;

    @Autowired
    public ManagerServiceImpl(ManagerMapper managerMapper) {
        this.managerMapper = managerMapper;
    }

    @Autowired
    RolePermissonMapper rolePermissonMapper;

    @Override
    public ManagerDTO getByEmail(String email) {

        QueryWrapper query = new QueryWrapper();
        query.eq("email", email);
        ManagerDO managerDO = managerMapper.selectOne(query);
        return DO2DTO(managerDO);
    }

    private ManagerDTO DO2DTO(ManagerDO managerDO) {
        ManagerDTO managerDTO = new ManagerDTO();
        BeanUtils.copyProperties(managerDO, managerDTO);
        if(StringUtils.isNotEmpty(managerDO.getPermission())){
            managerDTO.setPermission(Arrays.asList(managerDO.getPermission().split(",")));
        }else{
            managerDTO.setPermission(new ArrayList<>());
        }
        if(StringUtils.isNotEmpty(managerDO.getAppPkgs())){
            managerDTO.setApps(Arrays.asList(managerDO.getAppPkgs().split(",")));
        }else{
            managerDTO.setApps(new ArrayList<>());
        }
        return managerDTO;
    }

    private ManagerDO DTO2DO(ManagerDTO managerDTO) {
        ManagerDO managerDO = new ManagerDO();
        BeanUtils.copyProperties(managerDTO, managerDO);
        managerDO.setPermission(getPermission(managerDTO.getRole()));
        managerDO.setEmail(managerDO.getName());
        if(managerDTO.getApps() != null){
            managerDO.setAppPkgs(String.join(",", managerDTO.getApps()));
        }else{
            managerDO.setAppPkgs("");
        }
        return managerDO;
    }

    @Override
    public List<String> listApp() {
        ManagerDTO managerDTO = info();
        return managerDTO.getApps();
    }

    private ManagerDTO info() {
        Subject subject = SecurityUtils.getSubject();
        String email = subject.getPrincipal().toString();
        return getByEmail(email);
    }

    @Override
    public void update(String pkg) {
        ManagerDTO managerDTO = info();
        ManagerDO managerDO = DTO2DO(managerDTO);
        managerDO.setAppPkgs(pkg);
        managerMapper.updateById(managerDO);
    }

    @Override
    public void update(ManagerDTO managerDTO) {
        ManagerDO managerDO = DTO2DO(managerDTO);
        managerMapper.updateById(managerDO);
    }

    private String getPermission(String role) {
        if(StringUtils.isNotEmpty(role)){
            return rolePermissonMapper.selectOne(new QueryWrapper<RolePermissonDO>().eq("role", role)).getPermission();
        }
        return "default";
    }

    @Override
    public void add(ManagerDTO managerDTO) {
        ManagerDO managerDO = DTO2DO(managerDTO);
        managerMapper.insert(managerDO);
    }

    @Override
    public void delete(Long id) {
        removeById(id);
    }

    @Override
    public List<RolePermissonDO> listAuth() {
        return rolePermissonMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public PageResult<List<ManagerDTO>> list(PageQuery<ManagerDTO> pageQuery) {
        ManagerDO query = new ManagerDO();
        BeanUtils.copyProperties(pageQuery.getQuery(), query);
        QueryWrapper queryWrapper = new QueryWrapper(query);

        String orderBy = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, pageQuery.getOrderBy());
        if (Constants.ASCENDING.equals(pageQuery.getSequence())) {
            queryWrapper.orderByAsc(orderBy);
        }

        if (Constants.DESCENDING.equals(pageQuery.getSequence())) {
            queryWrapper.orderByDesc(orderBy);
        }

        Page page = new Page(pageQuery.getPageNo(), pageQuery.getPageSize());
        IPage<ManagerDO> managerDOIPage = managerMapper.selectPage(page, queryWrapper);

        PageResult pageResult = new PageResult();
        pageResult.setTotal(managerDOIPage.getTotal());

        List<ManagerDTO> managerDTOList = Optional
                .ofNullable(managerDOIPage.getRecords())
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(
                        managerDO -> {
                            return DO2DTO(managerDO);
                        }
                )
                .collect(Collectors.toList());

        pageResult.setLists(managerDTOList);


        return pageResult;
    }

}
