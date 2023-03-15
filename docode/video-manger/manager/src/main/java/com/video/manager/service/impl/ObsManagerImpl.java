package com.video.manager.service.impl;

import com.obs.services.EcsObsCredentialsProvider;
import com.obs.services.ObsClient;
import com.obs.services.model.*;
import com.video.manager.service.ObsManager;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 华为云的OBS文件上传的实现
 *
 * @author laojiang
 */
@Service
public class ObsManagerImpl implements ObsManager {

    private static final Logger logger = LoggerFactory.getLogger(ObsManager.class);

    protected static final String endPoint = "https://obs.cn-south-1.myhuaweicloud.com";

    protected static final String bucketName = "material-feed";

    protected static final String url = "http://mv-res.xdplt.com";


    @Override
    public void deleteFile(String path) {
        try {
            try (ObsClient obsClient = new ObsClient(new EcsObsCredentialsProvider(), endPoint)) {

                DeleteObjectResult result = obsClient.deleteObject(bucketName, path);

                logger.info("deleteObjectResult={}", result);

            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void batchDeleteFile(List<String> pathList) {
        try {
            try (ObsClient obsClient = new ObsClient(new EcsObsCredentialsProvider(), endPoint)) {
                DeleteObjectsRequest deleteObjectRequest = new DeleteObjectsRequest(bucketName);

                for (String path : pathList) {
                    deleteObjectRequest.addKeyAndVersion(path.replace(url + "/" ,""));
                }
                DeleteObjectsResult result = obsClient.deleteObjects(deleteObjectRequest);

                logger.info("deleteObjectResult={}", result);

            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void upload() {

    }
}
