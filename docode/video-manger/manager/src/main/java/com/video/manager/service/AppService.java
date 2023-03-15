package com.video.manager.service;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @program: video-manger
 * @description: app服务
 * @author: laojiang
 * @create: 2020-09-03 14:08
 **/
public interface AppService {

    /**
     * 添加app
     * @param appDTO
     * @return
     */
    Boolean insert(AppDTO appDTO);

    /**
     * 上传 weChatApiCert
     * @param  fileByte
     * @return
     */
    UploadDTO upload(MultipartFile fileByte);

    /**
     * 修改app
     * @param appDTO
     * @param id
     * @return
     */
    Boolean update(AppDTO appDTO,Long id);

    /**
     * 删除app
     * @param id
     * @return
     */
    Boolean delete(Long id);

    /**
     * 查询全部的app的名字和编号
     * @return
     */
    List<AppIdAndNameDTO> findIdAndName();

    /**
     * 查询app列表
     * @param pageQuery
     * @return
     */
    PageResult<List<AppDTO>> find(PageQuery<AppQueryDTO> pageQuery);

    /**
     * 查询一个app
     * @param id
     * @return
     */
    AppDTO findOne(Long id);

    /**
     * 更新所有包的广告位信息
     * @return
     */
    void updateSid();

    List<Long> listAppIds();

    Boolean addAppVersionConfig(AppVersionConfigDTO appVersionConfigDTO);

    Boolean updateAppVersionConfig(AppVersionConfigDTO appVersionConfigDTO);

    Boolean deleteAppVersionConfig(Long id);

    List<AppVersionConfigDTO> findAppVersionConfigList(Long appId);

    List<MediationDTO> getAidList(String pkg);
}
