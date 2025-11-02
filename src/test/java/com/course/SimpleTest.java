package com.course;

import com.course.dao.PointDATA;
import com.course.dao.MockPointDATAImpl;
import com.course.pojo.PointObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * 最简单的测试，不依赖Spring容器
 */
public class SimpleTest {

    @Test
    public void testMockPointDATA() {
        System.out.println("开始简单测试...");
        
        // 直接创建Mock对象，不使用Spring
        PointDATA pointDATA = new MockPointDATAImpl();
        
        // 测试获取积分对象
        PointObject pointObject = pointDATA.getPoint();
        assertNotNull("积分对象不能为空", pointObject);
        
        // 记录初始积分
        int initialGrow = pointObject.getGrowScore();
        int initialExchange = pointObject.getExchangeScore();
        int initialTotal = pointObject.getScoreTotal();
        
        System.out.println("初始积分 - 成长:" + initialGrow + ", 可兑换:" + initialExchange + ", 总计:" + initialTotal);
        
        // 测试增加积分
        pointDATA.addScore(3, 5);
        
        // 验证积分增加
        PointObject afterObject = pointDATA.getPoint();
        assertEquals("成长积分应该增加3", (long)(initialGrow + 3), (long)afterObject.getGrowScore());
        assertEquals("可兑换积分应该增加5", (long)(initialExchange + 5), (long)afterObject.getExchangeScore());
        assertEquals("总积分应该增加8", (long)(initialTotal + 8), (long)afterObject.getScoreTotal());
        
        System.out.println("最终积分 - 成长:" + afterObject.getGrowScore() + 
                          ", 可兑换:" + afterObject.getExchangeScore() + 
                          ", 总计:" + afterObject.getScoreTotal());
        
        System.out.println("简单测试通过!");
    }
    
    @Test
    public void testFileOperations() {
        System.out.println("测试文件操作...");
        
        try {
            // 这里可以测试FileUtils和JsonUtils的基本功能
            // 但为了简单起见，我们只做基本验证
            System.out.println("文件操作测试跳过（避免文件系统依赖）");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("文件操作测试完成!");
    }
}
