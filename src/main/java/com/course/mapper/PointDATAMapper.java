package com.course.mapper;

import com.course.pojo.PointObject;
import com.course.pojo.ScoreRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PointDATAMapper {

    @Select("select * from pointobject where id=#{id}")
    List<PointObject> selectById(Integer id);

    @Select("select * from pointobject")
    List<PointObject> selectAll();

    @Insert("INSERT INTO pointobject " +
            "(id, growScore, exchangeScore, scoreTotal, lastComplicationTime, lastLoginTime, lastYdqnTime, profileInputed, bloodSugarCount) " +
            "VALUES " +
            "(#{id}, #{growScore}, #{exchangeScore}, #{scoreTotal}, #{lastComplicationTime}, #{lastLoginTime}, #{lastYdqnTime}, #{profileInputed}, #{bloodSugarCount})")
    void insert(PointObject pointObject);


    @Update("update pointobject set " +
            "growScore=#{growScore},exchangeScore=#{exchangeScore}," +
            "scoreTotal=#{scoreTotal},lastComplicationTime=#{lastComplicationTime}," +
            "lastLoginTime=#{lastLoginTime},lastYdqnTime=#{lastYdqnTime}," +
            "profileInputed=#{profileInputed},bloodSugarCount=#{bloodSugarCount}" +
            " where id=#{id}")
    void update(PointObject pointObject);

    @Insert("INSERT INTO score_record (grow_score,exchange_score,add_time,p_id) " +
            "values(#{record.growScore},#{record.exchangeScore},#{record.addTime},#{p_id}) ")
    void insertScoreRecord(@Param("record") ScoreRecord scoreRecord, @Param("p_id") Integer pId);

    @Update("UPDATE score_record " +
            "SET grou_score=#{record.growScore},exchange_score=#{record.exchangeScore}" +
            "WHERE id = #{record.id}")
    void updateScoreRecord(@Param("record") ScoreRecord scoreRecord);

    @Select("SELECT * FROM score_record WHERE p_id=#{pId}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "growScore", column = "grow_score"),
            @Result(property = "exchangeScore", column = "exchange_score"),
            @Result(property = "addTime",column = "add_time")
    })
    List<ScoreRecord> getRecordsByPId(@Param("pId") Integer pId);
}
