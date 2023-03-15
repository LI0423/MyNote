package com.video.oversea.payment.util;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;

public class IdentifierGeneratorUtils {
    private static final DefaultIdentifierGenerator IDENTIFIER_GENERATOR = new DefaultIdentifierGenerator();

    public static Long nextId() {
        return IDENTIFIER_GENERATOR.nextId(null);
    }
}
