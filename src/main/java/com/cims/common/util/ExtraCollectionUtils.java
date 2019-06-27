package com.cims.common.util;

import com.cims.framework.exception.AppException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author baidu
 * @date 2018/11/6 下午4:41
 * @description 集合处理工具类
 **/
public class ExtraCollectionUtils {

    /**
     * list 深复制
     * @param source
     * @param <T>
     * @return
     */
    public static <T> List<T> deepCopy(List<T> source) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(source);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            @SuppressWarnings("unchecked")
            List<T> dest = (List<T>) in.readObject();
            return dest;
        } catch (IOException e) {
            throw new AppException("IO exception",e);
        } catch (ClassNotFoundException e) {
            throw new AppException("Class not found exception",e);
        }
    }

    /**
     * 将一组数据固定分组，每组n个元素
     * @param source 要分组的数据源
     * @param n      每组n个元素
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> fixedGrouping(List<T> source, int n) {

        if (null == source || source.size() == 0 || n <= 0)
            return null;
        List<List<T>> result = new ArrayList<List<T>>();

        int sourceSize = source.size();
        int size = (source.size() / n);
        if (source.size() % n > 0){
            size = size + 1;
        }
        for (int i = 0; i < size; i++) {
            List<T> subset = new ArrayList<T>();
            for (int j = i * n; j < (i + 1) * n; j++) {
                if (j < sourceSize) {
                    subset.add(source.get(j));
                }
            }
            result.add(subset);
        }
        return result;
    }

}
