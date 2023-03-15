package com.video.manager.service;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.ProWithdrawalDTO;
import com.video.manager.domain.dto.WithdrawalDTO;
import com.video.manager.domain.dto.WithdrawalQueryDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @program: video-manger
 * @description: Withdrawal
 * @author: laojiang
 * @create: 2020-09-03 14:42
 **/
public interface WithdrawalService {

    /**
     * 查询提现信息
     *
     * @param pageQuery
     * @return
     */
    PageResult<ProWithdrawalDTO> find(PageQuery<WithdrawalQueryDTO> pageQuery);

    List<Map<String, Object>> withdrawTimes();

    void excel(HttpServletResponse response, PageQuery<WithdrawalQueryDTO> pageQuery);
}
