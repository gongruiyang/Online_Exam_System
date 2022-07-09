package com.xpu.onlineexaminationsys.mapper;

import com.xpu.onlineexaminationsys.bean.QuestionJudge;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface JudgeMapper {

    @Select("select quesId from question_judge")
    List<Integer> getAllQid();
    @Select("select * from question_judge where quesId = #{qid}")
    QuestionJudge getJudgeById(Integer qid);

    @Insert("insert into question_judge(quesDesc, rightAns, ansAnalysis, score, blgSub, tid) " +
            "values (#{quesDesc}, #{rightAns}, #{ansAnalysis}, #{score}, #{blgSub}, #{tid})")
    int addJudge(QuestionJudge q);
    @Select("select max(quesId) from question_judge")
    int getLatestQid();
}
