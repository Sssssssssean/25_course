package com.course.schedule;

import com.course.dao.PointDATA;
import com.course.pojo.PointObject;
import com.course.pojo.ScoreRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


// 扩展需求：
// 在完成基本需求的基础上，完成以下功能可以获得额外分数：
//        ① 成长积分：根据成长积分每月进行评级：C：0-10；B：11-25；A：大于 25
//        ② 可兑换积分：兑换积分的有效期为 1 年，满一年将积分清零


@Component
public class ScoreHandler {
    @Autowired
    PointDATA pointDATA;

    // clearExchangeScore 清除超过一年的可兑换积分
    public void clearExchangeScore() {
        PointObject dataPoint = pointDATA.getPoint();
        List<ScoreRecord> scoreRecords = dataPoint.getScoreRecords();
        // 超过一年的积分删除
        processScoreRecords(scoreRecords);
        int gscore = 0;
        int escore = 0;
        for (ScoreRecord scoreRecord : scoreRecords) {
            gscore += scoreRecord.getGrowScore();
            escore += scoreRecord.getExchangeScore();
        }
        dataPoint.setScoreRecords(scoreRecords);
        dataPoint.setGrowScore(gscore);
        dataPoint.setExchangeScore(escore);
        pointDATA.setPoint(dataPoint);
    }

    // makeLevel 根据成长积分每月进行评级 返回 yyyy:mm -> A|B|C map
    public Map<String, String> makeLevel() {
        Map<String, String> resMap = new HashMap<>();
        Map<String, Integer> scoreMap = new HashMap<>();
        PointObject dataPoint = pointDATA.getPoint();
        List<ScoreRecord> scoreRecords = dataPoint.getScoreRecords();
        for (ScoreRecord scoreRecord : scoreRecords) {
            if (scoreRecord.getGrowScore() <= 0) {
                // 无成长积分记录跳过
                continue;
            }
            LocalDateTime localDateTime = LocalDateTime.parse(scoreRecord.getAddTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String monthTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            if (!scoreMap.containsKey(monthTime)) {
                scoreMap.put(monthTime, scoreRecord.getGrowScore());
            } else {
                scoreMap.put(monthTime, scoreRecord.getGrowScore() + scoreRecord.getExchangeScore());
            }
        }
        scoreMap.forEach((k, v) -> {
            String level = "";
            if (v > 25) {
                level = "A";
            } else if (v > 10) {
                level = "B";
            } else {
                level = "C";
            }
            resMap.put(k, level);
        });
        return resMap;
    }

    private void processScoreRecords(List<ScoreRecord> scoreRecords) {
        Iterator<ScoreRecord> iterator = scoreRecords.iterator();
        LocalDateTime now = LocalDateTime.now();

        while (iterator.hasNext()) {
            ScoreRecord record = iterator.next();
            LocalDateTime addTime = LocalDateTime.parse(record.getAddTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // 检查条件：addTime距离现在超过一年，并且exchangeScore不为0
            if (addTime.isBefore(now.minusYears(1)) && record.getExchangeScore() != 0) {
                // 如果growScore为0，则直接删除该记录
                if (record.getGrowScore() == 0) {
                    iterator.remove();
                } else {
                    // 如果growScore不为0，则将exchangeScore置为0
                    record.setExchangeScore(0);
                }
            }
        }
    }
}
