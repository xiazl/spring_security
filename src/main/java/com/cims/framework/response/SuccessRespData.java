package com.cims.framework.response;

/**
 * @author baidu
 * @date 2018/8/18 下午1:03
 * @description 成功返回
 **/

public class SuccessRespData<T> extends Result {

    public static final String MESSAGE = "操作成功";


    public SuccessRespData() {
        this(null);
    }

    public SuccessRespData(T data) {
        this(data,MESSAGE);
    }

    public SuccessRespData(T data, String message) {
        this.setData(data);
        this.setMessage(message);
    }
}
