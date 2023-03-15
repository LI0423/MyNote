package com.video.user.service;

import com.video.user.domain.dto.app.AppDTO;

/**
 * @author laojjiang
 */
public interface AppService {

    /**
     * 根据pkg得到app
     * @param pkg
     * @return
     */
    AppDTO find(String pkg);

    /**
     * 根据id得到app
     * @param id
     * @return
     */
    AppDTO find(Long id);
}
