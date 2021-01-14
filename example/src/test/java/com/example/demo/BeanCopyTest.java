package com.example.demo;

import com.example.demo.model.Student;
import com.example.demo.model.StudentDTO;
import com.example.demo.util.CopyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.core.DebuggingClassWriter;

/**
 * @author Pecker
 * @Description Bean 拷贝测试
 * @since 2021-01-13
 */
@Slf4j
public class BeanCopyTest {

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/apple/Desktop/class");
        /**
         *  试验证明:
         *  1.spring BeanUtils 是可以对 int <-> Integer 互相 copy 的
         *  2.BeanCopier 是不可以对 int <-> Integer 互相 copy ,参数名和类型要完全相同
         */
        newCopy();
        builderCopy();

    }

    /**
     * new 方式实例化对象后进行 copy
     */
    public static void newCopy() {
        log.info("new 方式实例化对象后进行 copy ");
        Student sourceStu1 = new Student();
        sourceStu1.setAge(10);
        sourceStu1.setName("张三");

        StudentDTO targetStu2 = new StudentDTO();
        //StudentDTO beanCopierTarget = new StudentDTO();

        //spring BeanUtils
        BeanUtils.copyProperties(sourceStu1, targetStu2);
        log.info("int --> Integer, spring BeanUtils 结果:{}", targetStu2);
        StudentDTO beanCopierTarget1 = CopyUtil.copy(sourceStu1, StudentDTO.class);
        log.info("int --> Integer, BeanCopier 结果:{}", beanCopierTarget1);

        StudentDTO sourceStu2 = new StudentDTO();
        sourceStu2.setAge(10);
        sourceStu2.setName("张三");

        Student targetStu1 = new Student();

        //spring BeanUtils
        BeanUtils.copyProperties(sourceStu2, targetStu1);
        log.info("Integer --> int, spring BeanUtils 结果:{}", targetStu1);
        StudentDTO beanCopierTarget2 = CopyUtil.copy(sourceStu1, StudentDTO.class);
        log.info("Integer --> int, BeanCopier 结果:{}", beanCopierTarget2);
    }

    /**
     * builder 方式实例化对象后进行 copy
     */
    public static void builderCopy() {
        log.info("builder 方式实例化对象后进行 copy ");
        Student sourceStu1 = Student.builder().age(10).name("张三").build();

        StudentDTO targetStu2 = new StudentDTO();

        //spring BeanUtils
        BeanUtils.copyProperties(sourceStu1, targetStu2);
        log.info("int --> Integer, spring BeanUtils 结果:{}", targetStu2);
        StudentDTO beanCopierTarget1 = CopyUtil.copy(sourceStu1, StudentDTO.class);
        log.info("int --> Integer, BeanCopier 结果:{}", beanCopierTarget1);

        StudentDTO sourceStu2 = StudentDTO.builder().age(10).name("张三").build();
        Student targetStu1 = new Student();

        //spring BeanUtils
        BeanUtils.copyProperties(sourceStu2, targetStu1);
        log.info("Integer --> int, spring BeanUtils 结果:{}", targetStu1);
        StudentDTO beanCopierTarget2 = CopyUtil.copy(sourceStu1, StudentDTO.class);
        log.info("Integer --> int, BeanCopier 结果:{}", beanCopierTarget2);
    }

}
