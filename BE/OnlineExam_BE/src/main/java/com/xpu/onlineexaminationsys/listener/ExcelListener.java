package com.xpu.onlineexaminationsys.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.xpu.onlineexaminationsys.bean.QuestionFillGap;
import com.xpu.onlineexaminationsys.bean.QuestionJudge;
import com.xpu.onlineexaminationsys.bean.QuestionShortAns;
import com.xpu.onlineexaminationsys.bean.QuestionSingleChoice;
import com.xpu.onlineexaminationsys.service.FillGapService;
import com.xpu.onlineexaminationsys.service.JudgeService;
import com.xpu.onlineexaminationsys.service.ShortAnsService;
import com.xpu.onlineexaminationsys.service.SingleChoiceService;
import com.xpu.onlineexaminationsys.util.SpringJobBeanFactory;

import java.util.ArrayList;
import java.util.List;

// 读取 excel 文档监听器
public class ExcelListener extends AnalysisEventListener {

    private Integer tid;
    public ExcelListener(Integer tid) {
        this.tid = tid;
        singleQidList = new ArrayList<>();
        fillQidList = new ArrayList<>();
        judgeQidList = new ArrayList<>();
        shortQidList = new ArrayList<>();
    }
    public List<Integer> singleQidList;
    public List<Integer> fillQidList;
    public List<Integer> judgeQidList;
    public List<Integer> shortQidList;
    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        int qid;
        if(o instanceof QuestionSingleChoice) {
            QuestionSingleChoice q = (QuestionSingleChoice) o;
            q.setTid(tid);
            SingleChoiceService singleChoiceService = SpringJobBeanFactory.getBean(SingleChoiceService.class);
            singleChoiceService.addSingleChoice(q);
            qid = singleChoiceService.getLatestQid();
            singleQidList.add(qid);
        } else if (o instanceof QuestionFillGap) {
            QuestionFillGap q = (QuestionFillGap) o;
            q.setTid(tid);
            FillGapService fillGapService = SpringJobBeanFactory.getBean(FillGapService.class);
            fillGapService.addFillGap(q);
            qid = fillGapService.getLatestQid();
            fillQidList.add(qid);
        } else if (o instanceof QuestionJudge) {
            QuestionJudge q = (QuestionJudge) o;
            q.setTid(tid);
            JudgeService judgeService = SpringJobBeanFactory.getBean(JudgeService.class);
            judgeService.addJudge(q);
            qid = judgeService.getLatestQid();
            judgeQidList.add(qid);
        } else if (o instanceof QuestionShortAns) {
            QuestionShortAns q = (QuestionShortAns) o;
            q.setTid(tid);
            ShortAnsService shortAnsService = SpringJobBeanFactory.getBean(ShortAnsService.class);
            shortAnsService.addShortAns(q);
            qid = shortAnsService.getLatestQid();
            shortQidList.add(qid);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
