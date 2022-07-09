package com.xpu.onlineexaminationsys.mapper;

import com.xpu.onlineexaminationsys.bean.Admin;
import com.xpu.onlineexaminationsys.bean.Student;
import com.xpu.onlineexaminationsys.bean.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {
    @Select("select * from admin where username = #{username}")
    Admin getAdminByUsername(String username);
    @Select("select * from teacher where username = #{username}")
    Teacher getTeacherByUsername(String username);
    @Select("select * from student where username = #{username}")
    Student getStudentByUsername(String username);
}
