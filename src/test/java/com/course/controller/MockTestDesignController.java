package com.course.controller;

import com.course.service.MockTestDesign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mock版本的TestDesignController，用于测试
 * 使用MockTestDesign服务，避免数据库依赖
 */
@Component
public class MockTestDesignController {
    
    @Autowired
    private MockTestDesign mockTestDesign;
    
    public String testDesign(int id) {
        System.out.println("======Mock被拦截方法======");
        mockTestDesign.testDesign(id);
        return "ok";
    }
}
