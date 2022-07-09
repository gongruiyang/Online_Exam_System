package com.xpu.onlineexaminationsys.service;

import com.xpu.onlineexaminationsys.mapper.PaperMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PaperService {
    @Autowired
    PaperMapper paperMapper;
    public Integer getMaxPaperId() {
        return paperMapper.getMaxPaperId();
    }

    public void addQues(int paperId, int examId, int quesId, int quesType, int score) {
        paperMapper.addQues(paperId, examId, quesId, quesType, score);
    }
    public List<Integer> getTotalNumsByPaperId(int paperId, int quesType) {
        return paperMapper.getTotalNumsByPaperId(paperId, quesType);
    }
}
