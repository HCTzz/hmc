package com.ctt.constant;

/**
 * 用户状态
 */
public enum UserStatusEnum {

    S_1(1, "正常"),
    S_2(2, "您的账号已过期"),
    S_3(3, "您的账号已被限制登陆"),
    S_5(5, "账号不存在"),
    ;

    private int status;

    private String message;

    UserStatusEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public int getStatus() {
        return this.status;
    }
}
