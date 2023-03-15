/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.video.oversea.user.domain.common;

import lombok.Builder;
import lombok.Data;

/**
 * Created by jason on 2019/3/3.
 */
@Data
@Builder
public class CommonParams {
    String tk;
    String vn;
    String pkg;
    String lang;
    String ts;
    String vc;

}
