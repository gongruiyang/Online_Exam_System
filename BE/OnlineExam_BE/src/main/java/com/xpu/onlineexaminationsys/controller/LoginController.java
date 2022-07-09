package com.xpu.onlineexaminationsys.controller;

import com.xpu.onlineexaminationsys.bean.LoginBody;
import com.xpu.onlineexaminationsys.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 *  登录逻辑
 */
@Controller
public class LoginController {

    @Autowired
    LoginService loginService;


    @ResponseBody
    @PostMapping(value = "/login")
    public Map<String, String> login(@RequestBody LoginBody loginBody) {
        String username = loginBody.getUsername();
        String password = loginBody.getPassword();
        String role = loginBody.getRole();

        Map<String, String> jsonmap = new HashMap<>();
        if (username == null || password == null || role == null ||
                username.isEmpty() || password.isEmpty() || role.isEmpty()) {
            jsonmap.put("code", "404");
            return jsonmap;
        }

        if (role.equals("1")) {    // 超级权限
            boolean isAdmin = loginService.adminLogin(username, password);
            if(isAdmin) {
                jsonmap.put("code", "1");
            } else {
                jsonmap.put("code", "404");
            }
        } else if (role.equals("2")) {  // 教师
            boolean isTeacher = loginService.teacherLogin(username, password);
            if(isTeacher) {
                jsonmap.put("code", "2");
            } else {
                jsonmap.put("code", "404");
            }
        } else if (role.equals("3")) {  // 学生
            boolean isStudent = loginService.studentLogin(username, password);
            if(isStudent) {
                jsonmap.put("code", "3");
            } else {
                jsonmap.put("code", "404");
            }
        } else {
            jsonmap.put("code", "404");
        }
        jsonmap.put("data", username);

        return jsonmap;
    }
}
