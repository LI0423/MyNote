package com.video.payment.domain.dto.third;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.io.Serializable;

@Data
public class WeChatErrorMessageDTO {

    /**
     * 详细错误码, 例如:PARAM_ERROR
     */
    private String code;

    /**
     * 错误信息, 例如:参数错误
     */
    private String message;

    /**
     * 详细错误信息
     */
    private Detail detail;

    @Data
    public static class Detail implements Serializable {
        private static final long serialVersionUID = 1190706416089418783L;

        /**
         * 错误字段
         */
        private String field;

        /**
         * 错误值, 可能是各种对象, 故类型不能固定
         */
        private JsonNode value;

        /**
         * 具体错误原因
         */
        private String issue;

        /**
         * 错误位置
         */
        private String location;
    }
}
