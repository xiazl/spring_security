package com.cims.framework.response;

/**
 * @author baidu
 * @date 2018/8/18 下午1:03
 * @description 失败返回
 **/
public class FailedRespData<T> extends Result {

    private static final Boolean SUCCESS = false;

    private static final String MESSAGE = "操作失败";

    public FailedRespData() {
        this(MESSAGE);
    }

    public FailedRespData(String message) {
        this(SUCCESS,message);
    }

    public FailedRespData(Boolean success,String message) {
        this.setSuccess(success);
        this.setMessage(message);
    }

    public FailedRespData(T data) {
        this.setSuccess(SUCCESS);
        this.setData(data);
        this.setMessage(MESSAGE);
    }

}
