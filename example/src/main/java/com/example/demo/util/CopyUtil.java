package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * BeanCopier是通过修改字节码实现对象的 copy
 * <p>
 * 1.当源类和目标类的属性名称、类型要完全相同才能实现 copy。
 * 2.当源对象和目标对象的属性名称相同、类型不同,那么名称相同而类型不同的属性不会被拷贝。注意，原始类型（int，short，char）和 他们的包装类型，在这里都被当成了不同类型，因此不会被拷贝。
 * 3.源类或目标类的setter比getter少，拷贝没问题，此时setter多余，但是不会报错。
 * 4.源类和目标类有相同的属性（两者的getter都存在），但是目标类的setter不存在，此时会抛出NullPointerException。
 * 5.BeanCopier拷贝速度快，性能瓶颈出现在创建BeanCopier实例的过程中。 所以，把创建过的BeanCopier实例放到缓存中，下次可以直接获取，提升性能。
 *
 * @author Pecker
 * @Description BeanCopier 实现 BeanCopy
 * @since 2020-11-30
 */
@Slf4j
public class CopyUtil {
    /**
     * @param source      源对象实例
     * @param targetClazz 目标对象 Class
     * @param <T>         目标对象 Class 类型
     * @return <T>
     */
    public static <T> T copy(Object source, Class<T> targetClazz) {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(targetClazz, "targetClazz must not be null");

        BeanCopier copier = BeanCopier.create(source.getClass(), targetClazz, false);
        T target = newInstance(targetClazz);
        copier.copy(source, target, null);
        return target;
    }

    /**
     * @param source      源对象实例
     * @param targetClazz 目标对象 Class
     * @param target      目标对象实例
     * @param <T>         目标对象 Class 类型
     * @return <T>
     */
    public static <T> T copyProperties(Object source, Class<T> targetClazz, T target) {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(source, "Target must not be null");
        Assert.notNull(targetClazz, "TargetClazz must not be null");

        BeanCopier copier = BeanCopier.create(source.getClass(), targetClazz, false);
        copier.copy(source, target, null);
        return target;
    }

    /**
     * 拷贝数组
     *
     * @param source      源对象实例数组
     * @param targetClazz 目标对象 Class
     * @param <T>         目标对象 Class 类型
     * @return List<T>
     */
    public static <T> List<T> copyList(List<?> source, Class<T> targetClazz) {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(targetClazz, "Target must not be null");

        BeanCopier copier = BeanCopier.create(source.get(0).getClass(), targetClazz, false);
        return source.stream().map(x -> {
            T target = newInstance(targetClazz);
            copier.copy(x, target, null);
            return target;
        }).collect(Collectors.toList());
    }

    /**
     * 创建目标对象实例
     *
     * @param clazz 目标对象 Class
     * @param <T>   目标对象 Class 类型
     * @return 目标类实例
     */
    private static <T> T newInstance(Class<T> clazz) {
        try {
            // 性能瓶颈,可以做个缓存
            return clazz.newInstance();
        } catch (Exception e) {
            log.error("缺少默认构造器", e);
            return null;
        }

    }
}
