package com.course.utils;

public class RequestHeaderUtil {
    private static final ThreadLocal<String> threadLocalHeader = new ThreadLocal<>();

    public static void setHeader(String headerValue) {
        threadLocalHeader.set(headerValue);
    }

    public static String getHeader() {
        return threadLocalHeader.get();
    }

    public static void clear() {
        threadLocalHeader.remove();
    }
}
