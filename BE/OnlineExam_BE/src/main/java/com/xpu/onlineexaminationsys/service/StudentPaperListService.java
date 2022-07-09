package com.xpu.onlineexaminationsys.service;

import com.xpu.onlineexaminationsys.bean.StudentPaperList;
import com.xpu.onlineexaminationsys.mapper.StudentPaperListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentPaperListService {
    @Autowired
    StudentPaperListMapper studentPaperListMapper;

    public int updateStudentPaper(StudentPaperList studentPaperList) {
        return studentPaperListMapper.updateStudentPaper(studentPaperList);
    }

    public void addStudentPaperList(StudentPaperList studentPaperList) {
        studentPaperListMapper.addStudentPaperList(studentPaperList);
    }

    public List<StudentPaperList> getAllByPaperId(int paperId) {
        return studentPaperListMapper.getAllByPaperId(paperId);
    }

    public Integer getSubmitStatusBySidAndPaperId(Integer sid, Integer paperId) {
        return studentPaperListMapper.getSubmitStatusBySidAndPaperId(sid, paperId);
    }

    public StudentPaperList getOneStudenPaper(Integer paperId, Integer sid) {
        return studentPaperListMapper.getOneStudenPaper(paperId, sid);
    }
}
