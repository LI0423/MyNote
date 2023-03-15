package com.video.manager.service;

import com.video.entity.BlackUserDO;
import com.video.manager.domain.dto.BlackUserDTO;
import com.video.manager.domain.dto.BlackUserViewDTO;
import com.video.manager.domain.entity.BlackUserViewDO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;

/**
 * 黑名单用户service
 */
public interface BlackUserService {

    /**
     * 查询黑名单用户记录
     * @param token token
     * @return
     */
    BlackUserDTO find(String token);

    /**
     * 添加token到黑名单
     * @param blackUserDTO
     */
    void add(BlackUserDTO blackUserDTO);

    /**
     * 批量添加token到黑名单
     * @param blackUserDTOList
     */
    void addBatch(List<BlackUserDTO> blackUserDTOList);

    /**
     * 查询列表
     * @return
     */
    List<BlackUserViewDTO> list();

    static BlackUserDTO convertTo(BlackUserDO blackUserDO) {

        if (Objects.isNull(blackUserDO)) {
            return null;
        }

        BlackUserDTO blackUserDTO = new BlackUserDTO();

        BeanUtils.copyProperties(blackUserDO, blackUserDTO);

        blackUserDTO.setDesc(blackUserDO.getBDesc());

        return blackUserDTO;
    }

    static BlackUserViewDTO convertTo(BlackUserViewDO blackUserViewDO) {

        if (Objects.isNull(blackUserViewDO)) {
            return null;
        }

        BlackUserViewDTO blackUserViewDTO = new BlackUserViewDTO();

        BeanUtils.copyProperties(blackUserViewDO, blackUserViewDTO);

        return blackUserViewDTO;
    }
}
