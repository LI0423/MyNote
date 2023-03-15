package com.video.oversea.payment.exception;

import lombok.Getter;

/**
 * 类名称：BusinessException
 * ********************************
 * <p>
 * 类描述：业务类异常
 *
 * @author
 * @date 下午9:34
 */
public class BusinessException extends RuntimeException {

    /**
     * 异常编号
     */
    @Getter
    private final int code;

    /**
     * 异常详情
     */
    @Getter
    private final String desc;

    /**
     * 根据枚举构造业务类异常
     * @param error
     */
    public BusinessException(ErrorCodeEnum error) {
        super(error.getMessage());
        this.code = error.getCode();
        this.desc = error.getDesc();
    }

    public BusinessException(ErrorCodeEnum error, String desc) {
        super(error.getMessage());
        this.code = error.getCode();
        this.desc = desc;
    }

    /**
     * 自定义消息体构造业务类异常
     * @param error
     * @param message
     */
    public BusinessException(ErrorCodeEnum error, String message, String desc) {
        super(message);
        this.code = error.getCode();
        this.desc = desc;
    }

    /**
     * 根据异常构造业务类异常
     * @param error
     * @param cause
     */
    public BusinessException(ErrorCodeEnum error, String desc, Throwable cause) {
        super(cause);
        this.code = error.getCode();
        this.desc = desc;
    }

}
