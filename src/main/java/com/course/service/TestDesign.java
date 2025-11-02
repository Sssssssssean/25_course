package com.course.service;
import com.course.mapper.UserMapper;
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
public class TestDesign {
	@Autowired
	UserMapper userMapper;
	public  void testDesign(int id){
		PointObject pointObject=userMapper.getPointObjectByID(id);
		int exchange=pointObject.getExchangeScore();
		int grow=pointObject.getGrowScore();
		int total=pointObject.getScoreTotal();
		pointObject.setScoreTotal(total+2);
		pointObject.setGrowScore(grow+1);
		pointObject.setExchangeScore(exchange+1);
		userMapper.updatePointObject(pointObject);
		System.out.println("testDesign execute");
	}
}
