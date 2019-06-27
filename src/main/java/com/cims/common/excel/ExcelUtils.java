package com.cims.common.excel;

import com.cims.common.excel.annotation.ExcelTitle;
import com.cims.common.util.DateUtils;
import com.cims.framework.exception.AppException;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author baidu
 * @date 2018/10/22 下午3:47
 * @description excel工具转换类
 **/
public class ExcelUtils {

    private ExcelUtils() {
    }

    private final static Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * 将list数据对象中有@ExcelTitle注解的字段转数组
     * @param list
     * @param <E>
     * @return
     */
    public static <E> String[][] list2StringArray(List<E> list){

        if(list == null || list.isEmpty()){
            return new String[][]{};
        }

        Method[] methods = list.get(0).getClass().getMethods();

        SortedMap<Integer, ExcelTitleGetterPair> columnTitles = new TreeMap<>();

        for(Method m : methods){

            ExcelTitle excelTitle = ExcelWriter.getAnnotationFromDeclaredFieldOrMethod(list.get(0).getClass(), m, "get");

            if(excelTitle != null && excelTitle.enable()){

                columnTitles.put(excelTitle.order(), new ExcelUtils().new ExcelTitleGetterPair(excelTitle.value(), m)); // order作为key，title-getter键值对作为value
            } else{
                continue;
            }
        }

        columnTitles = Collections.synchronizedSortedMap(columnTitles); // 按ExcelTitle.order排序

        String[][] result = new String[list.size()][columnTitles.size()];
        int columnCount = 0;
        Iterator<Integer> it = columnTitles.keySet().iterator();
        while(it.hasNext()){

            ExcelTitleGetterPair excelTitleGetterPair = columnTitles.get(it.next());

            for(int rowCount = 0; rowCount < list.size(); rowCount++){ // 一次循环添加一列内容
                String value = "";
                try{
                    Object valueObject = excelTitleGetterPair.getGetter().invoke(list.get(rowCount));
                    if(valueObject instanceof Date){
                        value = DateUtils.format((Date) valueObject, DateUtils.YY_MM_DD_HH_MM_SS); // TODO hard code
                    } else if(valueObject instanceof Double || valueObject instanceof BigDecimal){
                        value = removeRedundantZero(String.valueOf(valueObject)); // 如果是Double/BigDecimal，去除小数点后多余的0
                    } else{
                        value = String.valueOf(valueObject != null ? valueObject : "");
                    }
                } catch (Exception e){
                    throw new AppException("导出解析失败",e);
                }
                result[rowCount][columnCount] = value;
            }
            columnCount++;
        }

        return result;
    }

    /**
     * excel导出回写流文件
     * @param wb
     * @param fileName
     * @param response
     */
    public static void writeExcelResponse(SXSSFWorkbook wb, String fileName, HttpServletResponse response) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            wb.write(os);
        } catch (IOException e) {
            throw new AppException("导出失败",e);
        }
        byte[] content = os.toByteArray();
        InputStream inputStream = new ByteArrayInputStream(content);

        writeResponse(inputStream,fileName,response);
    }



    /**
     * 文件下载
     * @param inputStream
     * @param fileName
     * @param response
     */
    public static void writeResponse(InputStream inputStream, String fileName, HttpServletResponse response){
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        fileName += ".xlsx";

        try {
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1"));
            // 自定义header字段(filename)，用UTF-8编码，前端再解码，以解决中文乱码问题
            response.setHeader("filename", URLEncoder.encode(fileName, "UTF-8").replace("+", "%20"));
            ServletOutputStream out = response.getOutputStream();

            bis = new BufferedInputStream(inputStream);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bos.flush();
        } catch (Exception e) {
            LOGGER.error("文件流回写错误",e);
        } finally {
            if (bis != null)
                try {
                    bis.close();
                } catch (IOException e) {
                    throw new AppException("导出失败",e);
                }
            if (bos != null)
                try {
                    bos.close();
                } catch (IOException e) {
                    throw new AppException("导出失败",e);
                }
        }
    }

    /**
     * 省略多余的0，如，将1.12000处理为1.12
     * @param str
     * @return
     */
    private static String removeRedundantZero(String str) {
        if(str == null){
            return "";
        }

        if(str != null && str.matches("^-?[0-9]{1,}.[0-9]{1,}$") && str.indexOf('.') > 0){

            StringBuilder buffer = new StringBuilder(str);

            try {
                while(buffer.charAt(buffer.length() - 1) == '0'){
                    buffer = buffer.deleteCharAt(buffer.length() - 1);
                }
                while(buffer.charAt(buffer.length() - 1) == '.'){
                    buffer = buffer.deleteCharAt(buffer.length() - 1);
                }
            } catch (Exception e) {
                throw new AppException("转换数据出错", e);
            }

            str = buffer.toString();
        }
        return str;
    }


    private class ExcelTitleGetterPair {

        private String title;
        private Method getter;

        public ExcelTitleGetterPair(String title, Method getter) {
            this.title = title;
            this.getter = getter;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Method getGetter() {
            return getter;
        }

        public void setGetter(Method getter) {
            this.getter = getter;
        }

    }



}
