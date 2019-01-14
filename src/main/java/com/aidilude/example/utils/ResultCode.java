package com.aidilude.example.utils;

public enum ResultCode
{

    Ok(1),

    Error(0),

    NotLogin(120),   //登录过期

    IllegalAccess(110),  //非法访问

    NoAuthorize(119),  //没有权限

    InvalidParam(403),   //参数非法

    NotFind(404);   //无法找到

    private final int value;

    public int getValue() {
        return this.value;
    }

    private ResultCode(int value) {
        this.value = value;
    }

}