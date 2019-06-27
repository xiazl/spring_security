package com.cims.framework.exception;

public class BizException extends Exception {

    /** 错误编码 **/
    protected String errorCode;

    public BizException(String errorCode){
        super(errorCode);
        this.errorCode = errorCode;
    }

    public BizException(String errorCode, Throwable cause){
        super(errorCode, cause);
        this.errorCode = errorCode;
    }

    public BizException(String errorCode, String message, Throwable cause){
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

