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
public class EvaluateReport {

    @Autowired
    private PointDATA pointDATA;

    public void evaluate() {
        System.out.println("+++++evaluateReport积分计算方法执行+++++");
        PointObject point=pointDATA.getPoint();
        if(point==null){
            System.err.println("数据错误");
            return;
        }
        if(!point.getProfileInputed()){
            System.err.println("未完善个人信息");
            return;
        }
        if(point.getBloodSugarCount()<10){
            System.err.println("血糖记录次数不足");
            return;
        }
        //积分
        pointDATA.addScore(2,0);
    }
}
