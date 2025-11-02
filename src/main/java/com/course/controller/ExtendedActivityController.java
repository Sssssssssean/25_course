package com.course.controller;

import com.course.model.Result;
import com.course.service.ExtendedActivity;
import com.course.utils.Constant;
import com.course.utils.RequestHeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.course.model.Result;
import org.springframework.stereotype.Component;

/**
 * 控制器组件：用于处理用户参与扩展活动的操作。
 * 示例方法仅用于模拟行为，可结合 AOP 或其他机制进行拦截测试。
 */
@RestController
public class ExtendedActivityController {

    @Autowired
    private ExtendedActivity extendedActivity;

    @GetMapping("/extendedActivity")
    public Result extendedActivity(
            @RequestHeader(value = Constant.USER_HEADER_ID, required = false) String userIdFromHeader,
            @RequestParam(value = Constant.USER_HEADER_ID, required = false) String userIdFromParam) {
        System.out.println("======被拦截的extendedActivity方法执行======");

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
            System.out.println("ExtendedActivity 用户ID: " + userId);

            extendedActivity.extendedActivity();
            return Result.success("参加扩展活动成功");
        } catch (Exception e) {
            System.err.println("ExtendedActivity 执行失败: " + e.getMessage());
            return Result.fail(500, "参加扩展活动失败: " + e.getMessage());
        } finally {
            // 清理ThreadLocal
            RequestHeaderUtil.clear();
        }
    }

}