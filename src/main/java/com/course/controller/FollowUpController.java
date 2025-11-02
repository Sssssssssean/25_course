package com.course.controller;

import com.course.model.Result;
import com.course.service.FollowUp;
import com.course.utils.Constant;
import com.course.utils.RequestHeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FollowUpController {
    @Autowired
    private FollowUp followUp;

    @GetMapping("/followUp")
    public Result followup(
            @RequestHeader(value = Constant.USER_HEADER_ID, required = false) String userIdFromHeader,
            @RequestParam(value = Constant.USER_HEADER_ID, required = false) String userIdFromParam){
        System.out.println("followup 执行");

        // 获取用户ID，优先从请求头获取，其次从参数获取
        String userId = userIdFromHeader;
        if (userId == null || userId.isEmpty()) {
            userId = userIdFromParam;
        }

        // 如果仍然没有用户ID，返回错误
        if (userId == null || userId.isEmpty()) {
            return Result.fail(400, "用户ID不能为空");
        }

        try {
            // 设置用户ID到ThreadLocal
            RequestHeaderUtil.setHeader(userId);
            System.out.println("FollowUp 用户ID: " + userId);

            followUp.followUp();
            return Result.success("FollowUp completed");
        } catch (Exception e) {
            System.err.println("FollowUp 执行失败: " + e.getMessage());
            return Result.fail(500, "门诊随访失败: " + e.getMessage());
        } finally {
            // 清理ThreadLocal
            RequestHeaderUtil.clear();
        }
    }
}
