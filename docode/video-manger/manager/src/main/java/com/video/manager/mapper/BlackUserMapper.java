package com.video.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.entity.BlackUserDO;
import com.video.manager.domain.dto.BlackUserViewDTO;
import com.video.manager.domain.entity.BlackUserViewDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BlackUserMapper extends BaseMapper<BlackUserDO> {

    /**
     * 查询一个black user信息
     * @param tokens
     * @return
     */
    @Select("<script>" +
                "select * from black_user where token in " +
                "<foreach item='item' index='index' collection='tokens' open='(' separator=', ' close=')'>" +
                    "#{item}" +
                "</foreach>" +
                "limit 1" +
            "</script>")
    BlackUserDO selectOneInToken(@Param("tokens") List<String> tokens);

    @Select("select u.id userId, bu.token, u.name name, a.pkg pkg, a.name pkgName, bu.create_time blackCreateTime" +
            " from black_user bu " +
            " left join userid_token_mapping utm " +
            " on bu.token = utm.token " +
            " left join user u " +
            " on utm.user_id = u.id " +
            " left join app a " +
            " on u.app_id = a.id " +
            " order by pkg limit 10")
    List<BlackUserViewDO> selectBlackUserViewList();
}
