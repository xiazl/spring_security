package com.cims.common.excel;

import com.cims.common.excel.annotation.ExcelTitle;
import com.cims.common.util.DateUtils;
import com.cims.framework.exception.AppException;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author baidu
 * @date 2019-04-22 18:28
 * @description Excel文件读取解析
 **/
public class ExcelReader {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExcelReader.class);

    /**
     *
     * @param clazz 类型
     * @param in 字节输入流
     * @param rowOffset 开始行
     * @param colOffset 开始列
     * @param <E> 范型类
     * @return
     * @throws IOException
     */
    public static <E> List<E> readExcel(Class<E> clazz, InputStream in, int rowOffset, int colOffset) throws IOException {
        return stringArray2List(clazz, readExcel(in, rowOffset, colOffset));
    }


    /**
     * 将读取的数据映射到对应的字段
     * @param clazz 类型
     * @param strArray 数据
     * @param <E>
     * @return
     */
    private static <E> List<E> stringArray2List(Class<E> clazz, String[][] strArray) {

        List<E> list = new ArrayList<>(strArray.length);

        int maxOrder = 0;
        int maxColsCount = 0;

        try {

            Method[] methods = clazz.getMethods();

            SortedMap<Integer, ExcelReader.ExcelTitleSetterPair> columnTitles = new TreeMap<>();

            for(Method m : methods){

                ExcelTitle excelTitle = ExcelWriter.getAnnotationFromDeclaredFieldOrMethod(clazz, m, "set");

                if(excelTitle != null && excelTitle.enable()){

                    columnTitles.put(excelTitle.order(), new ExcelReader().new ExcelTitleSetterPair(excelTitle.value(), m));
                } else{
                    continue;
                }
            }

            columnTitles = Collections.synchronizedSortedMap(columnTitles); // 按ExcelTitle.order排序

            for(int rowCount = 0; rowCount < strArray.length; rowCount++){ // 一次循环添加某一行内容

                E e = clazz.newInstance();

                for(Iterator<Integer> it = columnTitles.keySet().iterator(); it.hasNext(); ){ // 一次循环添加一行的某一列

                    Integer order =  it.next();

                    maxOrder = order > maxOrder ? order : maxOrder;
                    maxColsCount = strArray[rowCount].length > maxColsCount ? strArray[rowCount].length : maxColsCount;

                    Method method = columnTitles.get(order).getSetter();

                    String cellValue = strArray[rowCount][order];

                    if(cellValue == null){
                        continue;
                    }
                    method.invoke(e, getValueByParameterType(method,cellValue));
                }
                list.add(e);
            }

        }catch (Exception e) {
            if(e instanceof InvocationTargetException){
                InvocationTargetException targetException = (InvocationTargetException) e;
                throw new AppException(targetException.getTargetException().getMessage(),e);
            }
            throw new AppException("无法正确读取数据，请检查所填数据的合法性（空格和非法字符会造成数据读取失败）", e);
        }
//        if(maxColsCount - maxOrder != 1){
//            LOGGER.error("模板有误，最大列数 {}，实体类ExcelTitle.order最大值 {}，前者-后者应等于1", maxColsCount, maxOrder);
//            throw new AppException("导入失败，请检查模板或数据是否正确！（Excel解析出错，额外插入的空列或空格均会影响数据读取的正确性，建议重新下载模板）");
//        }

        return list;
    }



    /**
     *
     * @param in 字节输入流
     * @param rowOffset 从rowOffset行开始读取数据
     * @param colOffset 从colOffset列开始读取数据
     * @return
     * @throws IOException
     */
    public static String[][] readExcel(InputStream in, int rowOffset, int colOffset) throws IOException {

        if(in == null){
            return null;
        }

        XSSFWorkbook hssfWorkbook = new XSSFWorkbook(in);
        XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);

        int rows = hssfSheet.getLastRowNum() + 1 - rowOffset;
        int cols = hssfSheet.getRow(rowOffset > 0 ? rowOffset-1 : 0).getLastCellNum(); // 应以标题的列数为准

        String[][] contentsArray = new String[rows][cols];

        for (int i = 0 ; i < contentsArray.length; i++) {
            // 获取第i行，第j列的值
            XSSFRow xssfRow = hssfSheet.getRow(i+rowOffset);
            if(xssfRow == null){
                continue;
            }
            for (int j = 0 + colOffset; j < contentsArray[i].length; j++) {
                XSSFCell xssfCell = xssfRow.getCell(j);
                if(xssfCell == null){
                    continue;
                }
                contentsArray[i][j] = getCellValueAsString(xssfCell);
            }
        }
        in.close();
        return contentsArray;
    }


    /**
     *  获取但单元个内容
     * @param cell
     * @return
     */
    private static String getCellValueAsString(XSSFCell cell){

        String v = "";

        switch (cell.getCellType()) {
            case NUMERIC: // 数字
                // 时间
                if (DateUtil.isCellDateFormatted(cell)){
                    Date date = cell.getDateCellValue();
                    v = DateUtils.format(date, "yyyy/MM/dd");
                } else {
                    double dv = cell.getNumericCellValue();
                    long lv = new BigDecimal(String.valueOf(dv)).longValue();
                    if(Double.compare(dv, lv) == 0){
                        v = String.valueOf(lv);
                    } else {
                        v = String.valueOf(dv);
                    }
                }
                break;
            case STRING: // 字符串
                v = String.valueOf(cell.getStringCellValue());
                break;
            default:
                break;
        }
        return v;
    }

    /**
     * 值转换为对象setter方法对应值类型
     * @param setter
     * @param value
     * @return
     */
    private static Object getValueByParameterType(Method setter, String value){
        if (StringUtils.isEmpty(value)){
            return null;
        }
        Class clazz = setter.getParameterTypes()[0];
        Object object;
        if(clazz == BigDecimal.class){
            object = new BigDecimal(value);
        } else if(clazz == Date.class){
            object = DateUtils.parse(value, "yyyy/MM/dd");
        } else if(clazz == Integer.class){
            object = Integer.valueOf(value);
        } else if(clazz == Long.class){
            object = Long.valueOf(value);
        } else {
            object = value;
        }

        return object;
    }


    /**
     * “Excel标题”-“set方法”配对
     */
    private class ExcelTitleSetterPair {

        private String title;
        private Method setter;

        public ExcelTitleSetterPair(String title, Method setter) {
            this.title = title;
            this.setter = setter;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Method getSetter() {
            return setter;
        }

        public void setSetter(Method setter) {
            this.setter = setter;
        }

    }
}
