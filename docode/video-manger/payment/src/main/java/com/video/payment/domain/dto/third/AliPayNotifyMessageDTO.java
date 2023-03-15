package com.video.payment.domain.dto.third;

import com.alipay.api.internal.mapping.ApiField;
import lombok.Data;

import java.util.Date;

@Data
public class AliPayNotifyMessageDTO {

    @ApiField("notify_type")
    private String notifyType;

    /**
     * 通知id
     */
    @ApiField("notify_id")
    private String notifyId;

    @ApiField("notify_time")
    private Date notifyTime;

    /**
     * 第三方appId
     */
    @ApiField("app_id")
    private String appId;

    /**
     * 编码格式
     */
    @ApiField("charset")
    private String charset;

    /**
     * 调用的接口版本
     */
    @ApiField("version")
    private String version;

    /**
     * 签名类型
     */
    @ApiField("sign_type")
    private String signType;

    /**
     * 签名
     */
    @ApiField("sign")
    private String sign;

    /**
     * 支付宝交易号
     */
    @ApiField("trade_no")
    private String tradeNo;

    /**
     * 订单id
     */
    @ApiField("out_trade_no")
    private String outTradeNo;

    /**
     * 商户业务id,主要是退款通知中返回退款申请的流水号
     */
    @ApiField("out_biz_no")
    private String outBizNo;

    /**
     * 买家支付宝账号的 PID
     */
    @ApiField("buyer_id")
    private String buyerId;

    @ApiField("buyer_logon_id")
    private String buyerLogonId;

    @ApiField("seller_id")
    private String sellerId;

    @ApiField("seller_email")
    private String sellerEmail;

    /**
     * 通知类型
     */
    @ApiField("trade_status")
    private String tradeStatus;

    /**
     * 总金额, 单位:元
     */
    @ApiField("total_amount")
    private String totalAmount;

    /**
     * 商家在交易中实际收到的款项，单位为人民币（元）
     */
    @ApiField("receipt_amount")
    private String receiptAmount;

    /**
     * 用户在交易中支付的可开发票的金额
     */
    @ApiField("invoice_amount")
    private String invoiceAmount;

    /**
     * 用户在交易中支付的金额
     */
    @ApiField("buyer_pay_amount")
    private String buyerPayAmount;

    /**
     * 使用集分宝支付的金额，单位为人民币（元）
     */
    @ApiField("point_amount")
    private String pointAmount;

    /**
     * 退款通知中，返回总退款金额，单位为人民币（元）
     */
    @ApiField("refund_fee")
    private String refundFee;

    /**
     * 商品的标题/交易标题/订单标题/订单关键字等，是请求时对应的参数，在通知中原样传回
     */
    @ApiField("subject")
    private String subject;

    /**
     * 该笔订单的备注、描述、明细等。对应请求时的 body 参数，在通知中原样传回
     */
    @ApiField("body")
    private String body;

    /**
     * 该笔订单的创建时间。格式为 yyyy-MM-dd HH:mm:ss
     */
    @ApiField("gmt_create")
    private Date gmtCreate;

    /**
     * 该笔订单买家付款的时间。格式为 yyyy-MM-dd HH:mm:ss
     */
    @ApiField("gmt_payment")
    private String gmtPayment;

    /**
     * 该笔订单的退款时间。格式为 yyyy-MM-dd HH:mm:ss.S
     */
    @ApiField("gmt_refund")
    private String gmtRefund;

    /**
     * 该笔订单的结束时间。格式为 yyyy-MM-dd HH:mm:ss
     */
    @ApiField("gmt_close")
    private String gmtClose;

    /**
     * 支付成功的各个渠道金额信息
     */
    @ApiField("fund_bill_list")
    private String fundBillList;

    /**
     * 公共回传参数，如果请求时传递了该参数，则返回给商家时会在异步通知中原样传回该参数。本参数必须进行UrlEncode之后才可传入
     */
    @ApiField("passback_params")
    private String passbackParams;

    /**
     * 本订单支付时所使用的所有优惠券信息
     */
    @ApiField("voucher_detail_list")
    private String voucherDetailList;
}
