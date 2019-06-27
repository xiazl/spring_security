package com.cims.framework.response;

/**
 * @author baidu
 * @date 2018/8/18 下午1:09
 * @description Api返回调用
 **/

public class ResponseData {

    public static <T> SuccessRespData<T> success(){
        return new SuccessRespData<T>();
    }

    public static <T> SuccessRespData<T> success(T t){
        return new SuccessRespData<T>(t);
    }

    public static <T> SuccessRespData<T> success(T t, String message){
        return new SuccessRespData<T>(t,message);
    }

    public static <T> FailedRespData<T> failed(){
        return new FailedRespData<T>();
    }

    public static <T> FailedRespData<T> failed(String message){
        return new FailedRespData<T>(message);
    }

    public static <T> FailedRespData<T> failed(T t){
        return new FailedRespData<T>(t);
    }

}
