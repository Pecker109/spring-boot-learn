package com.example.demo.util;

import com.example.demo.bean.DynamicBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtilsBean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pecker
 * @Description
 * @since 2021-06-10
 */
@Slf4j
public class ReflectUtil {
    /**
     * 通过 CGLib 给 dest 对象添加新字段
     *
     * @param dest        原有对象,也是在该对象上新增字段
     * @param newValueMap 新字段map :key-字段名,value-字段值
     * @return 新增字段后的对象
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException    IllegalAccessException
     */
    public static Object getObject(Object dest, Map<String, Object> newValueMap) throws InvocationTargetException, IllegalAccessException {
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();

        //1.获取原对象的字段数组
        PropertyDescriptor[] descriptorArr = propertyUtilsBean.getPropertyDescriptors(dest);

        //2.遍历原对象的字段数组，并将其封装到Map
        Map<String, Class> oldKeyMap = new HashMap<>(4);
        for (PropertyDescriptor it : descriptorArr) {
            if (!"class".equalsIgnoreCase(it.getName())) {
                oldKeyMap.put(it.getName(), it.getPropertyType());
                newValueMap.put(it.getName(), it.getReadMethod().invoke(dest));
            }
        }

        //执行到这里 oldKeyMap value 值是字段类型,newValueMap value 值是字段的值

        //3.将扩展字段Map合并到原字段Map中
        newValueMap.forEach((k, v) -> {
            if (null != v) {
                oldKeyMap.put(k, v.getClass());
            }
        });

        //4.根据新的字段组合生成子类对象
        DynamicBean dynamicBean = new DynamicBean(dest.getClass(), oldKeyMap);

        //5.放回合并后的属性集合
        newValueMap.forEach((k, v) -> {
            try {
                dynamicBean.setValue(k, v);
            } catch (Exception e) {
                log.error("动态添加字段【值】出错", e);
            }
        });
        return dynamicBean.getTarget();
    }


    public static Object firstWordToUpper(Object dest) throws InvocationTargetException, IllegalAccessException {

        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        Map<String, Object> valueMap = new HashMap<>();

        //1.获取原对象的字段数组
        PropertyDescriptor[] descriptorArr = propertyUtilsBean.getPropertyDescriptors(dest);

        //2.遍历原对象的字段数组，并将其封装到Map
        Map<String, Class> typeMap = new HashMap<>();
        for (PropertyDescriptor it : descriptorArr) {
            if (!"class".equalsIgnoreCase(it.getName())) {

//                typeMap.put(it.getName(), it.getPropertyType());
//                valueMap.put(it.getName(), it.getReadMethod().invoke(dest));

                // 首字母大写
                String fieldName = it.getName().toUpperCase();
                typeMap.put(fieldName, it.getPropertyType());
                valueMap.put(fieldName, it.getReadMethod().invoke(dest));
            }
        }

        //执行到这里 oldKeyMap value 值是字段类型,newValueMap value 值是字段的值

        //3.将扩展字段Map合并到原字段Map中
        valueMap.forEach((k, v) -> {
            if (null != v) {
                typeMap.put(k, v.getClass());
            }
        });

        //4.根据新的字段组合生成子类对象
        DynamicBean dynamicBean = new DynamicBean(dest.getClass(), typeMap);

        //5.放回合并后的属性集合
        valueMap.forEach((k, v) -> {
            try {
                dynamicBean.setValue(k, v);
            } catch (Exception e) {
                log.error("动态添加字段【值】出错", e);
            }
        });
        return dynamicBean.getTarget();
    }
}
