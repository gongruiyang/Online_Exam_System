package com.xpu.onlineexaminationsys.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Exams {
    private Integer examId;
    private Integer tid;
    private String teacherName;
    private Integer paperId;
    private String examTime;
    private Integer examLastTime;
    private String grade;
    private String term;
    private String institute;
    private String major;
    private String subject;
    private String description;
    private String tips;
    private Integer examType;
    private Integer totalSingleChoiceScore;
    private Integer totalFillGapScore;
    private Integer totalJudgeScore;
    private Integer totalShortAnsScore;
    private Integer totalScore;
}
