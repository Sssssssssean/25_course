package com.course.service;
import com.course.dao.PointDATA;
import com.course.pojo.PointObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.course.pojo.PointObject;
//import com.course.utils.FileUtils;
//import com.course.utils.JsonUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
//类名与方法名须与controller层拦截的方法一致
@Service
public class Login {
    @Autowired
    private PointDATA pointDATA;

    public void login() {
        LocalDateTime lastLoginTime = null;
        PointObject dataPoint1 = pointDATA.getPoint();
        if (dataPoint1.getLastLoginTime() != null && !dataPoint1.getLastLoginTime().isEmpty()) {
            lastLoginTime = LocalDateTime.parse(pointDATA.getPoint().getLastLoginTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

        if (lastLoginTime == null || lastLoginTime.toLocalDate().isBefore(LocalDate.now())) {
            // 今天未登录 首次获取积分
            pointDATA.addScore(1, 0);
            // 更新登录时间
            PointObject dataPoint = pointDATA.getPoint();
            dataPoint.setLastLoginTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            pointDATA.setPoint(dataPoint);
        }
        System.out.println("+++++login积分计算方法执行+++++");
    }
}
