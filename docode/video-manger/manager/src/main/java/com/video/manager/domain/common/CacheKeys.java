package com.video.manager.domain.common;

public class CacheKeys {
    /**
     * types:contentLevel:channelId list 每个key凌晨4点-5点之间失效 管理后台托管生命周期
     */
    public static final String VIDEOID_GET_PREFIX = "mango:loadVideoByCondition:%s";
    public static final String VIDEOID_GET_KEY = "mango:loadVideoByCondition:%s:%d:%s";
    public static final String VIDEOID_GET_KEY_BACK = "mango:loadVideoByConditionBack:%s:%d:%s";
    /**
     * string 每天凌晨3点-4点失效 管理后台托管生命周期
     */
    public static final String VIDEO_GET_KEY = "mango:loadVideoById:%s";

}
