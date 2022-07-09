package com.xpu.onlineexaminationsys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xpu.onlineexaminationsys.bean.Exams;
import com.xpu.onlineexaminationsys.bean.Result;
import com.xpu.onlineexaminationsys.bean.Student;
import com.xpu.onlineexaminationsys.bean.StudentPaperList;
import com.xpu.onlineexaminationsys.service.ExamsService;
import com.xpu.onlineexaminationsys.service.StudentPaperListService;
import com.xpu.onlineexaminationsys.service.StudentService;
import com.xpu.onlineexaminationsys.service.TeacherService;
import com.xpu.onlineexaminationsys.util.CONST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Student Home 逻辑
 */
@Controller
public class StudentManageController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;
    @Autowired
    ExamsService examsService;
    @Autowired
    StudentPaperListService studentPaperListService;

    @ResponseBody
    @GetMapping(value = "/getSubmitStatus")
    public Result getSubmitStatus(@RequestParam(name = "username") String username,
                                  @RequestParam(name = "paperId") Integer paperId) {
        // get sid by username
        Student student = studentService.getStudentByUsername(username);
        Integer sid = student.getSid();
        Integer isSubmitted = studentPaperListService.getSubmitStatusBySidAndPaperId(sid, paperId);
        return Result.success(isSubmitted);
    }


    @ResponseBody
    @GetMapping(value = "/getTeacherByStudentUsername")
    public Result getTeacherByStudentUsername(@RequestParam(name = "username") String username) {
        Student student = studentService.getStudentByUsername(username);
        if(student == null) {
            return Result.error("no student");
        }
        return Result.success(student);
    }

    @ResponseBody
    @GetMapping(value = "/getExamListByInstituteAndMajor")
    public Result getExamListByInstituteAndMajor(@RequestParam(name = "institute") String institute,
                                        @RequestParam(name = "major") String major,
                                        @RequestParam(name = "pagenum")  Integer pagenum,
                                        @RequestParam(name = "pagesize") Integer pagesize) {
        Map<String, Object> jsonmap = new HashMap<>();
        Page<Exams> examsPage = new Page<>(pagenum, pagesize);
        IPage<Exams> allExam  = examsService.getExamListByInstituteAndMajor(examsPage, institute, major);
        List<Exams> examList = allExam.getRecords();
        jsonmap.put("examList", examList);
        jsonmap.put("total", allExam.getTotal());
        return Result.success(jsonmap);
    }
    @ResponseBody
    @GetMapping(value = "/getStudentByUsername")
    public Result getStudentByUsername(@RequestParam(name = "username") String username) {
        Student student = studentService.getStudentByUsername(username);
        if(student == null) {
            return Result.error("no student");
        }
        return Result.success(student);
    }
    @ResponseBody
    @GetMapping(value = "/getExamByPaperId")
    public Result getExamByPaperId(@RequestParam(name = "paperId") Integer paperId) {
        Exams exam = examsService.getExamByPaperId(paperId);
        if(exam == null) {
            return Result.error("no exam");
        }
        return Result.success(exam);
    }
}
