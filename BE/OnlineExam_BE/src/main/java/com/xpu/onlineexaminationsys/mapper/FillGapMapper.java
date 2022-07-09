package com.xpu.onlineexaminationsys.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xpu.onlineexaminationsys.bean.QuestionFillGap;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FillGapMapper {
    @Insert("insert into question_fill_gap(quesDesc, rightAns, ansAnalysis, score, blgSub, tid) " +
            "values (#{quesDesc}, #{rightAns}, #{ansAnalysis}, #{score}, #{blgSub}, #{tid})")
    int addFillGap(QuestionFillGap fillGap);
    @Update("update question_fill_gap set quesDesc=#{quesDesc}, rightAns=#{rightAns}, " +
            "ansAnalysis=#{ansAnalysis}, score=#{score}, blgSub=#{blgSub}, tid=#{tid} " +
            "where quesId=#{quesId}")
    int updateFillGapByQid(QuestionFillGap fillGap);

    @Delete("delete from question_fill_gap where quesId = #{qid}")
    int deleteFillGapByQid(Integer quesId);
    @Select("select * from question_fill_gap")
    IPage<QuestionFillGap> getAllFillGap(Page<QuestionFillGap> fillGapPage);
    @Select("select * from question_fill_gap where blgSub = #{querySubject}")
    List<QuestionFillGap> getFillGapBySubject(String querySubject);
    @Select("select * from question_fill_gap where quesId = #{quesId}")
    QuestionFillGap getFillGapById(Integer quesId);
    @Select("select quesId from question_fill_gap")
    List<Integer> getAllQid();
    @Select("select max(quesId) from question_fill_gap")
    int getLatestQid();
}
