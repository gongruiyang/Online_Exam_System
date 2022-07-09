package com.xpu.onlineexaminationsys.util;

import com.xpu.onlineexaminationsys.bean.*;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WordUtil {
    public static String export2word(List<QuestionSingleChoice> choiceList, List<QuestionFillGap> fillGapList,
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
    public static void setTitle(XWPFRun run) {
        run.setBold(true);
        run.setFontSize(24);
        run.setFontFamily("宋体");
    }
    public static void setExamInfor(XWPFRun run) {
        run.setFontSize(10);
        run.setFontFamily("宋体");
    }
    public static void setStudentInfor(XWPFRun run) {
        run.setFontSize(10);
        run.setFontFamily("宋体");
    }
    public static void setCategory(XWPFRun run) {
        run.setBold(true);
        run.setFontSize(18);
        run.setFontFamily("宋体");
    }
    public static void setQuestion(XWPFRun run) {
        run.setFontSize(10);
        run.setFontFamily("宋体");
    }
    public static void setScore(XWPFRun run) {
        run.setFontSize(12);
        run.setColor("DC143C");
        run.setFontFamily("宋体");
    }
}
