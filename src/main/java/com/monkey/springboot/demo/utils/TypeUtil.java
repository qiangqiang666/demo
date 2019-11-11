package com.monkey.springboot.demo.utils;

/**
 * 描述:
 * 〈类型判定工具类〉
 *
 * @author Monkey
 * @create 2019/11/11 16:15
 */
public class TypeUtil {

    /**
     * 判断基本数据类型的包装类
     * @param obj
     * @return
     */
    public static boolean IsPackingType(Object obj) {
        if (obj instanceof Byte) {
            return true;
        } else if (obj instanceof Short) {
            return true;
        } else if (obj instanceof Integer) {
            return true;
        } else if (obj instanceof Long) {
            return true;
        } else if (obj instanceof Float) {
            return true;
        } else if (obj instanceof Double) {
            return true;
        } else if (obj instanceof Character) {
            return true;
        } else if (obj instanceof Boolean) {
            return true;
        } else if (obj instanceof String) {
            return true;
        }
        return false;
    }
}