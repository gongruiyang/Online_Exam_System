package com.xpu.onlineexaminationsys.service;

import com.xpu.onlineexaminationsys.bean.QuestionShortAns;
import com.xpu.onlineexaminationsys.mapper.ShortAnsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortAnsService {
    @Autowired
    ShortAnsMapper shortAnsMapper;
    public List<Integer> getAllQid() {
        return shortAnsMapper.getAllQid();
    }

    public QuestionShortAns getShortAnsById(Integer qid) {
        return shortAnsMapper.getShortAnsById(qid);
    }

    public int addShortAns(QuestionShortAns q) {
        return shortAnsMapper.addShortAns(q);
    }

    public int getLatestQid() {
        return shortAnsMapper.getLatestQid();
    }
}
