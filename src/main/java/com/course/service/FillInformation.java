package com.course.service;
import com.course.dao.PointDATA;
import com.course.pojo.PointObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.course.pojo.PointObject;
//import com.course.utils.FileUtils;
//import com.course.utils.JsonUtils;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
//类名与方法名须与controller层拦截的方法一致
@Service
public class FillInformation {
    @Autowired
    private PointDATA pointDATA;

    public void fillInformation() {
        System.out.println("+++++fillInformation积分计算方法执行+++++");
        PointObject point = pointDATA.getPoint();
        if (point == null) {
            System.out.println("数据错误");
            return;
        }
        if (point.getProfileInputed()) {
            System.out.println("非首次填写");
            return;
        }
        // 增加成长积分
        pointDATA.addScore(2, 0);
        // 修改个人资料填写状态
        PointObject dataPoint = pointDATA.getPoint();
        dataPoint.setProfileInputed(true);
        pointDATA.setPoint(dataPoint);
    }
}