package com.course.controller;
import com.course.model.Result;
import com.course.service.ResearchRecruitment;
import com.course.utils.Constant;
import com.course.utils.RequestHeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.stereotype.Component;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
@Component
//参加科研招募
@RestController
public class ResearchRecruitmentController {
    @Autowired
    private ResearchRecruitment researchRecruitment;

    @GetMapping("/researchRecruitment")
    public Result researchrecruitment(
            @RequestHeader(value = Constant.USER_HEADER_ID, required = false) String userIdFromHeader,
            @RequestParam(value = Constant.USER_HEADER_ID, required = false) String userIdFromParam) {
        System.out.println("======被拦截的researchRecruitment方法执行======");

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
            System.out.println("ResearchRecruitment 用户ID: " + userId);

            researchRecruitment.researchRecruitment();
            return Result.success("researchRecruitment completed");
        } catch (Exception e) {
            System.err.println("ResearchRecruitment 执行失败: " + e.getMessage());
            return Result.fail(500, "科研招募失败: " + e.getMessage());
        } finally {
            // 清理ThreadLocal
            RequestHeaderUtil.clear();
        }
    }
}