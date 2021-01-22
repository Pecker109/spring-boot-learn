package com.example.demo;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-01-21
 */
public class ProxyTest {
    public static void main(String[] args) {

        // JDK 动态代理
        jdkProxyTest();

        //CGLib 动态代理
        cglibProxyTest();
    }

    /**
     * 被代理接口
     */
    public interface IUserDao {
        void save();
    }

    public static class UserDao implements IUserDao {

        @Override
        public void save() {
            System.out.println("保存数据");
        }
    }


    static void jdkProxyTest() {
        /**
         * JDK 动态代理 :
         * 1.父类必然是 Proxy ,这也为什么说 JDK 动态代理只能代理接口
         * 2.必须实现InvocationHandler接口
         * 3.通过反射代理方法，比较消耗系统性能，但可以减少代理类的数量，使用更灵活
         */
        // 调用过程 : Proxy --> InvocationHandler --> 目标对象方法
        IUserDao target = new UserDao();
        System.out.println("目标调用对象:" + target.getClass());  //输出目标对象信息
        IUserDao proxy = (IUserDao) new JDKProxyFactory(target).getProxyInstance();
        System.out.println("代理对象" + proxy.getClass());  //输出代理对象信息
        proxy.save();
    }


    //生成代理对象工厂类
    public static class JDKProxyFactory {

        private Object target;// 维护一个目标对象

        public JDKProxyFactory(Object target) {
            this.target = target;
        }

        // 为目标对象生成代理对象
        public Object getProxyInstance() {
            /**
             * 调用 Proxy#newProxyInstance() 生成代理对象
             */
            return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                    //关键点: 实现 InvocationHandler 接口,InvocationHandler 就是代理类
                    (proxy, method, args) -> {
                        System.out.println("开启事务");

                        // 执行目标对象方法
                        Object returnValue = method.invoke(target, args);

                        System.out.println("提交事务");
                        return null;
                    });
        }
    }


    /**
     * CGLib 动态代理测试
     * 1.cglib代理无需实现接口
     * 2.通过生成类字节码实现代理，比反射稍快，不存在性能问题，
     * 3.但 cglib 会继承目标对象，需要重写方法，所以目标对象不能为final类。
     * <p>
     * Spring AOP 所生成的 AOP 代理就是使用的 CGLib 动态代理
     */
    static void cglibProxyTest() {
        //目标对象
        UserDao target = new UserDao();
        System.out.println(target.getClass());
        //代理对象
        UserDao proxy = (UserDao) new CglibProxyFactory(target).getProxyInstance();
        System.out.println(proxy.getClass());
        //执行代理对象方法
        proxy.save();
    }

    public static class CglibProxyFactory implements MethodInterceptor {

        private Object target;//维护一个目标对象

        public CglibProxyFactory(Object target) {
            this.target = target;
        }

        //为目标对象生成代理对象
        public Object getProxyInstance() {
            //工具类
            Enhancer en = new Enhancer();
            //设置父类
            en.setSuperclass(target.getClass());
            //设置回调函数
            en.setCallback(this);
            //创建子类对象代理
            return en.create();
        }

        @Override
        public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            System.out.println("开启事务");
            // 执行目标对象的方法
            Object returnValue = method.invoke(target, args);
            System.out.println("关闭事务");
            return null;
        }
    }
}
