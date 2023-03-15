package com.video.manager.controller;

import com.video.manager.domain.dto.ManagerDTO;
import com.video.manager.service.ManagerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    @Autowired
    protected ManagerService managerService;

    protected ManagerDTO getManager(){
        Subject subject = SecurityUtils.getSubject();
        String email = subject.getPrincipal().toString();
        ManagerDTO managerDTO = managerService.getByEmail(email);
        return managerDTO;
    }

}
