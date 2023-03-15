package com.video.manager.domain.common;

import com.video.manager.exception.ErrorCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 类名称：ResponseResult
 * ********************************
 * <p>
 * 类描述：通用返回结果模型
 *
 * @author
 * @date 下午12:59
 */
@Data
public class ResponseResult<T> implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7813356989387725160L;

    private Boolean success;

    private String code;

    private String message;

    private T result;

    /**
     * 成功
     *
     * @param result
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> success(T result) {
        ResponseResult<T> responseResult = new ResponseResult<>();

        responseResult.setSuccess(Boolean.TRUE);
        responseResult.setCode(ErrorCodeEnum.SUCCESS.getCode());
        responseResult.setResult(result);

        return responseResult;
    }

    /**
     * 失败
     *
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> failure(String code, String message) {
        ResponseResult<T> responseResult = new ResponseResult<>();

        responseResult.setSuccess(Boolean.FALSE);
        responseResult.setCode(code);
        responseResult.setMessage(message);

        return responseResult;
    }

    /**
     * 失败
     *
     * @param codeEnum
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> failure(ErrorCodeEnum codeEnum) {
        return failure(codeEnum.getCode(), codeEnum.getMessage());
    }


    /**
     * 自定义返回
     *
     * @param result
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> response(Boolean success, ErrorCodeEnum code, T result) {
        ResponseResult<T> responseResult = new ResponseResult<>();

        responseResult.setSuccess(success);
        responseResult.setCode(code.getCode());
        responseResult.setResult(result);

        return responseResult;
    }
}
