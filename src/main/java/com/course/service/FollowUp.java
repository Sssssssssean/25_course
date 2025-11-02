package com.course.service;

import com.course.dao.PointDATA;
import com.course.pojo.PointObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowUp {
    @Autowired
    private PointDATA pointDATA;
    public void followUp() {

        System.out.println("+++++followUp积分计算方法执行+++++");
        //获取积分对象
        PointObject point=pointDATA.getPoint();
        if(point==null){
            System.out.println("数据错误");
            return;
        }
        //计算积分
        pointDATA.addScore(0,3);
    }
}
