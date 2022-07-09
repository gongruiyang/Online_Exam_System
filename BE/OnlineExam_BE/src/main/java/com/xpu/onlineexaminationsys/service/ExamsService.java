package com.xpu.onlineexaminationsys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xpu.onlineexaminationsys.bean.Exams;
import com.xpu.onlineexaminationsys.mapper.ExamsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamsService {
    @Autowired
    ExamsMapper examsMapper;

    public IPage<Exams> getAllExams(Page<Exams> examsPage) {
        return examsMapper.getAllExams(examsPage);
    }

    public List<Exams> getExamByTeacherName(String queryTeacherName) {

        return examsMapper.getExamByTeacherName(queryTeacherName);
    }

    public IPage<Exams> getExamListByTid(Page<Exams> examsPage, Integer tid) {
        return examsMapper.getExamListByTid(examsPage, tid);
    }

    public void addExam(Exams exams) {
        examsMapper.addExam(exams);
    }

    public int getExamIdByPaperId(int paperId) {
        return examsMapper.getExamIdByPaperId(paperId);
    }

    public IPage<Exams> getExamListByInstituteAndMajor(Page<Exams> examsPage, String institute, String major) {
        return examsMapper.getExamListByInstituteAndMajor(examsPage, institute, major);
    }

    public Exams getExamByPaperId(Integer paperId) {
        return examsMapper.getExamByPaperId(paperId);
    }

    public List<Integer> getPaperIdListByTid(Integer tid) {
        return examsMapper.getPaperIdListByTid(tid);
    }
}
