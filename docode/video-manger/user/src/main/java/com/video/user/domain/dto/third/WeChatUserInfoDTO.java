package com.video.user.domain.dto.third;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeChatUserInfoDTO {

    @JsonProperty("openid")
    private String openId;

    @JsonProperty("nickname")
    private String nickName;

    @JsonProperty("sex")
    private String sex;

    @JsonProperty("province")
    private String province;

    @JsonProperty("city")
    private String city;

    @JsonProperty("country")
    private String country;

    @JsonProperty("headimgurl")
    private String headImgUrl;

    @JsonProperty("privilege")
    private List privilege;

    @JsonProperty("unionid")
    private String unionId;
}
