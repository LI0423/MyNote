package com.video.manager.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.video.entity.UserDO;
import com.video.manager.domain.dto.GoldCoinDTO;
import com.video.manager.domain.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @program: video-manger
 * @description: user mapper
 * @author: laojiang
 * @create: 2020-09-03 11:33
 **/
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
    @Select("select a.id,name,sex,avatar,max(a.number) number,max(score) score,max(withdrawal_times) withdrawal_times,sum(b.amount) withdrawal_amount " +
            "from user a " +
            "left join withdrawal b on a.id = b.user_id  ${ew.customSqlSegment}")
    IPage<UserDTO> getUserDto(@Param(Constants.WRAPPER) Wrapper wrapper, Page<GoldCoinDTO> page);
}
