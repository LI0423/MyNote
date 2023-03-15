package com.video.oversea.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PkgEnum {

    MQ_IMAGE(1, "mq_image", "", "妙趣p图")
    ;

    private int code;

    private String aline;

    private String pkg;

    private String desc;

    public static PkgEnum getByPkg(String pkg) {
        for (PkgEnum value : values()) {
            if (value.pkg.equals(pkg)) {
                return value;
            }
        }
        return null;
    }
}
