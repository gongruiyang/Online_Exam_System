package com.xpu.onlineexaminationsys.service;

import com.xpu.onlineexaminationsys.bean.Admin;
import com.xpu.onlineexaminationsys.bean.Student;
import com.xpu.onlineexaminationsys.bean.Teacher;
import com.xpu.onlineexaminationsys.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    LoginMapper loginMapper;

    public boolean adminLogin(String username, String password) {
        Admin curAdm = loginMapper.getAdminByUsername(username);
        if(curAdm != null && password.equals(curAdm.getPassword())){
            return true;
        }
        return false;
    }

    public Admin getAdmin(String username) {
        Admin curAdm = loginMapper.getAdminByUsername(username);
        return curAdm;
    }

    public boolean teacherLogin(String username, String password) {
        Teacher curTeacher = loginMapper.getTeacherByUsername(username);
        if(curTeacher != null && password.equals(curTeacher.getPassword())) {
            return true;
        }
        return false;
    }

    public boolean studentLogin(String username, String password) {
        Student curStudent = loginMapper.getStudentByUsername(username);
        if(curStudent != null && password.equals(curStudent.getPassword())) {
            return true;
        }
        return false;
    }
}
