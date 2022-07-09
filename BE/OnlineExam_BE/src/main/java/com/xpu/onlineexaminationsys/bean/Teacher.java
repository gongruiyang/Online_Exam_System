package com.xpu.onlineexaminationsys.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Teacher {
    private Integer tid;
    private String username;
    private String password;
    private String teacherName;
    private String role;
    private String email;
    private String idNumber;
    private String institute;
    private String major;
    private String sex;
    private String title;
    private String tel;
}
