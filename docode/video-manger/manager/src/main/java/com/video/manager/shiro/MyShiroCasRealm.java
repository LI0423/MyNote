package com.video.manager.shiro;

import com.google.common.collect.Lists;
import com.video.manager.domain.dto.ManagerDTO;
import com.video.manager.domain.entity.ManagerDO;
import com.video.manager.service.ManagerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 安全数据源
 * @author laojiang
 */
public class MyShiroCasRealm extends CasRealm{

    private static final Logger logger = LoggerFactory.getLogger(MyShiroCasRealm.class);

    @Autowired
    private ManagerService managerService;

    @PostConstruct
    public void initProperty(){
    }

    /**
     * 权限认证，为当前登录的Subject授予角色和权限
    */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        String username = principalCollection.getPrimaryPrincipal().toString();
        ManagerDTO manager = managerService.getByEmail(username);
        if(manager == null){
            return authorizationInfo;
        }
        String role = manager.getRole();
        if (StringUtils.isBlank(role)) {
            return authorizationInfo;
        }
        authorizationInfo.addRole(role);
        logger.info("role:{}",role);

        // 获取权限
        if(manager.getPermission() != null){
            logger.info("permissions:{}",manager.getPermission());
            for (String permission : manager.getPermission()) {
                authorizationInfo.addStringPermission(permission);
            }
        }

        return authorizationInfo;
    }

}

