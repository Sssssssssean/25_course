package com.course.dao.impl;

import com.course.dao.PointDATA;
import com.course.mapper.PointDATAMapper;
import com.course.pojo.PointObject;
import com.course.pojo.ScoreRecord;
import com.course.utils.RequestHeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PointDATAImpl implements PointDATA {

    @Autowired
    private PointDATAMapper pointDATAMapper;

    @Override
    public void setPoint(PointObject pointObject) {
        // 从ThreadLocal获取id
        String userIdStr = RequestHeaderUtil.getHeader();
        if (userIdStr == null || userIdStr.isEmpty()) {
            throw new RuntimeException("用户ID不能为空");
        }
        Integer id = Integer.parseInt(userIdStr);
        pointObject.setId(id);
        // 先查 查不到再新增point对象
        List<PointObject> pointObjects = pointDATAMapper.selectById(id);
        if (pointObjects.isEmpty()) {
            // 新增
            pointDATAMapper.insert(pointObject);
        } else {
            pointDATAMapper.update(pointObject);
        }
    }

    @Override
    public PointObject getPoint() {
        String userIdStr = RequestHeaderUtil.getHeader();
        if (userIdStr == null || userIdStr.isEmpty()) {
            throw new RuntimeException("用户ID不能为空");
        }
        Integer id = Integer.parseInt(userIdStr);
        List<PointObject> pointObjects = pointDATAMapper.selectById(id);
        if (!pointObjects.isEmpty()) {
            List<ScoreRecord> recordsByPId = pointDATAMapper.getRecordsByPId(id);
            pointObjects.get(0).setScoreRecords(recordsByPId);
            return pointObjects.get(0);
        }

        return setDefault(id);
    }

    @Override
    public int getGrowScore() {
        String userIdStr = RequestHeaderUtil.getHeader();
        if (userIdStr == null || userIdStr.isEmpty()) {
            throw new RuntimeException("用户ID不能为空");
        }
        Integer id = Integer.parseInt(userIdStr);
        List<PointObject> pointObjects = pointDATAMapper.selectById(id);
        if (pointObjects.isEmpty()) {
            setDefault(id);
            return 0;
        }
        return pointObjects.get(0).getGrowScore();
    }

    @Override
    public int getExchangeScore() {
        String userIdStr = RequestHeaderUtil.getHeader();
        if (userIdStr == null || userIdStr.isEmpty()) {
            throw new RuntimeException("用户ID不能为空");
        }
        Integer id = Integer.parseInt(userIdStr);
        List<PointObject> pointObjects = pointDATAMapper.selectById(id);
        if (pointObjects.isEmpty()) {
            setDefault(id);
            return 0;
        }
        return pointObjects.get(0).getExchangeScore();
    }

    @Override
    public void addScore(int growScore, int exchangeScore) {
        String userIdStr = RequestHeaderUtil.getHeader();
        if (userIdStr == null || userIdStr.isEmpty()) {
            throw new RuntimeException("用户ID不能为空");
        }
        Integer id = Integer.parseInt(userIdStr);
        List<PointObject> pointObjects = pointDATAMapper.selectById(id);
        if (pointObjects.isEmpty()) {
            setDefault(id);
        }
        // 修改
        PointObject p = pointObjects.get(0);
        p.setGrowScore(p.getGrowScore() + growScore);
        p.setExchangeScore(p.getExchangeScore() + exchangeScore);
        p.setScoreTotal(p.getGrowScore() + p.getExchangeScore());
        pointDATAMapper.update(p);
        // 增加记录
        pointDATAMapper.insertScoreRecord(new ScoreRecord(growScore, exchangeScore, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))), id);
    }

    @Override
    public PointObject getPointObjectById(int id) {
        List<PointObject> pointObjects = pointDATAMapper.selectById(id);
        if (!pointObjects.isEmpty()) {
            List<ScoreRecord> recordsByPId = pointDATAMapper.getRecordsByPId(id);
            pointObjects.get(0).setScoreRecords(recordsByPId);
            return pointObjects.get(0);
        }

        return setDefault(id);
    }

    @Override
    public List<PointObject> getAllPointObjects() {
        return pointDATAMapper.selectAll();
    }

    private PointObject setDefault(Integer id) {
        PointObject pointObject = new PointObject();
        pointObject.setId(id);
        pointObject.setScoreTotal(0);
        pointObject.setGrowScore(0);
        pointObject.setExchangeScore(0);
        pointObject.setBloodSugarCount(0);
        pointObject.setProfileInputed(false);
        pointObject.setLastComplicationTime("1949-10-01 12:00:00");
        pointObject.setLastLoginTime("1949-10-01 12:00:00");
        pointObject.setLastComplicationTime("1949-10-01 12:00:00");
        pointObject.setLastYdqnTime("1949-10-01 12:00:00");
        pointDATAMapper.insert(pointObject);
        return pointObject;
    }
}

