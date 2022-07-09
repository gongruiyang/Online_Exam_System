package com.xpu.onlineexaminationsys.service;

import com.xpu.onlineexaminationsys.bean.QuestionJudge;
import com.xpu.onlineexaminationsys.mapper.JudgeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JudgeService {
    @Autowired
    JudgeMapper judgeMapper;

    public List<Integer> getAllQid() {
        return judgeMapper.getAllQid();
    }

    public QuestionJudge getJudgeById(Integer qid) {
        return judgeMapper.getJudgeById(qid);
    }

    public int addJudge(QuestionJudge q) {
        return judgeMapper.addJudge(q);
    }

    public int getLatestQid() {
        return judgeMapper.getLatestQid();
    }
}
