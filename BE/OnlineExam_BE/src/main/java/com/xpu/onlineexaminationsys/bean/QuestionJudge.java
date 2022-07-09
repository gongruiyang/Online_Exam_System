package com.xpu.onlineexaminationsys.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuestionJudge {
    private Integer quesId;
    @ExcelProperty("题目描述")
    private String quesDesc;
    @ExcelProperty("正确答案")
    private String rightAns;
    @ExcelProperty("答案解析")
    private String ansAnalysis;
    @ExcelProperty("分值")
    private Integer score;
    @ExcelProperty("所属科目")
    public String blgSub;
    public Integer tid;
}
