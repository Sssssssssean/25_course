package com.course.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author lixuy
 * Created on 2019-04-10
 */
//实体类
public class PointObject {
    private Integer id;
    //成长积分数
    private Integer growScore;
    //可兑换积分数
    private Integer exchangeScore;
    //总积分数
    private Integer scoreTotal;
    // 上次登录时间
    private String lastLoginTime;
    // 最近并发症计分
    private String lastComplicationTime;
    //最近胰岛素检测成功时间
    private String lastYdqnTime;
    // 是否完善个人信息
    private Boolean profileInputed;
    //血糖记录次数
    private Integer bloodSugarCount;
    // 积分记录
    private List<ScoreRecord> scoreRecords;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGrowScore() {
        return growScore;
    }

    public void setGrowScore(Integer growScore) {
        this.growScore = growScore;
    }

    public Integer getExchangeScore() {
        return exchangeScore;
    }

    public void setExchangeScore(Integer exchangeScore) {
        this.exchangeScore = exchangeScore;
    }

    public Integer getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(Integer scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastComplicationTime() {
        return lastComplicationTime;
    }

    public void setLastComplicationTime(String lastComplicationTime) {
        this.lastComplicationTime = lastComplicationTime;
    }

    public String getLastYdqnTime() {
        return lastYdqnTime;
    }

    public void setLastYdqnTime(String lastYdqnTime) {
        this.lastYdqnTime = lastYdqnTime;
    }

    public Boolean getProfileInputed() {
        return profileInputed;
    }

    public void setProfileInputed(Boolean profileInputed) {
        this.profileInputed = profileInputed;
    }

    public Integer getBloodSugarCount() {
        return bloodSugarCount;
    }

    public void setBloodSugarCount(Integer bloodSugarCount) {
        this.bloodSugarCount = bloodSugarCount;
    }

    public List<ScoreRecord> getScoreRecords() {
        return scoreRecords;
    }

    public void setScoreRecords(List<ScoreRecord> scoreRecords) {
        this.scoreRecords = scoreRecords;
    }

    @Override
    public String toString() {
        return "PointObject{" +
                "id=" + id +
                ", growScore=" + growScore +
                ", exchangeScore=" + exchangeScore +
                ", scoreTotal=" + scoreTotal +
                '}';
    }

}
