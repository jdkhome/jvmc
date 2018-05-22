package com.jdkhome.jvmc.comment.enums;

/**
 * Created by jdk on 17/4/24.
 * 错误枚举，错误码和错误信息定义到这里,注意，这是面向(视图层)用户的错误提示
 */
public enum ResponseError {

    //===业务

    REQUEST_HAND(520,"请求握手"),

    //==============================================业务返回=======

    ALREADY_CONNECT(600001, "已经建立连接"),
    NODE_NO_FOUND(600002, "节点不存在"),

    //===========通用返回==============
    NO_LOGIN(100,"请先登录"),

    SUCCESS(200,"成功"),

    PARAMETER_ERROR(400,"参数错误"),

    NO_PERMISSION(403,"没有权限"),

    SERVER_ERROR(500,"未知错误"),

    UPSTREAM_ERROR(600,"上游错误");


    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ResponseError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
