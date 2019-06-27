package com.cims.framework.aop;

import com.cims.framework.exception.BizException;
import com.cims.framework.response.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author baidu
 * @date 2018/8/16 下午1:30
 * @description 异常处理
 **/

@ControllerAdvice
public class ExceptionAdvice {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler({Throwable.class})
    @ResponseBody
    public Object handlerException(
            Throwable throwable) throws Throwable {

        if(throwable instanceof AccessDeniedException){
            throw throwable;  // 返回401到前端
        }

        if(throwable instanceof BizException){
            logger.warn("business exception：", throwable);
        } else {
            logger.error("system exception：", throwable);
        }

        Result result = new Result();

        if (throwable instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) throwable;
            List<String> list = new ArrayList<>();
            for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
                list.add(fieldError.getDefaultMessage());
            }
            result.setErrorMessage(StringUtils.join(list, ","));
        } else if (throwable instanceof BindException){
            BindException e = (BindException) throwable;
            List<String> list = new ArrayList<>();
            for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
                list.add(fieldError.getDefaultMessage());
            }
            result.setErrorMessage(StringUtils.join(list, ","));        }
        else{
            result.setErrorMessage(throwable.getMessage());
        }
        return result;
    }
}

