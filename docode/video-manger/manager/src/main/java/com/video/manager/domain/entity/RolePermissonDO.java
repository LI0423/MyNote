package com.video.manager.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("role_permission")
public class RolePermissonDO {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String role;
    private String permission;
}
