package com.jdkhome.jvmc.comment.exception;


import com.jdkhome.jvmc.comment.enums.ResponseError;

/**
 * Created by jdk on 17/4/25.
 * 业务异常,必须包含错误码和错误信息，controller层直接返回其错误码和错误信息
 * 内部错误，不需要包含错误码的错误交给Exception来处理
 *
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    //异常码
    private final Integer errorCode;

    //异常信息
    private final String errorMsg;


    public ResponseError getResponseError() {
        return responseError;
    }

    //返回给前端的错误信息
    private final ResponseError responseError;

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public ServiceException() {
        super();
        errorMsg = null;
        errorCode = null;
        responseError = null;
    }

    public ServiceException(ResponseError responseError) {
        super();
        this.responseError = responseError;
        this.errorMsg = responseError.getMsg();
        this.errorCode = responseError.getCode();
    }


    public ServiceException(Integer errorCode, String message) {
        super(message);
        this.errorMsg = message;
        this.errorCode = errorCode;
        responseError = null;
    }

    public ServiceException(Integer errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        responseError = null;
        errorMsg = null;
    }

    public ServiceException(Integer errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorMsg = message;
        this.errorCode = errorCode;
        responseError = null;
    }


}
