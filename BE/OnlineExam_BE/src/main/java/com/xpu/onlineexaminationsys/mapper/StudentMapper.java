package com.xpu.onlineexaminationsys.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xpu.onlineexaminationsys.bean.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {
    @Select("select * from student")
    IPage<Student> getAllStudents(Page<Student> studentPage);
    @Select("select * from student where studentName = #{queryStudentName}")
    Student getStudentByName(String queryStudentName);
    @Select("select * from student where sid = #{sid}")
    Student getStudentById(Integer sid);

    @Options(useGeneratedKeys = true,keyProperty = "sid")
    @Insert("insert into student(sid, username, password, studentName, role, email, idNumber, grade, institute, major, class_, sex, tel) " +
            "values (#{sid}, #{username}, #{password}, #{studentName}, #{role}, #{email}, #{idNumber}, #{grade}, #{institute}, #{major}, #{class_}, #{sex}, #{tel})")
    int addStudent(Student student);

    @Delete("delete from student where sid = #{sid}")
    int deleteStudentById(Integer sid);

    @Update("update student set sid=#{sid}, username=#{username}, password=#{password}, studentName=#{studentName}, email=#{email}, role=#{role}, " +
            "idNumber=#{idNumber}, grade=#{grade}, institute=#{institute}, major=#{major}, sex=#{sex}, class_=#{class_}, tel=#{tel} " +
            "where sid=#{sid}")
    int updateStudentById(Student student);

    @Select("select * from student where institute = #{institute} and major = #{major}")
    IPage<Student> getStudentListByMajor(Page<Student> studentPage, String institute, String major);

    @Select("select * from student where studentName = #{studentName} and institute = #{institute} and major = #{major}")
    Student getStudentByNameAndMajor(String studentName, String institute, String major);

    @Select("select * from student where username = #{username}")
    Student getStudentByUsername(String username);

    @Select("select sid from student where major = #{major}")
    List<Integer> getAllSidByMajor(String major);
}
