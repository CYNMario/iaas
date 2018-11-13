package com.han.iaas.enums;

public enum ResultEnum {

    SUCCESS(200, "操作成功！"),
    ERROR(401, "操作失败！"),
    LOGIN_SUCCESS(201, "登录成功！"),
    LOGIN_ERROR_PASSWORD(1001, "登录失败:密码错误！"),
    LOGIN_ERROR_USER_NOT_EXIST(1002, "登录失败:用户不存在！"),
    VALI_ERROR(1003, "验证码错误！"),
    USER_EXIST(1004, "用户已存在！"),
    BEYOUND_AMOUNT_LIMIT(1005,"超出数量上限！"),
    LOGIN_TIMEOUT(1006,"登录超时"),
    VALI_TIMEOUT(1007,"验证码失效,请重新获取！"),
    USER_NOT_EXIST(1008,"身份验证失败——用户不存在！"),
    LOGIN_ERROR_USER_LOGGED(1009,"该用户已经登录"),
    ;

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
