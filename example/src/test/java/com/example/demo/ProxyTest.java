package com.example.demo;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-01-21
 */
public class ProxyTest {
    public static void main(String[] args) {

        /**
         *  JDK 动态代理和 CGLIB 动态代理对比:
         * 1.JDK 动态代理只能代理实现了接口的类或者直接代理接口，而 CGLIB 可以代理未实现任何接口的类。 代理对象的父类是 Proxy
         * 2.CGLIB 动态代理是通过生成一个被代理类的子类来拦截被代理类的方法调用，因此不能代理声明为 final 类型的类和方法。 生成一个代理对象的子类
         * 3.就二者的效率来说，大部分情况都是 JDK 动态代理更优秀，随着 JDK 版本的升级，这个优势更加明显。
         *
         * 可以通过idea配置vm 的方式将动态代理产生的类生成到指定目录.下面的示例中也将生成的代理类通过字节码的方式生成了出来.
         */

        // JDK 动态代理
        jdkProxyTest();

        /*
         CGLib 动态代理:
         1.先缓存代理类
         2.再创建代理对象
         */
        cglibProxyTest();
    }

    static void jdkProxyTest() {
        /**
         * JDK 动态代理 :
         * 1.父类必然是 Proxy ,这也为什么说对于没有实现接口的类无法被 JDK 动态代理
         * 2.必须实现 InvocationHandler 接口
         * 3.通过反射代理方法，比较消耗系统性能，但可以减少代理类的数量，使用更灵活
         * 4.JDK 动态代理只能代理接口中定义的方法，与目标类的 final 方法无关
         * -- 接口中定义方法为final,这种场景语法上不支持
         * -- 接口的目标实现类上定义为 final,JDK 方式可以进行动态代理,但是一般不会这么去写,没什么意义
         */
        // 调用过程 : Proxy --> InvocationHandler --> 目标对象方法
        IUserDao target = new UserDao();
        System.out.println("目标调用对象:" + target.getClass());  //输出目标对象信息

        IUserDao proxy = (IUserDao) new JDKProxyFactory(target).getProxyInstance();
        System.out.println("代理对象" + proxy.getClass());  //输出代理对象信息
        // JDK 动态代理被代理的父类一定是 java.lang.reflect.Proxy ,
        // 所以这就是为啥 JDK 动态代理不能直接代理类,因为Java 中是类是单继承的,JDK 不知道你这个类是否已经继承过一个类了,而被 JDK 动态代理的类的父类必然是 java.lang.reflect.Proxy
        // 所以说 JDK 动态代理只能是基于接口的
        System.out.println("被代理类的父类:" + proxy.getClass().getSuperclass().getName());
        proxy.save();

        // 获取内存中字节码文件,写到磁盘上
        String path = "/Users/apple/Documents/project/spring-boot-learn/UserDaoJDK-final.class";
        outClassFile("$Proxy0", path);

    }

    private static void outClassFile(String className, String path) {

        // JDK的动态代理，它产生的代理对象是在运行时创建.打印出动态代理类$Proxy0
        byte[] classFile = ProxyGenerator.generateProxyClass(className, UserDao.class.getInterfaces());
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(classFile);
            fos.flush();
            System.out.println("代理类class文件写入成功");
        } catch (Exception e) {
            System.out.println("写文件错误");
        }
    }

    /**
     * CGLIB(Code Generation Library)是一个基于ASM的字节码生成库，它允许我们在运行时对字节码进行修改和动态生成。
     * CGLIB 通过继承方式实现代理。很多知名的开源框架都使用到了CGLIB，
     * 例如 Spring 中的 AOP 模块中：如果目标对象实现了接口，则默认采用 JDK 动态代理，否则采用 CGLIB 动态代理。
     * <p>
     * CGLib 动态代理测试
     * 1.cglib 代理无需实现接口,但 cglib 会继承目标对象，需要重写方法，所以目标对象不能为final类。
     * 2.通过生成类字节码实现代理，比反射稍快，不存在性能问题，
     * 3.实现 MethodInterceptor,重写 intercept() 方法
     * <p>
     * SpringBoot 默认使用 CGLib 动态代理,Spring 默认使用的还是 JDK 动态代理。
     */
    static void cglibProxyTest() {
        //目标对象
        UserDao target = new UserDao();
        System.out.println(target.getClass());
        //代理对象
        UserDao proxy = (UserDao) new CglibProxyFactory(target).getProxyInstance();
        System.out.println("代理对象" + proxy.getClass().getName());  //输出代理对象信息
        // CGLib 动态代理生成的代理类(proxy.getClass())的父类一定是被代理类(UserDao),所以 final 方法是不能被代理的
        System.out.println("被代理类的父类:" + proxy.getClass().getSuperclass().getName());
        //执行代理对象方法
        proxy.save();

        // cglib 产生的动态代理对象可以通过 arthas 的 jad 查看
    }


    /**
     * 被代理接口
     */
    public interface IUserDao {
        void save();
    }

    public static class UserDao implements IUserDao {
        @Override
        public final void save() {
            System.out.println("保存数据");
        }
    }

    //生成代理对象工厂类
    public static class JDKProxyFactory {

        private Object target;// 维护一个目标对象

        public JDKProxyFactory(Object target) {
            this.target = target;
        }

        // 为目标对象生成代理对象
        public Object getProxyInstance() {
            /*
             * 调用 Proxy#newProxyInstance() 生成代理对象
             * Proxy.ProxyClassFactory#apply():
             *  1.生成代理对象名称$Proxy+num
             *  2.生成字节码数据:proxyClassFile
             *  3.从字节码生成 Class 对象:defineClass0()
             */
            return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                    //关键点: 实现 InvocationHandler 接口,InvocationHandler 就是代理类
                    (proxy, method, args) -> {
                        System.out.println("执行目标方法前逻辑处理,比如:开启事务");

                        // 执行目标对象方法
                        Object returnValue = method.invoke(target, args);

                        System.out.println("执行目标方法后逻辑处理,比如:提交事务");
                        return null;
                    });
        }

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
