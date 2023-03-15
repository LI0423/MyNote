package com.video.payment.constant;

public abstract class LocalCacheKey {

    public static final String PREFIX = "payment_lc:";

    public static final String WE_CHAT_PAY_HTTP_CLIENT_BUILDER_BY_KEY =
            PREFIX + "wechat_pay_http_client_build:mchid:%s:wcmpkey:%s:wcav3key:%s:wcmcsn:%s";

    private LocalCacheKey() {

    }
}
