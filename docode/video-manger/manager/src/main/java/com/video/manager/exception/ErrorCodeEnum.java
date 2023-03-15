package com.video.manager.exception;

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

    // 0*** 成功
    SUCCESS("0000", "操作成功"),
    SUCCESS_PARTIAL_FAIL("0001", "操作成功，部分失败"),


    // 1*** 参数异常
    PARAM_ERROR("1001", "参数异常"),
    PARAM_NULL("1002", "参数为空"),
    PARAM_FORMAT_ERROR("1003", "参数格式不正确"),
    PARAM_VALUE_ERROR("1004", "参数值不正确"),

    // 2*** 系统异常
    SYSTEM_ERROR("2001", "服务异常"),
    UNKNOWN_ERROR("2002", "未知异常"),

    //3***会话相关
    SESSION_INVALID("3001", "会话失效"),
    INSERT_FAILURE("3002", "新增失败"),
    UPDATE_FAILURE("3003", "更新失败"),
    DELETE_FAILURE("3004", "删除失败"),

    //业务
    USER_NOT_FOUND("3005", "用户没有找到"),
    USER_NOT_LOGIN("4040", "用户未登录"),
    CATEGORY_NOT_DELETE("3006", "分类下存在视频不能删除"),
    AD_CONFIG_DUPLICATE("3007","广告配置重复"),
    NAME_REPEAT("3008", "已有重复项！请检查！"),
    APP_FORBIDDEN("4001", "用户并无该产品权限！"),
    DATE_EMPTY("4006", "请至少选择一天的日期!"),
    CATEGORY_EMPTY("4007", "请打标签之后发布!"),
    VIDEO_REPEAT("4008", "线上已有重复数据!"),

    ADD_ERROR("5001","item重复");


;


    /**
     * 错误编码
     */
    private String code;

    /**
     * 错误描述
     */
    private String message;


}
