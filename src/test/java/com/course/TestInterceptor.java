package com.course;

import com.course.controller.MockTestDesignController;
import com.course.service.MockTestDesign;
import com.course.dao.PointDATA;
import com.course.dao.MockPointDATAImpl;
import com.course.pojo.PointObject;
import com.course.utils.FileUtils;
import com.course.utils.JsonUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author lixuy
 * Created on 2019-04-10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestInterceptor.TestConfig.class})
public class TestInterceptor {

	@Autowired
    MockTestDesignController testDesign;

    @Autowired
    PointDATA pointDATA;
	
    //检验当前积分情况
    private PointObject getPointObject(){
        try {
            PointObject pointObject = pointDATA.getPoint();
            System.out.println("成长积分："+pointObject.getGrowScore());
            System.out.println("可兑换积分："+pointObject.getExchangeScore());
            System.out.println("总积分："+pointObject.getScoreTotal());
            return pointObject;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 辅助方法：重置积分对象
    private void resetPointObject() {
        try {
            if (pointDATA instanceof MockPointDATAImpl) {
                ((MockPointDATAImpl) pointDATA).clearData();
                System.out.println("Mock数据已清空");

                // 验证清空后的状态
                PointObject resetObject = pointDATA.getPoint();
                System.out.println("重置后积分 - 成长:" + resetObject.getGrowScore() +
                                 ", 可兑换:" + resetObject.getExchangeScore() +
                                 ", 总计:" + resetObject.getScoreTotal());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("this is setUpBeforeClass...");
    }
 
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        System.out.println("this is tearDownAfterClass...");
    }
 
    @Before
    public void setUp() throws Exception {
        System.out.println("this is setUp...");
        // 每个测试前重置积分对象
        resetPointObject();
        System.out.println("setUp完成，积分已重置");
    }
 
    @After
    public void tearDown() throws Exception {
        System.out.println("this is tearDown...");
    }
    
    @Test
    public void testDesign() {
    	try {
    		// 确保测试开始时数据是干净的
    		resetPointObject();

    		PointObject beforeObj = getPointObject();
    		assertNotNull("积分对象不能为空", beforeObj);

    		// 保存初始积分值（而不是对象引用）
    		int beforeGrowScore = beforeObj.getGrowScore();
    		int beforeExchangeScore = beforeObj.getExchangeScore();
    		int beforeTotalScore = beforeObj.getScoreTotal();

    		System.out.println("测试开始前的积分状态:");
    		System.out.println("beforeGrowScore = " + beforeGrowScore);
    		System.out.println("beforeExchangeScore = " + beforeExchangeScore);
    		System.out.println("beforeTotalScore = " + beforeTotalScore);

    		testDesign.testDesign(1);

    		PointObject afterObj = getPointObject();
    		assertNotNull("积分对象不能为空", afterObj);

    		// 获取执行后的积分值
    		int afterGrowScore = afterObj.getGrowScore();
    		int afterExchangeScore = afterObj.getExchangeScore();
    		int afterTotalScore = afterObj.getScoreTotal();

    		// 调试信息
    		System.out.println("断言验证:");
    		System.out.println("beforeGrowScore = " + beforeGrowScore + ", afterGrowScore = " + afterGrowScore);
    		System.out.println("beforeExchangeScore = " + beforeExchangeScore + ", afterExchangeScore = " + afterExchangeScore);
    		System.out.println("beforeTotalScore = " + beforeTotalScore + ", afterTotalScore = " + afterTotalScore);

    		// TestDesign应该增加1分成长积分和1分可兑换积分，总共2分
    		assertEquals("成长积分应该从" + beforeGrowScore + "变为" + (beforeGrowScore + 1),
    		            beforeGrowScore + 1, afterGrowScore);
    		assertEquals("可兑换积分应该从" + beforeExchangeScore + "变为" + (beforeExchangeScore + 1),
    		            beforeExchangeScore + 1, afterExchangeScore);
    		assertEquals("总积分应该从" + beforeTotalScore + "变为" + (beforeTotalScore + 2),
    		            beforeTotalScore + 2, afterTotalScore);

    		System.out.println("testDesign测试通过");
    	}catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Test
    public void testPointDATABasicOperations() {
        try {
            // 确保测试开始时数据是干净的
            resetPointObject();

            PointObject beforeObj = getPointObject();
            assertNotNull("积分对象不能为空", beforeObj);

            // 保存初始积分值
            int beforeGrowScore = beforeObj.getGrowScore();
            int beforeExchangeScore = beforeObj.getExchangeScore();
            int beforeTotalScore = beforeObj.getScoreTotal();

            // 测试addScore方法
            pointDATA.addScore(5, 3);

            PointObject afterObj = getPointObject();
            assertNotNull("积分对象不能为空", afterObj);

            // 获取执行后的积分值
            int afterGrowScore = afterObj.getGrowScore();
            int afterExchangeScore = afterObj.getExchangeScore();
            int afterTotalScore = afterObj.getScoreTotal();

            // 验证积分增加
            assertEquals("成长积分应该增加5分", beforeGrowScore + 5, afterGrowScore);
            assertEquals("可兑换积分应该增加3分", beforeExchangeScore + 3, afterExchangeScore);
            assertEquals("总积分应该增加8分", beforeTotalScore + 8, afterTotalScore);

            System.out.println("PointDATA基本操作测试通过");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Configuration
    static class TestConfig {

        @Bean
        public PointDATA pointDATA() {
            return new MockPointDATAImpl();
        }

        @Bean
        public MockTestDesign mockTestDesign() {
            return new MockTestDesign();
        }

        @Bean
        public MockTestDesignController mockTestDesignController() {
            return new MockTestDesignController();
        }
    }
}
