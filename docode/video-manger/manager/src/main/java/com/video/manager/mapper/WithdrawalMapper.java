package com.video.manager.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.video.entity.WithdrawalDO;
import com.video.manager.domain.dto.WithdrawalDTO;
import com.video.manager.domain.dto.WithdrawalQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @program: video-manger
 * @description: 提现
 * @author: laojiang
 * @create: 2020-09-03 11:34
 **/
@Mapper
public interface WithdrawalMapper extends BaseMapper<WithdrawalDO> {
    @Select("select IFNULL(sum(amount),0) from withdrawal ${ew.customSqlSegment}")
    Long getTotalAmount(@Param(Constants.WRAPPER) QueryWrapper wrapper);
}
