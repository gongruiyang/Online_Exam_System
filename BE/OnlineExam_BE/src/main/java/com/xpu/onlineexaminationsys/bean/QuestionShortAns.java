package com.xpu.onlineexaminationsys.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuestionShortAns {
    private Integer quesId;
    @ExcelProperty("题目描述")
    private String quesDesc;
    @ExcelProperty("参考答案")
    private String refAns;
    @ExcelProperty("答案解析")
    private String ansAnalysis;
    @ExcelProperty("分值")
    private Integer score;
    @ExcelProperty("所属科目")
    private String blgSub;
    private Integer tid;
}
