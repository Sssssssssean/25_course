package com.course.dao;

import com.course.pojo.PointObject;

import java.util.List;

/**
 * 积分数据访问接口，提供获取与修改积分对象的基本操作。
 * 可用于管理成长积分与可兑换积分。
 */
public interface PointDATA {

    /**
     * 设置当前操作的积分对象。
     * @param pointObject 积分对象
     */
    void setPoint(PointObject pointObject);

    /**
     * 获取当前设置的积分对象。
     * @return 当前积分对象
     */
    PointObject getPoint();

    /**
     * 获取当前积分对象的成长积分数。
     * @return 成长积分
     */
    int getGrowScore();

    /**
     * 获取当前积分对象的可兑换积分数。
     * @return 可兑换积分
     */
    int getExchangeScore();

    /**
     * 增加积分：成长积分和可兑换积分。
     * @param growScore 成长积分增加值
     * @param exchangeScore 可兑换积分增加值
     */
    void addScore(int growScore, int exchangeScore);

    /**
     * 通过用户ID获取积分对象。
     * @param id 用户ID
     * @return 匹配的积分对象
     */
    PointObject getPointObjectById(int id);

    /**
     * 获取所有积分对象列表。
     * @return 所有积分对象的列表
     */
    List<PointObject> getAllPointObjects();
}