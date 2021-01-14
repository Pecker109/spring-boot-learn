package com.example.demo.strategy.scan;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.util.TypeUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Pecker
 * @Description 自定义类扫描器, 将标记自定义注解的所有类都加载到 Spring 上下文中
 * <p>
 * Spring 在扫描包的时候,只会将Spring 自己提供的注解(例如@Controller,@Service...等)
 * 注册到 Spring 上下文环境中,我们自定义的的注解是不会注册到上下文环境中的,所以我们要自己去
 * 写实现,将自定义的注解也注册到 Spring上下文环境中
 * </p>
 * @since 2020-08-23
 */
public class MyClassScanner {
    //获取Spring资源解析器
    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    //包含的
    private final List<TypeFilter> includeFilters = new ArrayList<TypeFilter>();
    //不包含的
    private final List<TypeFilter> excludeFilters = new ArrayList<TypeFilter>();

    private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

    /**
     * 添加包含的Filter
     *
     * @param includeFilter includeFilter
     */
    public void addIncludeFilter(TypeFilter includeFilter) {
        this.includeFilters.add(includeFilter);
    }

    /**
     * 添加排除的Filter
     *
     * @param excludeFilter excludeFilter
     */
    public void addExcludeFilter(TypeFilter excludeFilter) {
        this.excludeFilters.add(excludeFilter);
    }

    /**
     * 扫描指定的包，获取包下所有的Class
     *
     * @param basePackage 包名
     * @param targetTypes 需要指定的目标类型,可以是pojo,可以是注解
     * @return Set<Class < ?>>
     */
    public static Set<Class<?>> scan(String basePackage, Class<?>... targetTypes) {
        MyClassScanner cs = new MyClassScanner();
        for (Class<?> targetType : targetTypes) {
            //判断目标类型是否是注释类型
            if (TypeUtils.isAssignable(Annotation.class, targetType)) {
                cs.addIncludeFilter(new AnnotationTypeFilter((Class<? extends Annotation>) targetType));
            } else {
                cs.addIncludeFilter(new AssignableTypeFilter(targetType));
            }
        }
        //真正扫描包
        return cs.doScan(basePackage);
    }

    /**
     * 扫描指定的包，获取包下所有的Class
     *
     * @param basePackages 包名,多个
     * @param targetTypes  需要指定的目标类型,可以是pojo,可以是注解
     * @return Set<Class < ?>>
     */
    public static Set<Class<?>> scan(String[] basePackages,
                                     Class<?>... targetTypes) {
        MyClassScanner cs = new MyClassScanner();
        for (Class<?> targetType : targetTypes) {
            if (TypeUtils.isAssignable(Annotation.class, targetType)) {
                cs.addIncludeFilter(new AnnotationTypeFilter((Class<? extends Annotation>) targetType));
            } else {
                cs.addIncludeFilter(new AssignableTypeFilter(targetType));
            }
        }
        Set<Class<?>> classes = new HashSet<Class<?>>();
        for (String s : basePackages) {
            classes.addAll(cs.doScan(s));
        }
        return classes;
    }

    /**
     * 扫描指定的包，获取包下所有的Class
     *
     * @param basePackages 包名
     * @return Set<Class < ?>>
     */
    public Set<Class<?>> doScan(String[] basePackages) {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        for (String basePackage : basePackages) {
            classes.addAll(doScan(basePackage));
        }
        return classes;
    }

    /**
     * 扫描指定的包，获取包下所有的Class
     *
     * @param basePackage 包名
     * @return Set<Class < ?>>
     */
    public Set<Class<?>> doScan(String basePackage) {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        try {
            // 获取包所在的全量路径名
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                    + ClassUtils.convertClassNameToResourcePath(
                    SystemPropertyUtils.resolvePlaceholders(basePackage)) + "/**/*.class";
            Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
            for (int i = 0; i < resources.length; i++) {
                Resource resource = resources[i];
                if (resource.isReadable()) {
                    MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
                    if ((includeFilters.size() == 0 && excludeFilters.size() == 0) || matches(metadataReader)) {
                        try {
                            classes.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
                        } catch (ClassNotFoundException ignore) {
                        }
                    }
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException("I/O failure during classpath scanning", ex);
        }
        return classes;
    }

    /**
     * 处理 excludeFilters和includeFilters
     *
     * @param metadataReader
     * @return boolean
     * @throws IOException
     */
    private boolean matches(MetadataReader metadataReader) throws IOException {
        for (TypeFilter tf : this.excludeFilters) {
            if (tf.match(metadataReader, this.metadataReaderFactory)) {
                return false;
            }
        }
        for (TypeFilter tf : this.includeFilters) {
            if (tf.match(metadataReader, this.metadataReaderFactory)) {
                return true;
            }
        }
        return false;
    }
}
