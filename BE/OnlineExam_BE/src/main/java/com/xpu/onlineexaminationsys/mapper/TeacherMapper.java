package com.xpu.onlineexaminationsys.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xpu.onlineexaminationsys.bean.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherMapper {
    @Select("select * from teacher")
    IPage<Teacher> getAllTeachers(Page<Teacher> page);
    @Select("select * from teacher where teacherName = #{queryTeacherName}")
    Teacher getTeacherByName(String queryTeacherName);
    @Select("select * from teacher where tid = #{tid}")
    Teacher getTeacherById(Integer tid);
    @Select("select * from teacher where username = #{username}")
    Teacher getTeacherByUsername(String username);

    @Options(useGeneratedKeys = true,keyProperty = "tid")
    @Insert("insert into teacher(username, password, teacherName, role, email, idNumber, institute, major, sex, title, tel) " +
            "values (#{username}, #{password}, #{teacherName}, #{role}, #{email}, #{idNumber}, #{institute}, #{major}, #{sex}, #{title}, #{tel})")
    int addTeacher(Teacher teacher);

    @Update("update teacher set username=#{username}, password=#{password}, teacherName=#{teacherName}, email=#{email}, role=#{role}, " +
            "idNumber=#{idNumber}, institute=#{institute}, major=#{major}, sex=#{sex}, title=#{title}, tel=#{tel} " +
            "where tid=#{tid}")
    int updateTeacherById(Teacher teacher);

    @Delete("delete from teacher where tid = #{tid}")
    int deleteTeacherById(Integer tid);

    @Select("select * from teacher where institute = #{institute} and major = #{major}")
    Teacher getTeacherByInstituteAndMajor(String institute, String major);
}
