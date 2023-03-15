package com.video.manager.service;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.UserDTO;
import com.video.manager.domain.dto.UserQueryDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @program: video-manger
 * @description: user
 * @author: laojiang
 * @create: 2020-09-03 14:54
 **/
public interface UserService {

    /**
     * 查询用户列表
     * @param pageQuery
     * @return
     */
    PageResult<List<UserDTO>> find(PageQuery<UserQueryDTO> pageQuery);

    void excel(HttpServletResponse response, PageQuery<UserQueryDTO> pageQuery);

    void withdrawTimes();

    void clearUser(String accessToken);
}
