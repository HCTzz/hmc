package com.ctt.constant;

/**
 * @author: hxy
 * @date: 2017/10/24 10:16
 */
public enum SystemStatusEnum {
    /*
     * 错误信息
     * */
    E_100(100, "登陆成功"),
    E_400(400, "请求处理异常，请稍后再试"),
    E_501(501, "请求路径不存在"),
    E_503(503, "参数/参数解析错误"),
    E_500(500, "请求方式有误,请检查 GET/POST"),
    E_502(502, "权限不足"),
    E_10008(10008, "角色删除失败,尚有用户属于此角色"),
    E_10009(10009, "账户已存在"),
    E_10010(10010, "账户或密码错误"),
    E_20011(20011, "登陆已过期,将跳转到登陆页面..."),
    E_20012(20012, "账号状态错误"),
    E_90003(90003, "缺少必填参数"),
    E_20014(20014, "您已在另一台设备登录，本次登录已下线!"),
    E_20000(20000, "请求成功");

    private int code;

    private String message;

    SystemStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}