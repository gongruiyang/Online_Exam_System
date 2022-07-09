package com.xpu.onlineexaminationsys.vobean;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentScoreVO {
    @ExcelProperty("序号")
    Integer id;
    @ExcelProperty("姓名")
    String studentName;
    @ExcelProperty("学号")
    Integer sid;
    @ExcelProperty("专业")
    String majorAndClass;

    @ExcelProperty("单选题总分")
    Integer totalSingleChoiceScore;
    @ExcelProperty("填空题总分")
    Integer totalFillGapScore;
    @ExcelProperty("判断题总分")
    Integer totalJudgeScore;
    @ExcelProperty("简答题总分")
    Integer totalShortAnsScore;
    @ExcelProperty("试卷总分")
    Integer totalScore;
}
