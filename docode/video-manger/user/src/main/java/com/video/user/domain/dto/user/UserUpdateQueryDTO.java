package com.video.user.domain.dto.user;

import com.video.entity.UserSexEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserUpdateQueryDTO {

    @Length(max = 30, message = "名字长度不能多于30位!")
    private String name;

    private UserSexEnum sex;

    private String avatar;
}
