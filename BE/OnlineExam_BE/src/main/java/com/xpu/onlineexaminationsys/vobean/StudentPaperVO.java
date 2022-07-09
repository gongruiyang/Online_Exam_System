package com.xpu.onlineexaminationsys.vobean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentPaperVO {
    String studentName;
    Integer sid;
    Integer paperId;
    String examName;
    String examTime;
    Integer IsSubmitted;

    Integer totalSingleChoiceScore;
    Integer totalFillGapScore;
    Integer totalJudgeScore;
    Integer totalShortAnsScore;
    Integer totalScore;
}
