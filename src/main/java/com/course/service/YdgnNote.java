package com.course.service;
import com.course.dao.PointDATA;
import com.course.pojo.PointObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.course.pojo.PointObject;
//import com.course.utils.FileUtils;
//import com.course.utils.JsonUtils;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
//类名与方法名须与controller层拦截的方法一致
@Service
public class YdgnNote {
    @Autowired
    private PointDATA pointDATA;
    public void ydgnNote() {
        System.out.println("+++++ydgnNote积分计算方法执行+++++");
        PointObject point = pointDATA.getPoint();

        // 检查上次胰岛功能监测时间
        String lastYdqnTimeStr = point.getLastYdqnTime();
        LocalDateTime now = LocalDateTime.now();

        // 如果有上次监测时间，检查是否满足3个月间隔
        if (lastYdqnTimeStr != null && !lastYdqnTimeStr.isEmpty() &&
            !lastYdqnTimeStr.equals("1949-10-01 12:00:00")) {
            try {
                LocalDateTime lastydtime = LocalDateTime.parse(lastYdqnTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                // 三个月计分一次
                if (!lastydtime.plusMonths(3).isBefore(now)) {
                    System.err.println("距离上次胰岛功能监测不足3个月");
                    return;
                }
            } catch (Exception e) {
                System.err.println("解析上次胰岛功能监测时间失败: " + e.getMessage());
                // 如果解析失败，允许继续执行（视为首次监测）
            }
        }

        // 计分：2分成长积分
        pointDATA.addScore(2, 0);
        PointObject dataPoint = pointDATA.getPoint();
        dataPoint.setLastYdqnTime(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        pointDATA.setPoint(dataPoint);
        System.out.println("胰岛功能监测成功，获得2分成长积分");
    }
}