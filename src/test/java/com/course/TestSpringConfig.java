package com.course;

import com.course.dao.PointDATA;
import com.course.dao.MockPointDATAImpl;
import com.course.pojo.PointObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * 测试Spring配置是否正确
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestSpringConfig.TestConfig.class})
public class TestSpringConfig {

    @Autowired
    PointDATA pointDATA;

    @Test
    public void testPointDATAInjection() {
        System.out.println("测试PointDATA依赖注入...");
        assertNotNull("PointDATA不能为空", pointDATA);

        System.out.println("PointDATA类型: " + pointDATA.getClass().getName());

        // 测试基本功能
        PointObject pointObject = pointDATA.getPoint();
        assertNotNull("PointObject不能为空", pointObject);

        // 测试积分操作
        int beforeGrow = pointObject.getGrowScore();
        int beforeExchange = pointObject.getExchangeScore();

        pointDATA.addScore(1, 2);

        PointObject afterObject = pointDATA.getPoint();
        assertEquals("成长积分应该增加1", (long)(beforeGrow + 1), (long)afterObject.getGrowScore());
        assertEquals("可兑换积分应该增加2", (long)(beforeExchange + 2), (long)afterObject.getExchangeScore());

        System.out.println("PointDATA依赖注入和基本功能测试通过!");
    }

    @Configuration
    static class TestConfig {

        @Bean
        public PointDATA pointDATA() {
            return new MockPointDATAImpl();
        }
    }
}
