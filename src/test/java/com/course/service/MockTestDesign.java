package com.course.service;

import com.course.dao.PointDATA;
import com.course.pojo.PointObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Mock版本的TestDesign服务，用于测试
 * 使用PointDATA而不是UserMapper，避免数据库依赖
 */
@Service
public class MockTestDesign {
    
    @Autowired
    private PointDATA pointDATA;
    
    public void testDesign(int id) {
        // 获取当前积分对象
        PointObject pointObject = pointDATA.getPoint();
        
        // 增加积分：1分成长积分 + 1分可兑换积分
        int exchange = pointObject.getExchangeScore();
        int grow = pointObject.getGrowScore();
        int total = pointObject.getScoreTotal();
        
        pointObject.setScoreTotal(total + 2);
        pointObject.setGrowScore(grow + 1);
        pointObject.setExchangeScore(exchange + 1);
        
        // 保存更新后的积分对象
        pointDATA.setPoint(pointObject);
        
        System.out.println("MockTestDesign execute - 增加1分成长积分和1分可兑换积分");
    }
}
