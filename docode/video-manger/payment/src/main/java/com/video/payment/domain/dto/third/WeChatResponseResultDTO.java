package com.video.payment.domain.dto.third;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 包装微信返回内容
 * @author wangwei
 * @param <T>
 */
@Data
@AllArgsConstructor
public class WeChatResponseResultDTO<T> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7813356989387725160L;

    private T result;

    private WeChatErrorMessageDTO message;

    public static <T> WeChatResponseResultDTO<T> success(T result) {
        return new WeChatResponseResultDTO<>(result, null);
    }

    public static <T> WeChatResponseResultDTO<T> failure(WeChatErrorMessageDTO messagge, Class<T> resultClass) {
        return new WeChatResponseResultDTO<>(null, messagge);
    }
}
