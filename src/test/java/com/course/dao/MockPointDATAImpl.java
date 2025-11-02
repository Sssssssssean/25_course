package com.course.dao;

import com.course.pojo.PointObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mock版本的PointDATA实现，用于测试
 * 使用内存存储，避免数据库依赖
 */
@Service("mockPointDATA")
public class MockPointDATAImpl implements PointDATA {
    
    // 使用Map模拟数据库存储
    private Map<Integer, PointObject> dataStore = new HashMap<>();
    private int currentUserId = 1; // 默认用户ID
    
    @Override
    public void setPoint(PointObject pointObject) {
        if (pointObject.getId() == null) {
            pointObject.setId(currentUserId);
        }
        dataStore.put(pointObject.getId(), pointObject);
    }

    @Override
    public PointObject getPoint() {
        PointObject pointObject = dataStore.get(currentUserId);
        if (pointObject == null) {
            // 创建默认对象
            pointObject = createDefaultPointObject(currentUserId);
            dataStore.put(currentUserId, pointObject);
        }
        return pointObject;
    }

    @Override
    public int getGrowScore() {
        PointObject pointObject = getPoint();
        return pointObject.getGrowScore();
    }

    @Override
    public int getExchangeScore() {
        PointObject pointObject = getPoint();
        return pointObject.getExchangeScore();
    }

    @Override
    public void addScore(int growScore, int exchangeScore) {
        PointObject pointObject = getPoint();
        pointObject.setGrowScore(pointObject.getGrowScore() + growScore);
        pointObject.setExchangeScore(pointObject.getExchangeScore() + exchangeScore);
        pointObject.setScoreTotal(pointObject.getGrowScore() + pointObject.getExchangeScore());
        setPoint(pointObject);
    }

    @Override
    public PointObject getPointObjectById(int id) {
        PointObject pointObject = dataStore.get(id);
        if (pointObject == null) {
            pointObject = createDefaultPointObject(id);
            dataStore.put(id, pointObject);
        }
        return pointObject;
    }

    @Override
    public List<PointObject> getAllPointObjects() {
        return new ArrayList<>(dataStore.values());
    }
    
    private PointObject createDefaultPointObject(int id) {
        PointObject pointObject = new PointObject();
        pointObject.setId(id);
        pointObject.setGrowScore(0);
        pointObject.setExchangeScore(0);
        pointObject.setScoreTotal(0);
        pointObject.setProfileInputed(false);
        pointObject.setBloodSugarCount(0);
        pointObject.setLastComplicationTime("1949-10-01 12:00:00");
        pointObject.setLastLoginTime("1949-10-01 12:00:00");
        pointObject.setLastYdqnTime("1949-10-01 12:00:00");
        return pointObject;
    }
    
    // 测试辅助方法
    public void setCurrentUserId(int userId) {
        this.currentUserId = userId;
    }
    
    public void clearData() {
        dataStore.clear();
    }
}
