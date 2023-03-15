package com.video.manager.service;



import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.UserIncomeWithdrawalDTO;
import com.video.manager.domain.dto.UserIncomeWithdrawalQueryDTO;

import java.util.List;

public interface UserIncomeWithdrawalService {

    PageResult<List<UserIncomeWithdrawalDTO>> getAllInfo(PageQuery<UserIncomeWithdrawalQueryDTO> query);



}
