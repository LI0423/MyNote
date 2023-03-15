package com.video.oversea.payment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 类名称：ErrorCodeEnum
 * ********************************
 * <p>
 * 类描述：异常编码枚举
 *
 * @author
 * @date 2020/3/1 下午9:54
 */
@AllArgsConstructor
@Getter
public enum ErrorCodeEnum {

    // 2** 成功
    SUCCESS(200, "H20000", "操作成功"),

    // 3** 参数异常
    PARAM_ERROR(300, "H3000", "参数错误"),
    PARAM_MISSING(300, "H3001", "参数缺失"),
    HEADER_ERR(300, "H3011", "请求头缺失"),
    UPLOAD_FILE_CONTENT_MISSING(300, "H3009", "上传文件内容缺失"),

    USER_NOT_ADMIN(301, "H3009", "用户未登录"),
    USER_APP_MISMATCH(300, "H3006", "用户与app不匹配"), // 本来应该是301的...但是301客户端有特殊用处，就先这样吧
    ANONYMOUS_USER_HAS_BEEN_BIND(300, "H3012", "此游客账户已经被绑定"),
    USER_CANNOT_PERFORM_OP(300, "H3013", "此用户无法完成此操作"),
    USER_ALREADY_EXISTS(300, "H3014", "此用户已存在"),

    REPEAT_OPERATION(302, "H3002", "重复操作"),
    SKIP_PREVIOUS_TASK(302, "H3003", "跳过前一个奖励"),
    SIMULT_OPERATION(302, "H3004", "同时操作"),
    USER_IS_INVITED(302, "H3009", "被邀请的用户"),
    USER_IS_NOT_INVITED(302, "H3010", "非被邀请的用户"),
    APP_WITHDRAWAL_FUNCTION_CLOSED(303, "H3005", "app的提现功能关闭"),

    UPLOAD_FILE_TYPE_ERROR(304, "H3007", "上传文件类型错误"),
    UPLOAD_FILE_SIZE_MAX(304, "H3008", "上传文件内容过大"),

    // 4** 资源操作异常
    RESOURCE_NOT_FOUND(400, "H4001", "资源查询错误"),
    USER_INFO_NOT_FOUND(400, "H4004", "用户数据查询不到"),
    USER_AUTH_INFO_NOT_FOUND(400, "H4016", "用户认证数据查询不到"),
    VIDEO_DURATION_TASK_INFO_NOT_FOUND(400, "H4005", "观看视频任务数据查询不到"),
    NEW_HAND_TASK_INFO_NOT_FOUND(400, "H4006", "新手任务数据查询不到"),
    DAILY_TASK_INFO_NOT_FOUND(400, "H4007", "日常任务数据查询不到"),
    SINGIN_TASK_INFO_NOT_FOUND(400, "H4008", "签到任务数据查询不到"),
    APP_INFO_NOT_FOUND(400, "H4009", "app数据查询不到"),
    USER_DAILY_SINGIN_INFO_LIST_NOT_FOUND(400, "H4010", "用户每日签到数据查询不到"),
    USER_SCORE_INVALID_TIME_INFO_LIST_NOT_FOUND(400, "H4011", "用户积分过期列表数据查询不到"),
    TASK_INFO_NOT_FOUND(400, "H4017", "任务数据查询不到"),
    TASK_LIMIT(400, "H4018", "任务执行次数达到上限"),
    INVITED_USER_CREATE_TOO_EARLY(400, "H4020", "被邀请用户创建时间过早"),
    USER_HAS_BIND_PHONE(400, "H4023", "用户已经绑定了手机"),
    VERIFICATION_CODE_INVALID(400, "H4024", "验证码失效"),
    SERVER_SMS_CONFIG_NOT_FOUND(400, "H4025", "系统sms配置缺失"),
    PAY_BUSINESS_ERR(400, "H4026", "业务来源查询不到"),


    THIRD_AUTH_ERROR(401, "H4002", "第三方认证错误"),
    THIRD_RESOURCE_ERROR(401, "H4003", "第三方资源查询错误"),
    THIRD_PAY_ERROR(401, "H4013", "第三方支付接口错误"),
    THIRD_USER_INFO_ERROR(401, "H4015", "第三方用户数据获取错误"),

    WITHDRAWAL_MERCHANT_INSUFFICIENT_BALANCE(401, "H4020", "商户余额不足"),

    PAY_ORDER_NOT_FOUND(401, "H4023", "交易订单数据未找到"),

    BUSINESS_DATA_NOT_FOUND(402, "H4012", "业务数据获取不到"),
    NO_ACTION(402, "H4014", "没有进行有效操作"),
    TASK_CONDITIONS_NOT_MET(402, "H4019", "任务条件未达成"),
    RESOURCE_ERROR(402, "H4020", "资源错误"),
    ILLEGAL_OPERATION(402,"H4021","非法操作"),
    OP_TOO_FREQ(402, "H4022", "操作过于频繁"),

    // 5** 系统异常
    SYSTEM_ERROR(500, "H5000", "服务异常"),
    UNKNOWN_ERROR(501, "H5001", "未知异常"),
    TASK_CONF_ERROR(502, "H5002", "任务配置异常"),
    SERVICE_NOT_FOUND(502, "H503", "服务没有找到"),

    // 6** 谷歌支付
    PAY_GOOGLE_ORDER_INVALID(600, "H6000", "无效的订单"),
    PAY_GOOGLE_WRONG_ORDER_STATE(601, "H6010", "订单状态错误"),
    PAY_GOOGLE_ORDER_FINISHED(602, "H6020", "订单已完成"),
    PAY_GOOGLE_ALREADY_SUBSCRIBED(603, "H6030", "当前用户已订阅"),
    PAY_GOOGLE_SUBSCRIBED_ORDER_NOT_FOUND(604, "H6040", "无法找到订阅订单的信息"),
    PAY_GOOGLE_ORDER_CHECK_ERROR(605, "H6050", "谷歌订单校验失败"),
;


    /**
     * 错误编码
     */
    private int code;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 错误描述
     */
    private String desc;
}
