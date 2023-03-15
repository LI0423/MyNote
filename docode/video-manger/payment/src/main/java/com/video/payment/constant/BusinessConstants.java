package com.video.payment.constant;

public abstract class BusinessConstants {

    /**
     * 微信随机字符串长度
     */
    public static final int WX_PREPAY_NONCESTR_LEN = 32;

    /**
     * 微信支付填充
     */
    public static final String WX_PACKAGE = "Sign=WXPay";

    /**
     * 支付订单默认标题
     */
    public static final String DEFAULT_PAYMENT_DESC = "订单付款";

    /**
     * 支付宝app支付产品码(固定)
     */
    public static final String ALI_PAY_TRADE_APP_PAY_PRODUCT_CODE = "QUICK_MSECURITY_PAY";

    public static final long RMB_YUAN_IN_CENTS = 100;

    private BusinessConstants() {

    }
}
