package com.xpu.onlineexaminationsys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xpu.onlineexaminationsys.bean.QuestionFillGap;
import com.xpu.onlineexaminationsys.mapper.FillGapMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FillGapService {
    @Autowired
    FillGapMapper fillGapMapper;


    public int addFillGap(QuestionFillGap fillGap) {
        return fillGapMapper.addFillGap(fillGap);
    }

    public int deleteFillGapByQid(Integer quesId) {
        return fillGapMapper.deleteFillGapByQid(quesId);
    }

    public int updateFillGapByQid(QuestionFillGap fillGap) {
        return fillGapMapper.updateFillGapByQid(fillGap);
    }

    public IPage<QuestionFillGap> getAllFillGap(Page<QuestionFillGap> fillGapPage) {
        return fillGapMapper.getAllFillGap(fillGapPage);
    }

    public List<QuestionFillGap> getFillGapBySubject(String querySubject) {
        return fillGapMapper.getFillGapBySubject(querySubject);
    }

    public QuestionFillGap getFillGapById(Integer quesId) {
        return fillGapMapper.getFillGapById(quesId);
    }

    public List<Integer> getAllQid() {
        return fillGapMapper.getAllQid();
    }

    public int getLatestQid() {
        return fillGapMapper.getLatestQid();
    }
}
