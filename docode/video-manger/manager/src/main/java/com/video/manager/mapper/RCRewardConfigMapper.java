package com.video.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.RCRewardConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RCRewardConfigMapper extends BaseMapper<RCRewardConfigDO> {

    List<String> queryTokenList(@Param("pkg") String pkg, @Param("tokenList") List<String> tokenList);

    List<RCRewardConfigDO> queryList(@Param("pkg") String pkg, @Param("tokenList") List<String> tokenList);

}
