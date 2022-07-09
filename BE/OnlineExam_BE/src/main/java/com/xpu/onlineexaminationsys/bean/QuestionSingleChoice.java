package com.xpu.onlineexaminationsys.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuestionSingleChoice {
    private Integer quesId;
    @ExcelProperty("题目描述")
    private String quesDesc;
    @ExcelProperty("答案A")
    private String ansA;
    @ExcelProperty("答案B")
    private String ansB;
    @ExcelProperty("答案C")
    private String ansC;
    @ExcelProperty("答案D")
    private String ansD;
    @ExcelProperty("正确答案")
    private String rightAns;
    @ExcelProperty("答案解析")
    private String ansAnalysis;
    @ExcelProperty("分值")
    private Integer score;
    @ExcelProperty("所属科目")
    private String blgSub;
    private Integer tid;
}
