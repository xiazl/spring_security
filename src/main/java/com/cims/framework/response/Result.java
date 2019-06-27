package com.cims.framework.response;

/**
 * @author baidu
 * @date 2018/8/18 下午1:05
 * @description Api请求返回结构
 **/

public class Result<T> {
    // 返回状态
    private Boolean success = true;
    // 返回message
    private String message;
    // 数据返回
    private T data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setErrorMessage(String message) {
        this.success = false;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
