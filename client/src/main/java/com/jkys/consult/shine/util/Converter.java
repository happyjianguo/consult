package com.jkys.consult.shine.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 对象转换,要求数据类型且name相同的才能互转
 *
 * @author yangZh
 * @since 2018/4/20
 **/
public class Converter {

    public static <T> T objToClass(Object source, Class<T> target) {

        if (source == null) {
            return null;
        }
        T result;

        try {
            T targetObj = target.newInstance();
            List<Field> fieldList = new ArrayList<>() ;
            fieldList.addAll(Arrays.asList(source.getClass().getDeclaredFields()));
            fieldList.addAll(Arrays.asList(source.getClass().getSuperclass().getDeclaredFields()));
            for (Field field : fieldList) {
                field.setAccessible(true);
                Object fieldVal = field.get(source);

                Field newField;
                try {
                    newField = target.getDeclaredField(field.getName());
                } catch (NoSuchFieldException e) {
                    try {
                        newField = target.getSuperclass().getDeclaredField(field.getName());
                    } catch (NoSuchFieldException ne) {
                        continue;
                    }
                }
                if (!newField.getType().getName().equals(field.getType().getName())) {
                    continue;
                }
                if (newField.getModifiers() >= 8 || field.getModifiers() >= 8) {
                    continue;
                }
                newField.setAccessible(true);
                newField.set(targetObj, fieldVal);
            }
            result = targetObj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return result;
    }
}
