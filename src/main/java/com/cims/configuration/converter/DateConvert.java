package com.cims.configuration.converter;

import com.cims.common.util.DateUtils;
import com.cims.framework.exception.AppException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author baidu
 * @date 2019/4/14 下午2:35
 * @description
 **/

@Configuration
public class DateConvert {

    @Bean
    public Converter<String, Date> addNewConvert() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                if (StringUtils.isEmpty(source)){
                    return null;
                }
                SimpleDateFormat format;
                if(source.length() == DateUtils.YY_MM_DD.length()) {
                    format = new SimpleDateFormat(DateUtils.YY_MM_DD);
                } else {
                    format = new SimpleDateFormat(DateUtils.YY_MM_DD_HH_MM_SS);
                }
                Date date = null;
                try {
                    date = format.parse(source);
                } catch (Exception e) {
                    throw new AppException("时间转换失败");
                }
                return date;
            }
        };
    }
}
