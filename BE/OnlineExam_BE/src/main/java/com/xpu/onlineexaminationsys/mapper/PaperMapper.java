package com.xpu.onlineexaminationsys.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PaperMapper {
    @Select("select MAX(paperId) from papers")
    Integer getMaxPaperId();

    @Insert("insert into papers (paperId, examId, quesId, quesType, score) " +
            "values (#{paperId}, #{examId}, #{quesId}, #{quesType}, #{score})")
    void addQues(int paperId, int examId, int quesId, int quesType, int score);
    @Select("select quesId from papers where paperId = #{paperId} and quesType = #{quesType}")
    List<Integer> getTotalNumsByPaperId(int paperId, int quesType);
}
