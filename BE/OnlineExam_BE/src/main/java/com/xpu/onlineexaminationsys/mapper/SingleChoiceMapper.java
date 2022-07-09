package com.xpu.onlineexaminationsys.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xpu.onlineexaminationsys.bean.QuestionSingleChoice;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SingleChoiceMapper {
    @Select("select * from question_single_choice")
    IPage<QuestionSingleChoice> getAllSingleChoice(Page<QuestionSingleChoice> singleChoicePage);
    @Select("select * from question_single_choice where blgSub = #{querySubject}")
    List<QuestionSingleChoice> getSingleChoiceBySubject(String querySubject);
    @Select("select * from question_single_choice where quesId = #{quesId}")
    QuestionSingleChoice getSingleChoiceById(Integer quesId);


    @Insert("insert into question_single_choice(quesDesc, ansA, ansB, ansC, ansD, rightAns, ansAnalysis, score, blgSub, tid) " +
            "values (#{quesDesc}, #{ansA}, #{ansB}, #{ansC}, #{ansD}, #{rightAns}, #{ansAnalysis}, #{score}, #{blgSub}, #{tid})")
    int addSingleChoice(QuestionSingleChoice singleChoice);

    @Delete("delete from question_single_choice where quesId = #{qid}")
    int deleteSingleChoiceByQid(Integer qid);

    @Update("update question_single_choice set quesDesc=#{quesDesc}, ansA=#{ansA}, ansB=#{ansB}, ansC=#{ansC}, ansD=#{ansD}, rightAns=#{rightAns}, " +
            "ansAnalysis=#{ansAnalysis}, score=#{score}, blgSub=#{blgSub}, tid=#{tid} " +
            "where quesId=#{quesId}")
    int updateSingleChoiceByQid(QuestionSingleChoice singleChoice);

    @Select("select quesId from question_single_choice")
    List<Integer> getAllQid();

    @Select("select max(quesId) from question_single_choice")
    int getLatestQid();
}
