package com.course.controller;
import com.course.model.Result;
import com.course.service.FillInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
@Component
//填写个人资料
@RestController
@RequestMapping("/information")
public class FillInformationController {

    @Autowired
    private FillInformation fillInformationService;

    // 完善个人资料
    @PostMapping("/fill")
    public Result<String> fillInfor(){
        System.out.println("======被拦截的fillInformation方法执行======");
        fillInformationService.fillInformation();
        return Result.success("fillInformation completed");
    }
}
