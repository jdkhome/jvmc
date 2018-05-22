package com.jdkhome.jvmc.io.msg;

import com.jdkhome.jvmc.comment.enums.ResponseError;
import com.jdkhome.jvmc.comment.exception.ServiceException;

/**
 * author link.ji
 * createTime 下午6:22 2018/5/22
 */
public class BaseMsg {
    private int code;
    private String msg;
    private Object data;

    private static final int SUCCESS_CODE = 200;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public BaseMsg() {
        this.code = 0;
        this.msg = "";
        this.data = null;
    }

    public BaseMsg(int code) {
        this.code = code;
        this.msg = (code == SUCCESS_CODE) ? "success" : msg;
        this.data = null;
    }

    public BaseMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    public BaseMsg(ResponseError responseError) {
        this.code = responseError.getCode();
        this.msg = responseError.getMsg();
        this.data = null;
    }


    /**
     * 返回成功响应
     */
    public static BaseMsg successResponse(Object data) {

        BaseMsg response = new BaseMsg(SUCCESS_CODE);
        if (data != null) {
            response.data = data;
        } else {
            response.data = null;
        }
        return response;
    }

    /**
     * 返回成功响应
     */
    public static BaseMsg successResponse() {

        return successResponse(null);
    }

    /**
     * 返回指定错误类型的响应
     */
    public static BaseMsg responseWithServiceError(ServiceException se) {
        BaseMsg response = new BaseMsg();
        response.code = se.getErrorCode();
        response.msg = se.getErrorMsg();
        response.data = null;
        return response;
    }


}
