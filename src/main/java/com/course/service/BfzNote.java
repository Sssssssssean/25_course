package com.course.service;
import com.course.dao.PointDATA;
import com.course.model.Result;
import com.course.pojo.PointObject;
import com.course.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.pojo.PointObject;
//import com.course.utils.FileUtils;
//import com.course.utils.JsonUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
//类名与方法名须与controller层拦截的方法一致
@Service
public class BfzNote {
    @Autowired
    private PointDATA pointDATA;

    public void BfzNode() {
        System.out.println("+++++bfzNote积分计算方法执行+++++");
        PointObject point = pointDATA.getPoint();

        // 检查上次并发症记录时间
        String lastComplicationTimeStr = point.getLastComplicationTime();
        LocalDateTime now = LocalDateTime.now();

        // 如果有上次记录时间，检查是否满足一年间隔
        if (lastComplicationTimeStr != null && !lastComplicationTimeStr.isEmpty() &&
            !lastComplicationTimeStr.equals("1949-10-01 12:00:00")) {
            try {
                LocalDateTime lastBFZtime = LocalDateTime.parse(lastComplicationTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                // 一年记一次分
                if (!lastBFZtime.plusYears(1).isBefore(now)) {
                    System.err.println("距离上次填写并发症不足一年");
                    return;
                }
            } catch (Exception e) {
                System.err.println("解析上次并发症时间失败: " + e.getMessage());
                // 如果解析失败，允许继续执行（视为首次记录）
            }
        }

        // 计分
        pointDATA.addScore(3, 0);
        PointObject dataPoint = pointDATA.getPoint();
        dataPoint.setLastComplicationTime(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        pointDATA.setPoint(dataPoint);
        System.out.println("并发症记录成功，获得3分成长积分");
    }
}