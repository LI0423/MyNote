package com.video.manager.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.jasig.cas.client.util.AssertionHolder;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: api
 * @description:
 * @author: laojiang
 * @create: 2020-08-20 14:24
 **/
@Controller
@RequestMapping("/")
@Slf4j
public class IndexController {

    @Value("${config.cas.mainPage}")
    private String mainPage;

    @Value("${config.cas.casServerLogout}")
    private String casServerLogout;

    @Value("${config.cas.casServerLogin}")
    private String casServerLogin;

    @GetMapping
    public void index(HttpServletResponse response) throws IOException {

        Assertion assertion = AssertionHolder.getAssertion();
        // 未登录，需要重新登录
        log.info("assertion={}",assertion);

        response.sendRedirect("/static/index.html");
    }


    @GetMapping("/logout")
    public void logout(HttpServletResponse response) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.stop();
        response.sendRedirect(casServerLogout + "?service=" +
                casServerLogin + "?service=" + mainPage);
    }

    @GetMapping("/error")
    public void error(HttpServletResponse response){
        try {
            response.sendRedirect("/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
