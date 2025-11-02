package com.course.controller;

import com.course.model.Result;
import com.course.service.EvaluateReport;
import com.course.utils.Constant;
import com.course.utils.RequestHeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
//生成评估报告
@RestController
public class EvaluateReportController {
    @Autowired
    private EvaluateReport evaluateReport;

    @GetMapping("/evaluateReport")
    public Result<String> evaluateReport(
            @RequestHeader(value = Constant.USER_HEADER_ID, required = false) String userIdFromHeader,
            @RequestParam(value = Constant.USER_HEADER_ID, required = false) String userIdFromParam) {
        System.out.println("======被拦截的evaluateReport方法执行======");

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
            System.out.println("EvaluateReport 用户ID: " + userId);

            evaluateReport.evaluate();
            return Result.success("evaluateReport completed");
        } catch (Exception e) {
            System.err.println("EvaluateReport 执行失败: " + e.getMessage());
            return Result.fail(500, "生成评估报告失败: " + e.getMessage());
        } finally {
            // 清理ThreadLocal
            RequestHeaderUtil.clear();
        }
    }
}






