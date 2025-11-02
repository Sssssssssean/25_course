package com.course.controller;
import com.course.model.Result;
import com.course.service.Login;
import com.course.utils.RequestHeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
@Component
//登录平台
@RestController
@RequestMapping("/account")
public class LoginController {

    @Autowired
    private Login loginService;
    // 登录平台
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public Result login(@RequestParam(required = false, name = "X-userId") String id){
        System.out.println("======被拦截的login方法执行======");
        if (id == null || id.isEmpty()) {
            return Result.fail(400, "用户ID不能为空");
        }
        // 设置用户ID到RequestHeaderUtil
        RequestHeaderUtil.setHeader(id);
        loginService.login();
        return Result.success("登录成功");
    }
}