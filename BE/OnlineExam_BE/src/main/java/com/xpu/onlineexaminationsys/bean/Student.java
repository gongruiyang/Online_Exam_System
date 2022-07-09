package com.xpu.onlineexaminationsys.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student {
    private Integer sid;
    private String username;
    private String password;
    private String studentName;
    private String role;
    private String email;
    private String idNumber;
    private String grade;
    private String institute;
    private String major;
    private String class_;
    private String sex;
    private String tel;
}
