package com.video.oversea.user.exception;

import com.video.oversea.user.constant.BusinessHeaderConstants;
import com.video.oversea.user.domain.common.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 类名称：GlobalExceptionHandler
 * ********************************
 * <p>
 * 类描述：全局异常捕获处理器
 *
 * @author
 * @date 下午9:21
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 拦截业务类异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public ResponseResult businessExceptionHandle(BusinessException e) {
        log.error("捕捉到业务类异常：{}, {}", e.getCode(), e.getDesc());

        return ResponseResult.failure(e.getCode(), e.getMessage());
    }


    /**
     * 拦截运行时异常
     * @param e
     */
    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseResult runtimeExceptionHandle(RuntimeException e) {
        log.error("捕捉到运行时异常：", e);

        return ResponseResult.failure(ErrorCodeEnum.UNKNOWN_ERROR);
    }

    /**
     * 捕捉系统级异常
     * @param th
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public ResponseResult throwableHandle(Throwable th) {
        log.error("捕捉Throwable异常：", th);

        return ResponseResult.failure(
                ErrorCodeEnum.SYSTEM_ERROR.getCode(),
                th.getMessage());
    }


    /**
     * 捕捉参数校验异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseResult throwableHandle(MethodArgumentNotValidException e) {
        log.warn("参数错误：{}", e.getMessage());

        return ResponseResult.failure(ErrorCodeEnum.PARAM_MISSING);
    }

    /**
     * 捕捉参数校验异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseResult throwableHandle(HttpMessageNotReadableException e) {
        log.warn("参数缺失：{}", e.getMessage());

        return ResponseResult.failure(ErrorCodeEnum.PARAM_MISSING);
    }

    /**
     * 捕捉参数异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseResult throwableHandle(MissingServletRequestParameterException e) {
        log.warn("请求参数错误: {}", e.getMessage());

        return ResponseResult.failure(
                ErrorCodeEnum.PARAM_ERROR.getCode(),
                e.getMessage());
    }

    /**
     * 捕捉参数异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MissingRequestHeaderException.class)
    public ResponseResult throwableHandle(MissingRequestHeaderException e) {
        log.warn("请求头错误: {}", e.getMessage());
        // 缺失的是access token说明没登录
        if (BusinessHeaderConstants.ACCESS_TOKEN.equals(e.getHeaderName())) {
            return ResponseResult.failure(ErrorCodeEnum.USER_NOT_ADMIN);
        }
        return ResponseResult.failure(
                ErrorCodeEnum.HEADER_ERR.getCode(),
                e.getMessage());
    }
}
