package com.course.pojo;

import java.io.Serializable;

public class ScoreRecord implements Serializable {
    private Integer id;
    private Integer growScore;
    private Integer exchangeScore;
    private String addTime;

    public Integer getExchangeScore() {
        return exchangeScore;
    }

    public ScoreRecord() {
    }

    public ScoreRecord(Integer growScore, Integer exchangeScore, String addTime) {
        this.growScore = growScore;
        this.exchangeScore = exchangeScore;
        this.addTime = addTime;
    }

    public void setExchangeScore(Integer exchangeScore) {
        this.exchangeScore = exchangeScore;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public Integer getGrowScore() {
        return growScore;
    }

    public void setGrowScore(Integer growScore) {
        this.growScore = growScore;
    }
}
