package com.video.oversea.user.domain.dto.user;

import com.video.entity.UserSexEnum;
import lombok.Data;

@Data
public class BaseUserDTO implements Cloneable {

    private Long userId;
    private String name;
    private UserSexEnum sex;
    private String avatar;
    private String phoneNumber;

    public BaseUserDTO copy() throws CloneNotSupportedException {
        return (BaseUserDTO) clone();
    }
}
