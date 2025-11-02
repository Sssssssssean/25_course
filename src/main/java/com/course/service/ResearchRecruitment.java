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
public class ResearchRecruitment {
    @Autowired
    private PointDATA pointDATA;
    public void researchRecruitment() {
        System.out.println("+++++researchRecruitment积分计算方法执行+++++");
        PointObject point=pointDATA.getPoint();
        if(point==null){
            System.out.println("数据错误");
            return;
        }
        // 计分：8分可兑换积分
        pointDATA.addScore(0,8);
        System.out.println("参加科研招募成功，获得8分可兑换积分");
    }
}
