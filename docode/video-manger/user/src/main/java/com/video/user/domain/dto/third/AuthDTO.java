package com.video.user.domain.dto.third;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author admin
 */
@Data
public class AuthDTO implements Serializable {

    private static final long serialVersionUID = 6757200967839158425L;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private Long expiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("openid")
    private String openId;

    @JsonProperty("scope")
    private String scope;

}
