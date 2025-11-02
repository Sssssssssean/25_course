package com.course.service;

import com.course.dao.PointDATA;
import com.course.pojo.PointObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BloodSugar {
    @Autowired
    private PointDATA pointDATA;

    public void bloodSugar() {

        System.out.println("+++++bloodSugar积分计算方法执行+++++");
        PointObject point = pointDATA.getPoint();
        if (point == null) {
            System.out.println("数据错误");
            return;
        }
        Integer count= point.getBloodSugarCount();
        if (count == null) {
            System.out.println("数据错误");
            return;
        }
        count++;
        PointObject dataPoint = pointDATA.getPoint();
        dataPoint.setBloodSugarCount(count);
        pointDATA.setPoint(dataPoint);
        if(count<=3){
            System.out.println("血糖次数小于等于3次，不加分");
            return;
        }
        // 增加成长积分
        pointDATA.addScore(1, 0);
    }
}
