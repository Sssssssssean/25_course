package com.course.model;

import java.io.Serializable;

public class Result<DATA> implements Serializable {
    public Integer code; // 返回码，0 表示成功，其他值表示错误
    public String message; // 返回信息，描述操作结果或错误原因
    public DATA data; // 返回的数据对象，可以是任何类型的数据

    public static <T> Result<T> success(T data) {
        return new Result<T>(200, "", data);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(500, msg, null);
    }

    public Result(int code, String message, DATA data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
