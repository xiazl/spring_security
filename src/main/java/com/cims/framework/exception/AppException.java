package com.cims.framework.exception;

public class AppException extends RuntimeException{


    private static final long serialVersionUID = 1403920268808583220L;

    /** 错误编码 **/
    private String errorCode;

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public AppException(String errorCode, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
    }

    public AppException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
