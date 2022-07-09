package com.xpu.onlineexaminationsys.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Admin {
    private String username;
    private String password;
    private String name;
    private String role;
    private String email;
    private String idNumber;
    private String sex;
    private String tel;
}
