package com.course.controller;
import com.course.model.Result;
import com.course.service.BfzNote;
import com.course.utils.Constant;
import com.course.utils.RequestHeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
@Component
//填写并发症记录
@RestController
public class BfzNoteController {
    @Autowired
    private BfzNote bfzNote;

    @PostMapping("/bfzNode")
    public Result<String> BfzNode(
            @RequestHeader(value = Constant.USER_HEADER_ID, required = false) String userIdFromHeader,
            @RequestParam(value = Constant.USER_HEADER_ID, required = false) String userIdFromParam){
        System.out.println("======被拦截的bfzNote方法执行======");

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
            System.out.println("BfzNote 用户ID: " + userId);

            bfzNote.BfzNode();
            return Result.success("BfzNote completed");
        } catch (Exception e) {
            System.err.println("BfzNote 执行失败: " + e.getMessage());
            return Result.fail(500, "并发症记录失败: " + e.getMessage());
        } finally {
            // 清理ThreadLocal
            RequestHeaderUtil.clear();
        }
    }
}