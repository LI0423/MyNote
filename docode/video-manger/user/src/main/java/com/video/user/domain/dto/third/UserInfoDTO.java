package com.video.user.domain.dto.third;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.video.entity.UserSexEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author admin
 */
@Data
public class UserInfoDTO implements Serializable {

    private static final long serialVersionUID = -507722852508751376L;

    @JsonProperty("openid")
    private String openId;

    @JsonProperty("nickname")
    private String nickName;

    @JsonProperty("sex")
    private UserSexEnum sex;

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
