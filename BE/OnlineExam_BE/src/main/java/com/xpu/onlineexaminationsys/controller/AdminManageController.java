package com.xpu.onlineexaminationsys.controller;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.regexp.internal.RE;
import com.xpu.onlineexaminationsys.bean.*;
import com.xpu.onlineexaminationsys.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Admin Home 逻辑
 */
@Controller
public class AdminManageController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;
    @Autowired
    ExamsService examsService;
    @Autowired
    SingleChoiceService singleChoiceService;
    @Autowired
    FillGapService fillGapService;


    // 教师信息增删改查
    @ResponseBody
    @PostMapping(value = "/addTeacher")
    public Result addTeacher(@RequestBody Teacher teacher) {
        int tid = teacherService.addTeacher(teacher);
        return Result.success(tid);
    }

    @ResponseBody
    @DeleteMapping(value = "/deleteTeacherById/{tid}")
    public Result deleteTeacherById(@PathVariable("tid") Integer tid) {
        int col = teacherService.deleteTeacherById(tid);
        return Result.success(col);
    }

    @ResponseBody
    @PostMapping(value = "/updateTeacherById")
    public Result updateTeacherById(@RequestBody Teacher teacher) {
        int col = teacherService.updateTeacherById(teacher);
        return Result.success(col);
    }

    @ResponseBody
    @GetMapping(value = "/showTeacherLists")
    public Result showTeacherLists(@RequestParam(name = "query")    String queryTeacherName,
                                   @RequestParam(name = "pagenum")  Integer pagenum,
                                   @RequestParam(name = "pagesize") Integer pagesize) {
        Map<String, Object> jsonmap = new HashMap<>();
        List<Teacher> teacherList = new ArrayList<>();
        if("".equals(queryTeacherName)) {
            Page<Teacher> teacherPage = new Page<>(pagenum,pagesize);
            IPage<Teacher> allTeachers = teacherService.getAllTeachers(teacherPage);
            teacherList = allTeachers.getRecords();
            jsonmap.put("total", allTeachers.getTotal());
        } else {
            Teacher curTeacher = teacherService.getTeacherByName(queryTeacherName);
            if(curTeacher != null) {
                teacherList.add(curTeacher);
                jsonmap.put("total", 1);
            } else {
                jsonmap.put("total", 0);
            }
        }
        jsonmap.put("teacherList", teacherList);
        return Result.success(jsonmap);
    }
    @ResponseBody
    @GetMapping(value = "/getTeacherById")
    public Result getTeacherById(@RequestParam(name = "tid") Integer tid) {
        Teacher teacher = teacherService.getTeacherById(tid);
        return Result.success(teacher);
    }


    // 学生信息增删改查
    @ResponseBody
    @GetMapping(value = "/showStudentLists")
    public Result showStudentLists(@RequestParam(name = "query")    String queryStudentName,
                                   @RequestParam(name = "pagenum")  Integer pagenum,
                                   @RequestParam(name = "pagesize") Integer pagesize) {
        Map<String, Object> jsonmap = new HashMap<>();
        List<Student> studentList = new ArrayList<>();
        if("".equals(queryStudentName)) {
            Page<Student> studentPage = new Page<>(pagenum, pagesize);
            IPage<Student> allStudent = studentService.getAllStudents(studentPage);
            studentList = allStudent.getRecords();
            jsonmap.put("total", allStudent.getTotal());
        } else {
            Student queryStudent = studentService.getStudentByName(queryStudentName);
            if(queryStudent != null) {
                studentList.add(queryStudent);
                jsonmap.put("total", 1);
            } else {
                jsonmap.put("total", 0);
            }
        }
        jsonmap.put("studentList", studentList);
        return Result.success(jsonmap);
    }

    @ResponseBody
    @GetMapping(value = "/getStudentById")
    public Result getStudentById(@RequestParam(value = "sid") Integer sid) {
        Student student = studentService.getStudentById(sid);
        return Result.success(student);
    }

    @ResponseBody
    @PostMapping(value = "/addStudent")
    public Result addStudent(@RequestBody Student student) {
        int sid = studentService.addStudent(student);
        return Result.success(sid);
    }

    @ResponseBody
    @PostMapping(value = "/updateStudentById")
    public Result updateStudentById(@RequestBody Student student) {
        int col = studentService.updateStudentById(student);
        return Result.success(col);
    }

    @ResponseBody
    @DeleteMapping(value = "/deleteStudentById/{sid}")
    public Result deleteStudentById(@PathVariable("sid") Integer sid) {
        int col = studentService.deleteStudentById(sid);
        return Result.success(col);
    }


    // 试题信息增删改查
    @ResponseBody
    @PostMapping(value = "/addSingleChoice")
    public Result addSingleChoice(@RequestBody QuestionSingleChoice singleChoice) {
        singleChoiceService.addSingleChoice(singleChoice);
        return Result.success("sucess");
    }
    @ResponseBody
    @PostMapping(value = "/addFillGap")
    public Result addFillGap(@RequestBody QuestionFillGap fillGap) {
        fillGapService.addFillGap(fillGap);
        return Result.success("sucess");
    }

    @ResponseBody
    @DeleteMapping(value = "/deleteSingleChoiceByQid/{quesId}")
    public Result deleteSingleChoiceByQid(@PathVariable("quesId") Integer quesId) {
        int col = singleChoiceService.deleteSingleChoiceByQid(quesId);
        return Result.success(col);
    }
    @ResponseBody
    @DeleteMapping(value = "/deleteFillGapByQid/{quesId}")
    public Result deleteFillGapByQid(@PathVariable("quesId") Integer quesId) {
        int col = fillGapService.deleteFillGapByQid(quesId);
        return Result.success(col);
    }

    @ResponseBody
    @PostMapping(value = "/updateSingleChoiceByQid")
    public Result updateSingleChoiceByQid(@RequestBody QuestionSingleChoice singleChoice) {
        int col = singleChoiceService.updateSingleChoiceByQid(singleChoice);
        return Result.success(col);
    }
    @ResponseBody
    @PostMapping(value = "/updateFillGapByQid")
    public Result updateFillGapByQid(@RequestBody QuestionFillGap fillGap) {
        int col = fillGapService.updateFillGapByQid(fillGap);
        return Result.success(col);
    }
    @ResponseBody
    @GetMapping(value = "/showSingleChoiceQuesLists")
    public Result showSingleChoiceQuesLists(@RequestParam(name = "query")  String querySubject,
                                            @RequestParam(name = "pagenum")  Integer pagenum,
                                            @RequestParam(name = "pagesize") Integer pagesize){
        Map<String, Object> jsonmap = new HashMap<>();
        List<QuestionSingleChoice> singleChoiceList  = new ArrayList<>();
        if("".equals(querySubject)) {
            Page<QuestionSingleChoice> singleChoicePage = new Page<>(pagenum, pagesize);
            IPage<QuestionSingleChoice> allSingleChoice = singleChoiceService.getAllSingleChoice(singleChoicePage);
            singleChoiceList = allSingleChoice.getRecords();
            jsonmap.put("total", allSingleChoice.getTotal());
        } else {
            singleChoiceList = singleChoiceService.getSingleChoiceBySubject(querySubject);
            jsonmap.put("total", singleChoiceList.size());
        }
        jsonmap.put("singleChoiceQuesList", singleChoiceList);
        return Result.success(jsonmap);
    }
    @ResponseBody
    @GetMapping(value = "/showFillGapQuesLists")
    public Result showFillGapQuesLists(@RequestParam(name = "query")  String querySubject,
                                       @RequestParam(name = "pagenum")  Integer pagenum,
                                       @RequestParam(name = "pagesize") Integer pagesize){
        Map<String, Object> jsonmap = new HashMap<>();
        List<QuestionFillGap> fillGapQuesList  = new ArrayList<>();
        if("".equals(querySubject)) {
            Page<QuestionFillGap> fillGapPage = new Page<>(pagenum, pagesize);
            IPage<QuestionFillGap> allFillGap = fillGapService.getAllFillGap(fillGapPage);
            fillGapQuesList = allFillGap.getRecords();
            jsonmap.put("total", allFillGap.getTotal());
        } else {
            fillGapQuesList = fillGapService.getFillGapBySubject(querySubject);
            jsonmap.put("total", fillGapQuesList.size());
        }
        jsonmap.put("fillGapQuesList", fillGapQuesList);
        return Result.success(jsonmap);
    }

    @ResponseBody
    @GetMapping(value = "/getSingleChoiceById")
    public Result getSingleChoiceById(@RequestParam(value = "quesId") Integer quesId) {
        QuestionSingleChoice singleChoice = singleChoiceService.getSingleChoiceById(quesId);
        return Result.success(singleChoice);
    }
    @ResponseBody
    @GetMapping(value = "/getFillGapById")
    public Result getFillGapById(@RequestParam(value = "quesId") Integer quesId) {
        QuestionFillGap fillGap = fillGapService.getFillGapById(quesId);
        return Result.success(fillGap);
    }

    // 考试安排
    @ResponseBody
    @GetMapping(value = "/showExamLists")
    public Result showExamLists(@RequestParam(name = "query")    String queryTeacherName,
                                @RequestParam(name = "pagenum")  Integer pagenum,
                                @RequestParam(name = "pagesize") Integer pagesize) {
        Map<String, Object> jsonmap = new HashMap<>();
        List<Exams> examList  = new ArrayList<>();
        if("".equals(queryTeacherName)) {
            Page<Exams> examsPage = new Page<>(pagenum, pagesize);
            IPage<Exams> allExams = examsService.getAllExams(examsPage);
            examList = allExams.getRecords();
            jsonmap.put("total", allExams.getTotal());
        } else {
            examList = examsService.getExamByTeacherName(queryTeacherName);
            jsonmap.put("total", examList.size());
        }
        jsonmap.put("examList", examList);
        return Result.success(jsonmap);
    }

}
