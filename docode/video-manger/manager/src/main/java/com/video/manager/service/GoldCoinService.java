package com.video.manager.service;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.GoldCoinDTO;
import com.video.manager.domain.dto.GoldCoinQueryDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @program: video-manger
 * @description: GoldCoin
 * @author: laojiang
 * @create: 2020-09-03 14:49
 **/
public interface GoldCoinService {

    /**
     * 查询金币流水
     * @param pageQuery
     * @return
     */
    PageResult<List<GoldCoinDTO>> find(PageQuery<GoldCoinQueryDTO> pageQuery);

    void excel(HttpServletResponse response, PageQuery<GoldCoinQueryDTO> pageQuery);
}
