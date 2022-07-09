package com.xpu.onlineexaminationsys.mapper;

import com.xpu.onlineexaminationsys.bean.QuestionShortAns;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShortAnsMapper {

    @Select("select quesId from question_short_ans")
    List<Integer> getAllQid();
    @Select("select * from question_short_ans where quesId = #{qid}")
    QuestionShortAns getShortAnsById(Integer qid);

    @Insert("insert into question_short_ans(quesDesc, refAns, ansAnalysis, score, blgSub, tid) " +
            "values (#{quesDesc}, #{refAns}, #{ansAnalysis}, #{score}, #{blgSub}, #{tid})")
    int addShortAns(QuestionShortAns q);
    @Select("select max(quesId) from question_short_ans")
    int getLatestQid();
}
