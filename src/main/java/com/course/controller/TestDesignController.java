package com.course.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.course.service.TestDesign;

import org.springframework.stereotype.Component;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
@Component
//测试案例
@RestController
public class TestDesignController {
    @Autowired
    private TestDesign testDesign;
    @PutMapping("/testDesign/{id}")
    public String testDesign(@PathVariable("id") int id){
        System.out.println("======被拦截方法======");
        testDesign.testDesign(id);
        return("ok");
    }
}

