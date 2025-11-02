package com.course.controller;

import com.course.dao.PointDATA;
import com.course.pojo.PointObject;
import com.course.model.Result;
import com.course.utils.Constant;
import com.course.utils.RequestHeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 积分数据控制器
 * 提供积分相关的REST API接口
 */
@RestController
@RequestMapping("/points")
public class PointsController {

    @Autowired
    private PointDATA pointDATA;

    /**
     * 获取当前用户的积分详情
     * 从ThreadLocal中获取用户ID
     */
    @GetMapping("/current")
    public Result<PointObject> getCurrentUserPoints(
            @RequestHeader(value = Constant.USER_HEADER_ID, required = false) String userIdFromHeader,
            @RequestParam(value = Constant.USER_HEADER_ID, required = false) String userIdFromParam) {
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
            System.out.println("getCurrentUserPoints 用户ID: " + userId);

            PointObject pointObject = pointDATA.getPoint();
            return Result.success(pointObject);
        } catch (Exception e) {
            return Result.fail(500, "获取积分数据失败: " + e.getMessage());
        } finally {
            // 清理ThreadLocal
            RequestHeaderUtil.clear();
        }
    }

    /**
     * 根据用户ID获取积分详情
     */
    @GetMapping("/user/{id}")
    public Result<PointObject> getUserPointsById(@PathVariable("id") Integer id) {
        try {
            PointObject pointObject = pointDATA.getPointObjectById(id);
            return Result.success(pointObject);
        } catch (Exception e) {
            return Result.fail(500, "获取积分数据失败: " + e.getMessage());
        }
    }
}
