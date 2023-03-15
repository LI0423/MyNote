package com.video.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.RCRewardConfigDO;
import com.video.entity.RCWithdrawalConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RCWithdrawalConfigMapper extends BaseMapper<RCWithdrawalConfigDO> {

    List<RCWithdrawalConfigDO> queryList(@Param("pkg") String pkg, @Param("tokenList") List<String> tokenList);

    List<String> queryTokenList(@Param("pkg") String pkg, @Param("tokenList") List<String> tokenList);

}
