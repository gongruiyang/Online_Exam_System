package com.xpu.onlineexaminationsys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xpu.onlineexaminationsys.bean.Teacher;
import com.xpu.onlineexaminationsys.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    TeacherMapper teacherMapper;

    public IPage<Teacher> getAllTeachers(Page<Teacher> page) {
        return teacherMapper.getAllTeachers(page);
    }

    public Teacher getTeacherByName(String queryTeacherName) {
        return teacherMapper.getTeacherByName(queryTeacherName);
    }

    public int addTeacher(Teacher teacher) {
        return teacherMapper.addTeacher(teacher);
    }

    public Teacher getTeacherById(Integer tid) {
        return teacherMapper.getTeacherById(tid);
    }

    public int updateTeacherById(Teacher teacher) {
        return teacherMapper.updateTeacherById(teacher);
    }

    public int deleteTeacherById(Integer tid) {
        return teacherMapper.deleteTeacherById(tid);
    }

    public Teacher getTeacherByUsername(String username) {
        return teacherMapper.getTeacherByUsername(username);
    }

    public Teacher getTeacherByInstituteAndMajor(String institute, String major) {
        return teacherMapper.getTeacherByInstituteAndMajor(institute, major);
    }
}
