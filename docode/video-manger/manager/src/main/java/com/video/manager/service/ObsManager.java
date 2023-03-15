package com.video.manager.service;

import java.io.InputStream;
import java.util.List;

/**
 * @author laojiang
 * 上传的接口
 */
public interface ObsManager {

    /**
     * 从cdn删除文件
     *
     * @return
     */
    void deleteFile(String path);

    void batchDeleteFile(List<String> pathList);

    void upload();
}
