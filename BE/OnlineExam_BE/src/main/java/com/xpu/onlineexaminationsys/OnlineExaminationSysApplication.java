package com.xpu.onlineexaminationsys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.xpu.onlineexaminationsys.mapper")
@SpringBootApplication
public class OnlineExaminationSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineExaminationSysApplication.class, args);
    }
}
