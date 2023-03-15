package com.video.oversea.user.domain.dto.third.google;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoogleUserDTO implements Serializable {

    private static final long serialVersionUID = 676417390842119314L;
    private String googleUserId;
    private String name;
    private String email;
    private String picture;
}
