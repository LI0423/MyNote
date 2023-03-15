package com.video.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.AppVersionConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author lh
 * @date 2022/1/14 3:41 下午
 */
@Mapper
public interface AppVersionConfigMapper extends BaseMapper<AppVersionConfigDO> {

    @Select("select * from app_version_config where app_id = #{appId} and version_code = #{version} limit 1")
    AppVersionConfigDO selectByAppIdAndVersion(@Param("appId")Long appId, @Param("version")String version);

    @Select("select * from app_version_config where app_id = #{appId}")
    List<AppVersionConfigDO> selectByAppId(@Param("appId")Long appId);
}
