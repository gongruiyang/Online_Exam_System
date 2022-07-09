package com.xpu.onlineexaminationsys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xpu.onlineexaminationsys.bean.Student;
import com.xpu.onlineexaminationsys.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentMapper studentMapper;

    public IPage<Student> getAllStudents(Page<Student> studentPage) {
        return studentMapper.getAllStudents(studentPage);
    }

    public Student getStudentByName(String queryStudentName) {
        return studentMapper.getStudentByName(queryStudentName);
    }

    public int addStudent(Student student) {
        return studentMapper.addStudent(student);
    }

    public Student getStudentById(Integer sid) {
        return studentMapper.getStudentById(sid);
    }

    public int updateStudentById(Student student) {
        return studentMapper.updateStudentById(student);
    }

    public int deleteStudentById(Integer sid) {
        return studentMapper.deleteStudentById(sid);
    }

    public IPage<Student> getStudentListByMajor(Page<Student> studentPage, String institute, String major) {
        return studentMapper.getStudentListByMajor(studentPage, institute, major);
    }

    public Student getStudentByNameAndMajor(String studentName, String institute, String major) {
        return studentMapper.getStudentByNameAndMajor(studentName, institute, major);
    }

    public Student getStudentByUsername(String username) {
        return studentMapper.getStudentByUsername(username);
    }

    public List<Integer> getAllSidByMajor(String major) {
        return studentMapper.getAllSidByMajor(major);
    }
}
