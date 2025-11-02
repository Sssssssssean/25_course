package com.course.service;

import com.course.dao.PointDATA;
import com.course.pojo.PointObject;
//import com.course.utils.FileUtils;
//import com.course.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lixuy
 * Created on 2019-04-11
 *
 * 本类用于扩展活动相关的积分处理逻辑，
 * 要确保类名与 controller 层中需要拦截的方法一致。
 */
@Service
public class ExtendedActivity {

    @Autowired
    private PointDATA pointDATA;

    public void extendedActivity() {
        System.out.println("+++++extendedActivity积分计算方法执行+++++");

        // 获取当前用户的积分对象
        PointObject point = pointDATA.getPoint();
        if (point == null) {
            System.out.println("未获取到有效积分数据，终止处理。");
            return;
        }

        // 计分：5分可兑换积分
        pointDATA.addScore(0, 5);
        System.out.println("参加扩展活动成功，获得5分可兑换积分");
    }

}
