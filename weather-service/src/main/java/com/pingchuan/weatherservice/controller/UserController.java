package com.pingchuan.weatherservice.controller;

import com.pingchuan.domain.Caller;
import com.pingchuan.weatherservice.service.CallerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private CallerService callerService;

    @RequestMapping("/getCurrentLoginName")
    public String getCurrentLoginName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        }
        return null;
    }

    @RequestMapping("/getError")
    public String getError(){
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Object exception = sra.getRequest().getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        if (exception == null){
            return null;
        }

        return "用户名或密码错误！";
    }

    @RequestMapping("/login")
    public String login(String username, String password){
        Caller caller = callerService.findOneByUsernameAndPassword(username, password);
        if (caller == null){
            return "用户名或密码错误！";
        }

        if ("管理员".equals(caller.getRole())){
            return "login success";
        }

        return "权限不足";
    }

}
