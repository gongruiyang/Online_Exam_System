package com.xpu.onlineexaminationsys.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xpu.onlineexaminationsys.bean.*;
import com.xpu.onlineexaminationsys.listener.ExcelListener;
import com.xpu.onlineexaminationsys.service.*;
import com.xpu.onlineexaminationsys.util.RANDOM;
import com.xpu.onlineexaminationsys.util.WordUtil;
import com.xpu.onlineexaminationsys.vobean.CorrectRateVO;
import com.xpu.onlineexaminationsys.vobean.StudentPaperVO;
import com.xpu.onlineexaminationsys.vobean.StudentScoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xpu.onlineexaminationsys.util.CONST;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  Teacher Home 逻辑
 */
@Controller
public class TeacherManageController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;
    @Autowired
    ExamsService examsService;
    @Autowired
    PaperService paperService;
    @Autowired
    SingleChoiceService singleChoiceService;
    @Autowired
    FillGapService fillGapService;
    @Autowired
    JudgeService judgeService;
    @Autowired
    ShortAnsService shortAnsService;
    @Autowired
    StudentPaperListService studentPaperListService;

    @ResponseBody
    @GetMapping(value = "/getCorrectRate")
    public Result getCorrectRate(@RequestParam(value = "paperId") Integer paperId) {
        // paperId -> quesId list
        List<Integer> singleQidList     = paperService.getTotalNumsByPaperId(paperId, CONST.TYPE_SINGLE_CHOICE);
        List<Integer> fillGapQidList    = paperService.getTotalNumsByPaperId(paperId, CONST.TYPE_FILL_GAP);
        List<Integer> judgeQidList      = paperService.getTotalNumsByPaperId(paperId, CONST.TYPE_JUDGE);
        List<Integer> shortAnsQidList   = paperService.getTotalNumsByPaperId(paperId, CONST.TYPE_SHORT_ANS);

        // paperId -> student answer
        List<StudentPaperList> studentPaperLists = studentPaperListService.getAllByPaperId(paperId);

        // quesId -> correct rate
        List<CorrectRateVO> correctRateList = new ArrayList<>();
        int id = 1;
        for(int i = 0; i < singleQidList.size(); i ++, id ++) {
            // qid -> rightAns
            Integer qid = singleQidList.get(i);
            QuestionSingleChoice singleChoice = singleChoiceService.getSingleChoiceById(qid);
            String rightAns = singleChoice.getRightAns();

            // get rightCount & correct rate
            int rightCount = 0;
            for(int j = 0; j < studentPaperLists.size(); j ++) {
                StudentPaperList  studentPaper = studentPaperLists.get(j);
                if(1 == studentPaper.getIsSubmitted()) {
                    String singleStr = studentPaper.getAnsSingleChoice();
                    String[] split = singleStr.split("\\|");
                    if(rightAns.equals(split[i])) {
                        rightCount ++;
                    }
                }
            }
            float correctRate = 0.0f;
            if(studentPaperLists.size() != 0) {
                correctRate = (float) rightCount / studentPaperLists.size();
            }
            correctRateList.add(new CorrectRateVO(id, rightCount, correctRate));
        }
        for(int i = 0; i < fillGapQidList.size(); i ++, id ++) {
            Integer qid = fillGapQidList.get(i);
            QuestionFillGap fillGap = fillGapService.getFillGapById(qid);
            String rightAns = fillGap.getRightAns();

            int rightCount = 0;
            for(int j = 0; j < studentPaperLists.size(); j ++) {
                StudentPaperList  studentPaper = studentPaperLists.get(j);
                if(1 == studentPaper.getIsSubmitted()) {
                    String fillStr = studentPaper.getAnsFillGap();
                    String[] split = fillStr.split("\\|");
                    if(rightAns.equals(split[i])) {
                        rightCount ++;
                    }
                }
            }
            float correctRate = 0.0f;
            if(studentPaperLists.size() != 0) {
                correctRate = (float) rightCount / studentPaperLists.size();
            }
            correctRateList.add(new CorrectRateVO(id, rightCount, correctRate));
        }
        for(int i = 0; i < judgeQidList.size(); i ++, id ++) {
            Integer qid = judgeQidList.get(i);
            QuestionJudge judge = judgeService.getJudgeById(qid);
            String rightAns = judge.getRightAns();

            int rightCount = 0;
            for(int j = 0; j < studentPaperLists.size(); j ++) {
                StudentPaperList  studentPaper = studentPaperLists.get(j);
                if(1 == studentPaper.getIsSubmitted()) {
                    String judgeStr = studentPaper.getAnsJudge();
                    String[] split = judgeStr.split("\\|");
                    if(rightAns.equals(split[i])) {
                        rightCount ++;
                    }
                }
            }
            float correctRate = 0.0f;
            if(studentPaperLists.size() != 0) {
                correctRate = (float) rightCount / studentPaperLists.size();
            }
            correctRateList.add(new CorrectRateVO(id, rightCount, correctRate));
        }
        for(int i = 0; i < shortAnsQidList.size(); i ++, id ++) {
            int rightCount = 0;
            float correctRate = 0.0f;
            correctRateList.add(new CorrectRateVO(id, rightCount, correctRate));
        }
        return Result.success(correctRateList);
    }

    @ResponseBody
    @GetMapping(value = "/downloadStudentPaper")
    public String downloadStudentPaper(@RequestParam(value = "paperId") Integer paperId,
                                       @RequestParam(value = "sid") Integer sid,
                                       HttpServletResponse response) {
        // 1. 取出所有试题
        List<QuestionSingleChoice> choiceList   = new ArrayList<>();
        List<QuestionFillGap> fillGapList       = new ArrayList<>();
        List<QuestionJudge> judgeList           = new ArrayList<>();
        List<QuestionShortAns> shortAnsList     = new ArrayList<>();
        List<Integer> choiceQIDs    = paperService.getTotalNumsByPaperId(paperId, CONST.TYPE_SINGLE_CHOICE);
        List<Integer> fillGapQIDs   = paperService.getTotalNumsByPaperId(paperId, CONST.TYPE_FILL_GAP);
        List<Integer> judgeQIDs     = paperService.getTotalNumsByPaperId(paperId, CONST.TYPE_JUDGE);
        List<Integer> shortAnsQIDs  = paperService.getTotalNumsByPaperId(paperId, CONST.TYPE_SHORT_ANS);
        for (Integer qid:choiceQIDs) {
            QuestionSingleChoice singleChoice = singleChoiceService.getSingleChoiceById(qid);
            if(singleChoice != null) { choiceList.add(singleChoice); }
        }
        for (Integer qid:fillGapQIDs) {
            QuestionFillGap fillGap = fillGapService.getFillGapById(qid);
            if(fillGap != null) { fillGapList.add(fillGap); }
        }
        for(Integer qid : judgeQIDs) {
            QuestionJudge judge = judgeService.getJudgeById(qid);
            if(judge != null) { judgeList.add(judge); }
        }
        for(Integer qid : shortAnsQIDs) {
            QuestionShortAns shortAns = shortAnsService.getShortAnsById(qid);
            if(shortAns != null) { shortAnsList.add(shortAns); }
        }
        // 2. 取出考试信息
        Exams exams = examsService.getExamByPaperId(paperId);
        // 3. 获取学生答案
        StudentPaperList studentPaperList = studentPaperListService.getOneStudenPaper(paperId, sid);
        String stuSingleStr = studentPaperList.getAnsSingleChoice();
        String stuFillStr   = studentPaperList.getAnsFillGap();
        String stuJudgeStr  = studentPaperList.getAnsJudge();
        String stuShortStr  = studentPaperList.getAnsShortAns();
        String[] stuSingleArr   = stuSingleStr.split("\\|");
        String[] stuFillArr     = stuFillStr.split("\\|");
        String[] stuJudgeArr    = stuJudgeStr.split("\\|");
        String[] stuShortArr    = stuShortStr.split("\\|");
        // 4. 取出考生信息
        Student student = studentService.getStudentById(sid);
        // 5. 导出至 word
        String filePath = "";
        try {
            filePath = WordUtil.export2word(choiceList, fillGapList, judgeList, shortAnsList, exams, student, studentPaperList, stuSingleArr, stuFillArr, stuJudgeArr, stuShortArr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(filePath);
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName()));
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            return "下载失败";
        }
        return "下载成功";
    }
    @ResponseBody
    @GetMapping(value = "/getPaperListByTid")
    public Result getPaperListByTid(@RequestParam(name = "tid")  Integer tid) {
        Map<String, Object> jsonmap = new HashMap<>();
        List<Integer> paperIdLists = examsService.getPaperIdListByTid(tid);
        List<StudentPaperVO> studentPaperLists = new ArrayList<>();
        for(Integer paperId : paperIdLists) {
            List<StudentPaperList> list = studentPaperListService.getAllByPaperId(paperId);
            Exams exam = examsService.getExamByPaperId(paperId);
            String examName = exam.getGrade() + exam.getInstitute() + exam.getMajor() + exam.getSubject();
            String examTime = exam.getExamTime();
            for(StudentPaperList studentPaperList : list) {
                Integer sid = studentPaperList.getSid();
                Student student = studentService.getStudentById(sid);
                String studentName = student.getStudentName();

                StudentPaperVO studentPaper = new StudentPaperVO();
                studentPaper.setSid(sid);
                studentPaper.setStudentName(studentName);
                studentPaper.setExamName(examName);
                studentPaper.setExamTime(examTime);
                studentPaper.setPaperId(paperId);
                studentPaper.setTotalSingleChoiceScore(studentPaperList.getTotalSingleChoiceScore());
                studentPaper.setTotalFillGapScore(studentPaperList.getTotalFillGapScore());
                studentPaper.setTotalJudgeScore(studentPaperList.getTotalJudgeScore());
                studentPaper.setTotalShortAnsScore(studentPaperList.getTotalShortAnsScore());
                studentPaper.setTotalScore(studentPaperList.getTotalScore());
                studentPaper.setIsSubmitted(studentPaperList.getIsSubmitted());
                studentPaperLists.add(studentPaper);
            }
        }
        jsonmap.put("paperList", studentPaperLists);
        return Result.success(jsonmap);
    }

    @ResponseBody
    @GetMapping(value = "/downloadScore")
    public String downloadStudentScore(@RequestParam(value = "paperId") Integer paperId, HttpServletResponse response) {
        // get filename and filepath
        Exams exam = examsService.getExamByPaperId(paperId);
        String filename = exam.getMajor() + exam.getSubject() + "成绩单" + ".xlsx";
        String realPath = "download/";
        File folder = new File(realPath);
        if(!folder.exists()) {
            folder.mkdirs();
        }
        String filePath = realPath + filename;
        // create an Excel
        File file = new File(filePath);
        ExcelWriterBuilder excelWrite = EasyExcel.write(file, StudentScoreVO.class);
        ExcelWriterSheetBuilder sheet = excelWrite.sheet("学生成绩单");
        // build VO bean: student paper and score
        List<StudentScoreVO> voList = new ArrayList<>();
        List<StudentPaperList> studentPaperLists = studentPaperListService.getAllByPaperId(paperId);
        Integer id = 1;
        for(StudentPaperList s : studentPaperLists) {
            StudentScoreVO obj = new StudentScoreVO();
            Student student = studentService.getStudentById(s.getSid());
            obj.setId(id);
            obj.setSid(student.getSid());
            obj.setStudentName(student.getStudentName());
            obj.setMajorAndClass(student.getMajor());
            obj.setTotalScore(s.getTotalScore());
            obj.setTotalSingleChoiceScore(s.getTotalSingleChoiceScore());
            obj.setTotalFillGapScore(s.getTotalFillGapScore());
            obj.setTotalJudgeScore(s.getTotalJudgeScore());
            obj.setTotalShortAnsScore(s.getTotalShortAnsScore());
            voList.add(obj);
            id ++;
        }
        sheet.doWrite(voList);
        // set response
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename));

        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            return "下载失败";
        }
        return "下载成功";
    }

    @ResponseBody
    @PostMapping(value = "/uploadQuestion")
    public Result downLoadStudentPaper(@RequestParam(value = "MultipartFile") MultipartFile file, HttpServletRequest request) {
        String originNam = file.getOriginalFilename();
        if(!originNam.endsWith(".xlsx")) {
            return Result.error("文件类型不匹配");
        }
        String realPath = this.getClass().getResource("/").getPath() + "/upload/";
        File folder = new File(realPath);
        if(!folder.exists()) {
            folder.mkdirs();
        }
        String fileName = "question.xlsx";
        try {
            file.transferTo(new File(folder, fileName));
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/upload/" + fileName;
            System.out.println(url);
            return Result.success(url);
        } catch (IOException e) {
            return Result.error(e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping(value = "/uploadExamInfor")
    public Result uploadExamInfor(@RequestBody Exams exams) {
        String filename = this.getClass().getResource("/").getPath() + "/upload/question.xlsx";
        // get paperId
        Integer paperId = paperService.getMaxPaperId();
        if(paperId == null) {
            paperId = 0;
        } else {
            paperId++;
        }
        exams.setPaperId(paperId);
        // save question to DB
        Integer tid = exams.getTid();
        ExcelListener excelListener = new ExcelListener(tid);
        ExcelReaderBuilder excelReader = EasyExcel.read(filename, QuestionSingleChoice.class, excelListener);
        ExcelReaderSheetBuilder sheet = excelReader.sheet("单选题");
        sheet.doRead();
        excelReader = EasyExcel.read(filename, QuestionFillGap.class, excelListener);
        sheet = excelReader.sheet("填空题");
        sheet.doRead();
        excelReader = EasyExcel.read(filename, QuestionJudge.class, excelListener);
        sheet = excelReader.sheet("判断题");
        sheet.doRead();
        excelReader = EasyExcel.read(filename, QuestionShortAns.class, excelListener);
        sheet = excelReader.sheet("简答题");
        sheet.doRead();
        // get all category questions quesId
        List<Integer> singleQidList = excelListener.singleQidList;
        List<Integer> fillQidList = excelListener.fillQidList;
        List<Integer> judgeQidList = excelListener.judgeQidList;
        List<Integer> shortQidList = excelListener.shortQidList;
        // get score
        int totalSingleChoiceScore  = 0;
        int totalFillGapScore       = 0;
        int totalJudgeScore         = 0;
        int totalShortAnsScore      = 0;
        List<Integer> singleScoreList = new ArrayList<>();
        List<Integer> fillScoreList = new ArrayList<>();
        List<Integer> judgeScoreList = new ArrayList<>();
        List<Integer> shorScoreList = new ArrayList<>();
        for(Integer qid : singleQidList) {
            QuestionSingleChoice q = singleChoiceService.getSingleChoiceById(qid);
            totalSingleChoiceScore += q.getScore();
            singleScoreList.add(q.getScore());
        }
        for(Integer qid : fillQidList) {
            QuestionFillGap q = fillGapService.getFillGapById(qid);
            totalFillGapScore += q.getScore();
            fillScoreList.add(q.getScore());
        }
        for(Integer qid : judgeQidList) {
            QuestionJudge q = judgeService.getJudgeById(qid);
            totalJudgeScore += q.getScore();
            judgeScoreList.add(q.getScore());
        }
        for(Integer qid : shortQidList) {
            QuestionShortAns q = shortAnsService.getShortAnsById(qid);
            totalShortAnsScore += q.getScore();
            shorScoreList.add(q.getScore());
        }
        int totalScore = totalSingleChoiceScore + totalFillGapScore + totalJudgeScore + totalShortAnsScore;
        exams.setTotalSingleChoiceScore(totalSingleChoiceScore);
        exams.setTotalFillGapScore(totalFillGapScore);
        exams.setTotalJudgeScore(totalJudgeScore);
        exams.setTotalShortAnsScore(totalShortAnsScore);
        exams.setTotalScore(totalScore);
        // save exam
        examsService.addExam(exams);
        // save paper
        Integer examId = examsService.getExamIdByPaperId(paperId);
        // get all category question nums
        int singleChoiceNums    = singleQidList.size();
        int fillGapNums         = fillQidList.size();
        int judgeNums           = judgeQidList.size();
        int shortAnsNums        = shortQidList.size();
        // save paper
        int quesType, score, quesId;
        if(singleChoiceNums > 0) {
            quesType = CONST.TYPE_SINGLE_CHOICE;
            for(int i = 0; i < singleChoiceNums; i ++) {
                score = singleScoreList.get(i);
                quesId = singleQidList.get(i);
                paperService.addQues(paperId, examId, quesId, quesType, score);
            }
        }
        if(fillGapNums > 0) {
            quesType = CONST.TYPE_FILL_GAP;
            for(int i = 0; i < fillGapNums; i ++) {
                score = fillScoreList.get(i);
                quesId = fillQidList.get(i);
                paperService.addQues(paperId, examId, quesId, quesType, score);
            }
        }
        if(judgeNums > 0) {
            quesType = CONST.TYPE_JUDGE;
            for(int i = 0; i < judgeNums; i ++) {
                score = judgeScoreList.get(i);
                quesId = judgeQidList.get(i);
                paperService.addQues(paperId, examId, quesId, quesType, score);
            }
        }
        if(shortAnsNums > 0) {
            quesType = CONST.TYPE_SHORT_ANS;
            for(int i = 0; i < shortAnsNums; i ++) {
                score = shorScoreList.get(i);
                quesId = shortQidList.get(i);
                paperService.addQues(paperId, examId, quesId, quesType, score);
            }
        }
        // save student paper list
        String major = exams.getMajor();
        List<Integer> sidList = studentService.getAllSidByMajor(major);
        StudentPaperList studentPaperList = new StudentPaperList();
        studentPaperList.setExamId(examId);
        studentPaperList.setPaperId(paperId);
        studentPaperList.setIsSubmitted(0);
        for (Integer sid : sidList) {
            studentPaperList.setSid(sid);
            studentPaperListService.addStudentPaperList(studentPaperList);
        }
        return Result.success(paperId);
    }

    @ResponseBody
    @GetMapping(value = "/getExamStudentList")
    public Result getExamStudentList(@RequestParam(name = "paperId") int paperId) {
        Map<String, Object> jsonmap = new HashMap<>();
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<StudentPaperList> studentPaperLists = studentPaperListService.getAllByPaperId(paperId);
        for(StudentPaperList s : studentPaperLists) {
            Integer sid = s.getSid();
            Integer isSubmitted = s.getIsSubmitted();
            Student student = studentService.getStudentById(sid);
            if(student == null) continue;
            String studentName = student.getStudentName();
            Map<String, Object> data = new HashMap<>();
            data.put("studenName", studentName);
            data.put("isSubmitted", isSubmitted);
            dataList.add(data);
        }
        jsonmap.put("list", dataList);
        jsonmap.put("nums", dataList.size());
        return Result.success(jsonmap);
    }

    @ResponseBody
    @GetMapping(value = "/getPaperByPaperId")
    public Result getPaperByPapaerId(@RequestParam(name = "paperId") int paperId) {
        // 1. 获取四种类型题目的 quesId 列表
        List<Integer> choiceQIDs = paperService.getTotalNumsByPaperId(paperId, CONST.TYPE_SINGLE_CHOICE);
        List<Integer> fillGapQIDs = paperService.getTotalNumsByPaperId(paperId, CONST.TYPE_FILL_GAP);
        List<Integer> judgeQIDs = paperService.getTotalNumsByPaperId(paperId, CONST.TYPE_JUDGE);
        List<Integer> shortAnsQIDs = paperService.getTotalNumsByPaperId(paperId, CONST.TYPE_SHORT_ANS);
        // 2. 获取四种类型题目列表及数量
        List<QuestionSingleChoice> choiceList = new ArrayList<>();
        List<QuestionFillGap> fillGapList = new ArrayList<>();
        List<QuestionJudge> judgeList = new ArrayList<>();
        List<QuestionShortAns> shortAnsList = new ArrayList<>();
        for (Integer qid:choiceQIDs) {
            QuestionSingleChoice singleChoice = singleChoiceService.getSingleChoiceById(qid);
            if(singleChoice != null) { choiceList.add(singleChoice); }
        }
        for (Integer qid:fillGapQIDs) {
            QuestionFillGap fillGap = fillGapService.getFillGapById(qid);
            if(fillGap != null) { fillGapList.add(fillGap); }
        }
        for(Integer qid : judgeQIDs) {
            QuestionJudge judge = judgeService.getJudgeById(qid);
            if(judge != null) { judgeList.add(judge); }
        }
        for(Integer qid : shortAnsQIDs) {
            QuestionShortAns shortAns = shortAnsService.getShortAnsById(qid);
            if(shortAns != null) { shortAnsList.add(shortAns); }
        }
        // 3.封装返回值
        Map<String, Object> jsonmap = new HashMap<>();
        jsonmap.put("choiceNums", choiceList.size());
        jsonmap.put("choiceList", choiceList);
        jsonmap.put("fillGapNums", fillGapList.size());
        jsonmap.put("fillGapList", fillGapList);
        jsonmap.put("judgeNums", judgeList.size());
        jsonmap.put("judgeList", judgeList);
        jsonmap.put("shortAnsNums", shortAnsList.size());
        jsonmap.put("shortAnsList", shortAnsList);
        return Result.success(jsonmap);
    }

    @ResponseBody
    @GetMapping(value = "/getTeacherByUsername")
    public Result getTeacherByUsername(@RequestParam(name = "username") String username) {
        Teacher teacher = teacherService.getTeacherByUsername(username);
        if(teacher == null) {
            return Result.error("error");
        }
        return Result.success(teacher);
    }

    @ResponseBody
    @GetMapping(value = "/getStudentListByMajor")
    public Result getStudentListByMajor(@RequestParam(name = "query")  String query,
                                        @RequestParam(name = "institute") String institute,
                                        @RequestParam(name = "major") String major,
                                        @RequestParam(name = "pagenum")  Integer pagenum,
                                        @RequestParam(name = "pagesize") Integer pagesize) {
        if(institute == null || major == null || "".equals(institute) || "".equals(major)) {
            return Result.error("error");
        }

        Map<String, Object> jsonmap = new HashMap<>();
        List<Student> studentList = new ArrayList<>();
        if("".equals(query)) {
            Page<Student> studentPage = new Page<>(pagenum, pagesize);
            IPage<Student> allStudent = studentService.getStudentListByMajor(studentPage, institute, major);
            studentList = allStudent.getRecords();
            jsonmap.put("total", allStudent.getTotal());
        } else {
            Student queryStudent = studentService.getStudentByNameAndMajor(query, institute, major);
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
    @GetMapping(value = "/getExamListByTid")
    public Result getExamListByTid(@RequestParam(name = "tid")  Integer tid,
                                        @RequestParam(name = "pagenum")  Integer pagenum,
                                        @RequestParam(name = "pagesize") Integer pagesize) {
        if(tid == null) {
            return Result.error("error");
        }
        Map<String, Object> jsonmap = new HashMap<>();
        List<Exams> examList = new ArrayList<>();

        Page<Exams> examsPage = new Page<>(pagenum, pagesize);
        IPage<Exams> allExam  = examsService.getExamListByTid(examsPage, tid);

        examList = allExam.getRecords();
        jsonmap.put("examList", examList);
        jsonmap.put("total", allExam.getTotal());
        return Result.success(jsonmap);
    }

    @ResponseBody
    @PostMapping(value = "/addExam")
    public Result addExam(@RequestBody Exams exams) {
        // 获取最大的 paperId
        Integer maxPaperId = paperService.getMaxPaperId();
        if(maxPaperId == null) {
            exams.setPaperId(0);
        } else {
            exams.setPaperId(maxPaperId + 1);
        }
        // 获取总分
        int totalSingleChoiceScore  = exams.getTotalSingleChoiceScore();
        int totalFillGapScore       = exams.getTotalFillGapScore();
        int totalJudgeScore         = exams.getTotalJudgeScore();
        int totalShortAnsScore      = exams.getTotalShortAnsScore();
        int totalScore = totalSingleChoiceScore + totalFillGapScore + totalJudgeScore + totalShortAnsScore;
        exams.setTotalScore(totalScore);
        examsService.addExam(exams);

        // 计算每种类型的题目数量 = 该类型总分 / 单个分数
        int singleChoiceNums    = totalSingleChoiceScore / CONST.PER_SINGLE_CHOICE_SCORES;
        int fillGapNums         = totalFillGapScore / CONST.PER_FILL_GAP_SCORES;
        int judgeNums           = totalJudgeScore / CONST.PER_JUDGE_SCORES;
        int shortAnsNums        = totalShortAnsScore / CONST.PER_SHORT_ANS_SCORES;

        // 每种类型的题目 随机 获取对应个数
        int paperId = exams.getPaperId();
        int examId = examsService.getExamIdByPaperId(paperId);
        int quesType, score, nums;
        if(singleChoiceNums > 0) {
            quesType = CONST.TYPE_SINGLE_CHOICE;
            score    = CONST.PER_SINGLE_CHOICE_SCORES;
            nums     = singleChoiceNums;
            // 获取该类型所有题目的 qid 放进 List 中
            List<Integer> qids = singleChoiceService.getAllQid();
            // 通过随机算法，获得 nums 个不重复的随机 qid 放入 target List 中作为试卷该类型题目的题目列表
            List<Integer> target = RANDOM.getRandomList(qids, nums);
            for(int i = 0; i < target.size(); i ++) {
                int quesId = target.get(i);
                paperService.addQues(paperId, examId, quesId, quesType, score);
            }
        }
        if(fillGapNums > 0) {
            quesType = CONST.TYPE_FILL_GAP;
            score    = CONST.PER_FILL_GAP_SCORES;
            nums     = fillGapNums;
            List<Integer> qids = fillGapService.getAllQid();
            List<Integer> target = RANDOM.getRandomList(qids, nums);
            for(int i = 0; i < target.size(); i ++) {
                int quesId = target.get(i);
                paperService.addQues(paperId, examId, quesId, quesType, score);
            }
        }
        if(judgeNums > 0) {
            quesType = CONST.TYPE_JUDGE;
            score    = CONST.PER_JUDGE_SCORES;
            nums     = judgeNums;
            List<Integer> qids = judgeService.getAllQid();
            List<Integer> target = RANDOM.getRandomList(qids, nums);
            for(int i = 0; i < target.size(); i ++) {
                int quesId = target.get(i);
                paperService.addQues(paperId, examId, quesId, quesType, score);
            }
        }
        if(shortAnsNums > 0) {
            quesType = CONST.TYPE_SHORT_ANS;
            score    = CONST.PER_SHORT_ANS_SCORES;
            nums     = shortAnsNums;
            List<Integer> qids = shortAnsService.getAllQid();
            List<Integer> target = RANDOM.getRandomList(qids, nums);
            for(int i = 0; i < target.size(); i ++) {
                int quesId = target.get(i);
                paperService.addQues(paperId, examId, quesId, quesType, score);
            }
        }

        // 获取参加该场考试的所有学生的 sid
        String major = exams.getMajor();
        List<Integer> sidList = studentService.getAllSidByMajor(major);
        StudentPaperList studentPaperList = new StudentPaperList();
        studentPaperList.setExamId(examId);
        studentPaperList.setPaperId(paperId);
        studentPaperList.setIsSubmitted(0);
        for (Integer sid : sidList) {
            studentPaperList.setSid(sid);
            studentPaperListService.addStudentPaperList(studentPaperList);
        }

        return Result.success(paperId);
    }
}
