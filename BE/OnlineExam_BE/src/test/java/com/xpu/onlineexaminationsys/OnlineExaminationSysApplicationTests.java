package com.xpu.onlineexaminationsys;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.xpu.onlineexaminationsys.bean.*;
import com.xpu.onlineexaminationsys.listener.ExcelListener;
import com.xpu.onlineexaminationsys.service.*;
import com.xpu.onlineexaminationsys.util.CONST;
import com.xpu.onlineexaminationsys.vobean.StudentScoreVO;
import org.apache.poi.xwpf.usermodel.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class OnlineExaminationSysApplicationTests {
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

    @Test
    public void getPath() {
        String path = this.getClass().getResource("/").getPath();
        System.out.println(path);
    }

    @Test
    public void uploadQuestion() {
        String filePath = "question.xlsx";
        Integer tid = 1;

        ExcelListener excelListener = new ExcelListener(tid);
        ExcelReaderBuilder excelReader = EasyExcel.read(filePath, QuestionSingleChoice.class, excelListener);
        ExcelReaderSheetBuilder sheet = excelReader.sheet("单选题");
        sheet.doRead();

        excelReader = EasyExcel.read(filePath, QuestionFillGap.class, new ExcelListener(tid));
        sheet = excelReader.sheet("填空题");
        sheet.doRead();

        excelReader = EasyExcel.read(filePath, QuestionJudge.class, new ExcelListener(tid));
        sheet = excelReader.sheet("判断题");
        sheet.doRead();

        excelReader = EasyExcel.read(filePath, QuestionShortAns.class, new ExcelListener(tid));
        sheet = excelReader.sheet("简答题");
        sheet.doRead();

    }

    @Test
    public void getStudentScoreExcel() {
        Integer paperId = 5;
        // create a excel
        Exams exam = examsService.getExamByPaperId(paperId);
        String filename = exam.getMajor() + exam.getSubject() + "成绩单";
        String filePath = filename + ".xlsx";
        ExcelWriterBuilder excelWrite = EasyExcel.write(filePath, StudentScoreVO.class);
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

    }

    @Test
    public void getStudentPaper() {
        Integer paperId = 5;
        Integer sid     = 107;
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
            filePath = export2word(choiceList, fillGapList, judgeList, shortAnsList, exams, student, studentPaperList, stuSingleArr, stuFillArr, stuJudgeArr, stuShortArr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(filePath);
    }
    private String export2word(List<QuestionSingleChoice> choiceList, List<QuestionFillGap> fillGapList,
                               List<QuestionJudge> judgeList, List<QuestionShortAns> shortAnsList,
                               Exams exams, Student student, StudentPaperList studentPaperList,
                               String[] stuSingleArr, String[] stuFillArr,
                               String[] stuJudgeArr, String[] stuShortArr) throws IOException {
        String studentName = student.getStudentName();
        String sid = student.getSid().toString();
        String institute = student.getInstitute();
        String major = student.getMajor();
        String clazz = student.getClass_();
        String subject = exams.getSubject();
        String examBeginTime = exams.getExamTime();
        String examLastTime  = exams.getExamLastTime().toString();

        String filename = studentName + "-" + sid + "-" + subject + "答题试卷.docx";
        File paper = new File(filename);
        if(paper.exists()) {
            FileWriter fw = new FileWriter(paper);
            fw.write("");
            fw.flush();
            fw.close();
        }
        XWPFDocument document = new XWPFDocument();

        // 1. 试卷标题
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        setTitle(titleRun);
        titleRun.setText(subject + "考试试卷");
        // 2. 试卷信息
        XWPFParagraph examInfor = document.createParagraph();
        examInfor.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun examInforRun = examInfor.createRun();
        setExamInfor(examInforRun);

        examInforRun.setText("专业：" + institute + major);
        examInfor = document.createParagraph();
        examInfor.setAlignment(ParagraphAlignment.LEFT);
        examInforRun = examInfor.createRun();
        setExamInfor(examInforRun);
        examInforRun.setText("考试开始时间：" + examBeginTime);
        examInforRun.setText("\t考试持续时间：" + examLastTime + "分钟");

        examInfor = document.createParagraph();
        examInfor.setAlignment(ParagraphAlignment.LEFT);
        examInforRun = examInfor.createRun();
        setExamInfor(examInforRun);
        examInforRun.setText("试卷分值：" + exams.getTotalScore() + "分\t");
        examInforRun.setText("学生总分：" + studentPaperList.getTotalScore() + "分");
        // 3. 考生信息
        XWPFParagraph studentInfor = document.createParagraph();
        studentInfor.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun studentInforRun = studentInfor.createRun();
        setStudentInfor(studentInforRun);
        studentInforRun.setText("姓名：" + studentName);
        studentInforRun.setText("\t学号：" + sid);
        // 4. 试题信息
        int idx = 1;
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun paragraphRun = paragraph.createRun();
        setCategory(paragraphRun);
        paragraphRun.setText("一、单选题 (共" + choiceList.size() + "题，共" + exams.getTotalSingleChoiceScore() +"分)");
        for(int i = 0; i < choiceList.size(); i ++, idx ++) {
            int score = 0;
            if(choiceList.get(i).getRightAns().equals(stuSingleArr[i])) {
                score = choiceList.get(i).getScore();
            }
            String analysis = "无";
            if(choiceList.get(i).getAnsAnalysis() != null) {
                analysis = choiceList.get(i).getAnsAnalysis();
            }
            // information
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText(idx + "、" + choiceList.get(i).getQuesDesc());
            // choice
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText("A: " + choiceList.get(i).getAnsA());
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText("B: " + choiceList.get(i).getAnsB());
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText("C: " + choiceList.get(i).getAnsC());
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText("D: " + choiceList.get(i).getAnsD());
            // two answer
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText("正确答案: " + choiceList.get(i).getRightAns());
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText("学生答案: " + stuSingleArr[i]);
            // score
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setScore(paragraphRun);
            paragraphRun.setText("学生得分：" + score + "分");
            // analysis
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText("题目解析：" + analysis);
            // empty line
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText("\t");
        }

        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraphRun = paragraph.createRun();
        setCategory(paragraphRun);
        paragraphRun.setText("二、填空题 (共" + fillGapList.size() + "题，共" + exams.getTotalFillGapScore() +"分)");
        for(int i = 0; i < fillGapList.size(); i ++, idx ++) {
            int score = 0;
            String fillAnsStr = fillGapList.get(i).getRightAns();
            String[] fillAnsArr = fillAnsStr.split("\\|");
            for(String ans : fillAnsArr) {
                if(ans.equals(stuFillArr[i]))
                score = fillGapList.get(i).getScore();
            }
            String analysis = "无";
            if(fillGapList.get(i).getAnsAnalysis() != null) {
                analysis = fillGapList.get(i).getAnsAnalysis();
            }
            // question
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText(idx + "、" + fillGapList.get(i).getQuesDesc());
            // two answer
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText("正确答案: " + fillGapList.get(i).getRightAns());
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText("学生答案: " + stuFillArr[i]);
            // score
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setScore(paragraphRun);
            paragraphRun.setText("学生得分：" + score + "分");
            // analysis
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText("题目解析：" + analysis);
            // empty line
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText("\t");
        }

        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraphRun = paragraph.createRun();
        setCategory(paragraphRun);
        paragraphRun.setText("三、判断题 (共" + judgeList.size() + "题，共" + exams.getTotalJudgeScore() +"分)");
        for(int i = 0; i < judgeList.size(); i ++, idx ++) {
            int score = 0;
            if(judgeList.get(i).getRightAns().equals(stuJudgeArr[i])) {
                score = judgeList.get(i).getScore();
            }
            String analysis = "无";
            if(judgeList.get(i).getAnsAnalysis() != null) {
                analysis = judgeList.get(i).getAnsAnalysis();
            }
            String rightAns = "对";
            if(judgeList.get(i).getRightAns().equals("0")){
                rightAns = "错";
            }
            String stuAns = "对";
            if(stuJudgeArr[i].equals("0")){
                stuAns = "错";
            }
            // question
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText(idx + "、" + judgeList.get(i).getQuesDesc());
            // two answer
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText("正确答案: " + rightAns);
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText("学生答案: " + stuAns);
            // score
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setScore(paragraphRun);
            paragraphRun.setText("学生得分：" + score + "分");
            // analysis
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText("题目解析：" + analysis);
            // empty line
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText("\t");
        }
        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraphRun = paragraph.createRun();
        setCategory(paragraphRun);
        paragraphRun.setText("四、简答题 (共" + shortAnsList.size() + "题，共" + exams.getTotalShortAnsScore() +"分)");
        for(int i = 0; i < shortAnsList.size(); i ++, idx ++) {
            String analysis = "无";
            if(fillGapList.get(i).getAnsAnalysis() != null) {
                analysis = shortAnsList.get(i).getAnsAnalysis();
            }
            // question
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText(idx + "、" + shortAnsList.get(i).getQuesDesc());
            // two answer
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText("正确答案: " + shortAnsList.get(i).getRefAns());
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText("学生答案: " + stuShortArr[i]);
            // analysis
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText("题目解析：" + analysis);
            // empty line
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraphRun = paragraph.createRun();
            setQuestion(paragraphRun);
            paragraphRun.setText("\t");
        }

        FileOutputStream outputStream = new FileOutputStream(paper);
        document.write(outputStream);
        document.close();
        outputStream.close();
        return paper.getAbsolutePath();
    }
    public void setTitle(XWPFRun run) {
        run.setBold(true);
        run.setFontSize(24);
        run.setFontFamily("宋体");
    }
    public void setExamInfor(XWPFRun run) {
        run.setFontSize(10);
        run.setFontFamily("宋体");
    }
    public void setStudentInfor(XWPFRun run) {
        run.setFontSize(10);
        run.setFontFamily("宋体");
    }
    public void setCategory(XWPFRun run) {
        run.setBold(true);
        run.setFontSize(18);
        run.setFontFamily("宋体");
    }
    public void setQuestion(XWPFRun run) {
        run.setFontSize(10);
        run.setFontFamily("宋体");
    }
    public void setScore(XWPFRun run) {
        run.setFontSize(12);
        run.setColor("DC143C");
        run.setFontFamily("宋体");
    }
}
