package com.video.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zhangshiyu
 * @date 2022/2/21 15:42
 */
@Data
@TableName("question_material")
public class QuestionMaterialDO {

    private Long id;

    /**
     * 视频地址
     */
    private String videoPath;

    /**
     * 问题
     */
    private String question;

    /**
     * 正确答案
     */
    private String rightAnswer;

    /**
     * 错误答案
     */
    private String wrongAnswer;
}
