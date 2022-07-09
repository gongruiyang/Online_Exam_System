package com.xpu.onlineexaminationsys.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginBody {
    private String username;
    private String password;
    private String role;
}
