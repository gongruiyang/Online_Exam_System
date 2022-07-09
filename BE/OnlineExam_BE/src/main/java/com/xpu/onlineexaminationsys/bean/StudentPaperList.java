package com.xpu.onlineexaminationsys.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentPaperList {
    Integer examId;
    Integer paperId;
    Integer sid;
    Integer totalScore;
    Integer totalSingleChoiceScore;
    Integer totalFillGapScore;
    Integer totalJudgeScore;
    Integer totalShortAnsScore;
    String ansSingleChoice;
    String ansFillGap;
    String ansJudge;
    String ansShortAns;
    Integer IsSubmitted;
}
