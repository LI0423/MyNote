package com.video.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.AppAdConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @program: mango
 * @description: AppAdConfig mapper
 * @author: laojiang
 * @create: 2020-10-21 15:31
 **/
@Mapper
public interface AppAdConfigMapper extends BaseMapper<AppAdConfigDO> {


    /**
     *   查询个数
     * @param appId
     * @param appVn
     * @param sid
     * @param provinceCode
     * @param cityCode
     * @return
     */
    @Select("select count(*) from app_ad_config a " +
            "left join app_ad_config_sid b on(a.id=b.app_ad_config_id) " +
            " left join app_ad_config_app_vn c on(a.id=c.app_ad_config_id) " +
            "where a.app_id=#{appId} and vn=#{appVn} and sid=#{sid} " +
            "and province_code=#{provinceCode} and city_code=#{cityCode}")
    Long findAppVnAndSid(@Param("appId") Long appId,@Param("appVn") String appVn,
                         @Param("sid") String sid, @Param("provinceCode") String provinceCode,
                         @Param("cityCode") String cityCode);

    /**
     * 查询个数
     * @param appId
     * @param appVn
     * @param sid
     * @param provinceCode
     * @param cityCode
     * @param id
     * @return
     */
    @Select("select count(*) from app_ad_config a " +
            "left join app_ad_config_sid b on(a.id=b.app_ad_config_id) " +
            "left join app_ad_config_app_vn c on(a.id=c.app_ad_config_id) " +
            "where a.app_id=#{appId} and vn=#{appVn} and sid=#{sid} " +
            "and province_code=#{provinceCode} and city_code=#{cityCode} and a.id<>#{id}")
    Long findAppVnAndSidAndId(@Param("appId") Long appId,@Param("appVn") String appVn,
                              @Param("sid") String sid, @Param("provinceCode") String provinceCode,
                              @Param("cityCode") String cityCode,@Param("id") Long id);


}
