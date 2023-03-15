package com.video.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.manager.domain.entity.DataConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface DataConfigMapper extends BaseMapper<DataConfigDO> {

    Boolean updateStatus(@Param("id") Long id,
                         @Param("modifier") String modifier);

    Boolean ifRepeat(@Param("item")String item,@Param("id")Long id);

}
