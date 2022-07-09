package com.xpu.onlineexaminationsys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xpu.onlineexaminationsys.bean.QuestionSingleChoice;
import com.xpu.onlineexaminationsys.mapper.SingleChoiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SingleChoiceService {
    @Autowired
    SingleChoiceMapper singleChoiceMapper;

    public int addSingleChoice(QuestionSingleChoice singleChoice) {
        return singleChoiceMapper.addSingleChoice(singleChoice);
    }

    public IPage<QuestionSingleChoice> getAllSingleChoice(Page<QuestionSingleChoice> singleChoicePage) {
        return singleChoiceMapper.getAllSingleChoice(singleChoicePage);
    }

    public List<QuestionSingleChoice> getSingleChoiceBySubject(String querySubject) {
        return singleChoiceMapper.getSingleChoiceBySubject(querySubject);
    }

    public int deleteSingleChoiceByQid(Integer quesId) {
        return singleChoiceMapper.deleteSingleChoiceByQid(quesId);
    }

    public int updateSingleChoiceByQid(QuestionSingleChoice singleChoice) {
        return singleChoiceMapper.updateSingleChoiceByQid(singleChoice);
    }

    public QuestionSingleChoice getSingleChoiceById(Integer quesId) {
        return singleChoiceMapper.getSingleChoiceById(quesId);
    }

    public List<Integer> getAllQid() {
        return singleChoiceMapper.getAllQid();
    }

    public int getLatestQid() {
        return singleChoiceMapper.getLatestQid();
    }
}
