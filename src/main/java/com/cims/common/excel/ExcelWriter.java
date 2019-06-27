package com.cims.common.excel;

import com.cims.common.excel.annotation.ExcelTitle;
import com.cims.framework.exception.AppException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;


/**
 * @author belly
 * @create 2018/3/26 下午9:07
 */
public class ExcelWriter {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExcelWriter.class);

    /**
     * 从属性或方法上获取ExcelTitle注解
     * @param clazz
     * @param method
     * @return
     */
    public static ExcelTitle getAnnotationFromDeclaredFieldOrMethod(Class<?> clazz, Method method, String getOrSet){

        String getterName = method.getName();

        if(!getterName.startsWith(getOrSet) && (method.getParameterTypes() != null || method.getParameterTypes().length <= 0)){
            return null;
        } else if(getterName.equals("getClass")){
            return null;
        }

        ExcelTitle annotation = method.getAnnotation(ExcelTitle.class); // 注解声明在getter上，如此可继承父类的注解
        if(annotation == null){ //支持注解声明在本类的field上
            Field field = null;
            String uncapitalizedFieldName = StringUtils.uncapitalize(getterName.substring(3));
            try {
                field = clazz.getDeclaredField(uncapitalizedFieldName); // 获取fieldName，如果field首字母小写
                if(field == null){
                    field = clazz.getDeclaredField(method.getName().substring(3)); // 获取fieldName，如果field首字母大写
                }
            } catch (NoSuchFieldException e) {
                LOGGER.error("获取{}的父类属性{} clazz = clazz.getSuperclass() field", clazz.getName(), uncapitalizedFieldName);
                throw new AppException("No such field exception",e);
            }
            if(field != null){
                annotation = field.getAnnotation(ExcelTitle.class);
            }
        }
        return annotation;
    }


    /**
     * 生成Excel文本
     * @param inputStream
     * @param data
     * @return
     */
    public static <T> SXSSFWorkbook getDataForExcel(InputStream inputStream, List<T> data){
        String[][] values = ExcelUtils.list2StringArray(data);

        SXSSFWorkbook sxb = null;
        try {
            XSSFWorkbook wb = new XSSFWorkbook(inputStream);
            sxb = new SXSSFWorkbook(wb);
        } catch (IOException e) {
            throw new AppException("Excel read error",e);
        }

        SXSSFSheet sheet = sxb.getSheetAt(0);

        // 循环将数据写入Excel
        SXSSFRow row;
        for (int i = 0; i < values.length; i++) {

            row = sheet.createRow(i + 1);
            for(int j = 0;j < values[i].length;j++) {
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return sxb;
    }




}
