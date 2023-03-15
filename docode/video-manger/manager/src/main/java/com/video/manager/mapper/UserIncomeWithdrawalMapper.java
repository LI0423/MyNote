package com.video.manager.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.UserIncomeWithdrawalDO;
import org.apache.ibatis.annotations.Mapper;

@DS("mysql_3")
@Mapper
public interface UserIncomeWithdrawalMapper extends BaseMapper<UserIncomeWithdrawalDO> {
}
