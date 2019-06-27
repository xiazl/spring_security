package com.cims.common.util;

import com.cims.common.enums.BaseEnum;

/**
 * @author baidu
 * @date 2018/11/1 下午3:18
 * @description
 **/
public abstract class EnumUtils {

    /**
     * 枚举值转换成显示值
     * @param clazz
     * @param backValue
     * @param <E>
     * @return
     */
    public final static <E extends Enum<E>> E getEnum(Class<E> clazz, Integer backValue) {
        if (clazz == null || backValue == null) {
            return null;
        }
        if (isImplementsBaseEnum(clazz)) {
            E[] enums = clazz.getEnumConstants();
            for (E e : enums) {
                if (((BaseEnum<?>) e).getBackValue().intValue() == backValue) {
                    return e;
                }
            }
            throw new IllegalArgumentException("未识别的枚举内容："+backValue);
        } else {
            throw new IllegalArgumentException("未识别的枚举类");
        }
    }

    /**
     * 显示值转换枚举值
     * @param clazz
     * @param viewValue
     * @param <E>
     * @return
     */
    public final static <E extends Enum<E>> E getEnum(Class<E> clazz, String viewValue) {
        if (clazz == null || viewValue == null) {
            return null;
        }
        if (isImplementsBaseEnum(clazz)) {
            E[] enums = clazz.getEnumConstants();
            for (E e : enums) {
                if (((BaseEnum<?>) e).getViewValue().equals(viewValue)) {
                    return e;
                }
            }
            throw new IllegalArgumentException("未识别的枚举内容："+viewValue);
        } else {
            throw new IllegalArgumentException("未识别的枚举类");
        }
    }

    /**
     * 是否继承了枚举基类
     * @param clazz
     * @param <E>
     * @return
     */
    public static <E extends Enum<E>> Boolean isImplementsBaseEnum(Class<E> clazz) {
        Class<?>[] interfaces = clazz.getInterfaces();
        for (Class<?> c : interfaces) {
            if (c.toString().equals(BaseEnum.class.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 枚举类是否包含某个值
     * @param clazz
     * @param <E>
     * @param viewValue 值
     * @return
     */
    public static <E extends Enum<E>> Boolean isContainValue(Class<E> clazz,String viewValue) {
        if (clazz == null || viewValue == null) {
            return false;
        }
        if (isImplementsBaseEnum(clazz)) {
            E[] enums = clazz.getEnumConstants();
            for (E e : enums) {
                if (((BaseEnum<?>) e).getViewValue().equals(viewValue)) {
                    return true;
                }
            }
            return false;
        } else {
            throw new IllegalArgumentException("未识别的枚举类");
        }
    }


}
