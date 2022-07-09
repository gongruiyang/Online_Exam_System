package com.xpu.onlineexaminationsys.mapper;

import com.xpu.onlineexaminationsys.bean.StudentPaperList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StudentPaperListMapper {
    @Update("update student_paper_list set examId=#{examId}, paperId=#{paperId}, sid=#{sid}, totalScore=#{totalScore}, " +
            "totalSingleChoiceScore=#{totalSingleChoiceScore}, totalFillGapScore=#{totalFillGapScore}, totalJudgeScore=#{totalJudgeScore}, totalShortAnsScore=#{totalShortAnsScore}, " +
            "ansSingleChoice=#{ansSingleChoice}, ansFillGap=#{ansFillGap},ansJudge=#{ansJudge},ansShortAns=#{ansShortAns},IsSubmitted=#{IsSubmitted} " +
            "where sid=#{sid} and examId=#{examId} and paperId=#{paperId}")
    int updateStudentPaper(StudentPaperList studentPaperList);

    @Insert("insert into student_paper_list(examId, paperId, sid, IsSubmitted) " +
            "values (#{examId}, #{paperId},#{sid},#{IsSubmitted})")
    void addStudentPaperList(StudentPaperList studentPaperList);

    @Select("select * from student_paper_list where paperId = #{paperId}")
    List<StudentPaperList> getAllByPaperId(int paperId);

    @Select("select IsSubmitted from student_paper_list where paperId = #{paperId} and sid = #{sid}")
    Integer getSubmitStatusBySidAndPaperId(Integer sid, Integer paperId);

    @Select("select * from student_paper_list where paperId = #{paperId} and sid = #{sid}")
    StudentPaperList getOneStudenPaper(Integer paperId, Integer sid);
}
