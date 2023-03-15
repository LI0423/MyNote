package com.video.oversea.user.cache;

import com.video.oversea.user.domain.dto.user.BaseUserDTO;

import java.util.Date;

public interface UserCache {

    /**
     * 通过id从缓存中获取BaseUserDTO对象
     * @param id 用户id
     * @return BaseUserDTO对象
     */
    BaseUserDTO getBaseUserDTOById(Long id);

    /**
     * 将BaseUserDTO对象设置到缓存中
     * @param id 用户id
     * @param baseUserDTO BaseUserDTO对象
     */
    void setBaseUserDTOByIdExpire(Long id, BaseUserDTO baseUserDTO);

    /**
     * 删除缓存
     * @param id 用户id
     */
    void delBaseUserDTOById(Long id);
}
