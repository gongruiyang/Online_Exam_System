package com.xpu.onlineexaminationsys.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xpu.onlineexaminationsys.bean.Exams;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExamsMapper {
    @Select("select * from exams")
    IPage<Exams> getAllExams(Page<Exams> examsPage);
    @Select("select * from exams where teacherName = #{queryTeacherName}")
    List<Exams> getExamByTeacherName(String queryTeacherName);
    @Select("select * from exams where tid = #{tid}")
    IPage<Exams> getExamListByTid(Page<Exams> examsPage, Integer tid);
    @Select("select examId from exams where paperId = #{paperId}")
    int getExamIdByPaperId(int paperId);
    @Select("select * from exams where institute = #{institute} and major = #{major}")
    IPage<Exams> getExamListByInstituteAndMajor(Page<Exams> examsPage, String institute, String major);

    @Insert("insert into exams(tid, teacherName, paperId, examTime, examLastTime, grade, term, institute, major, subject, description, tips, examType, totalSingleChoiceScore, totalFillGapScore, totalJudgeScore, totalShortAnsScore, totalScore) " +
            "values (#{tid}, #{teacherName}, #{paperId}, #{examTime}, #{examLastTime}, #{grade}, #{term}, #{institute}, #{major}, #{subject}, #{description}, #{tips}, #{examType}, #{totalSingleChoiceScore}, #{totalFillGapScore}, #{totalJudgeScore}, #{totalShortAnsScore}, #{totalScore})")
    void addExam(Exams exams);
    @Select("select * from exams where paperId = #{paperId}")
    Exams getExamByPaperId(Integer paperId);

    @Select("select paperId from exams where tid = #{tid}")
    List<Integer> getPaperIdListByTid(Integer tid);
}
