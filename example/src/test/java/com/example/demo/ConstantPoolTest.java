package com.example.demo;

public class ConstantPoolTest {

    public static void main(String[] args) {
        stringType();

        packingType();
    }

    public static void stringType() {
        // 以下每一个方法必须单独执行,否则前面方法已经将字符串放入常量池后面的方法验证将不符合预期
        //string0();
        //string1();
        //string2();
        //string3();
        //string4();
        //string5();
        //string6();
        //string7();
        string8();
    }

    public static void string0() {
        // s指向常量池中的引用,这种方式创建的字符串对象，只会在常量池中。
        String s = "hello";
        // s1指向内存中的对象引用,这种方式会保证字符串常量池和堆中都有这个对象,创建两个对象
        String s1 = new String("hello");
        // s 为常量池中对象,s1 为堆中的对象,返回 false
        System.out.println(s == s1);
    }

    public static void string1() {
        /*
        String中的intern方法是一个 native 的方法，当调用 intern方法时，
        如果池已经包含一个等于此String对象的字符串（用equals(oject)方法确定），则返回池中的字符串。
        否则，将intern返回的引用指向当前字符串 s1(jdk1.6版本需要将 s1 复制到字符串常量池里)。
         */
        // 字符串常量池和堆中都有这个对象,创建两个对象
        String s1 = new String("hello");
        // 字符串常量池中已经有"hello",返回字符串常量池中的对象
        String s2 = s1.intern();
        // s1 为堆中的对象,s2 为常量池中对象,返回 false
        System.out.println(s1 == s2);
    }

    public static void string2() {
        /*
         在 JDK 1.6 下输出是 false，创建了 6 个对象,原因在 string0() 中
         在 JDK 1.7 及以上的版本输出是 true，创建了 5 个对象
         */

        String s1 = new String("he") + new String("llo");
        String s2 = s1.intern();
        // s1 为堆中的对象,s2 为常量池中对象,返回 false
        System.out.println(s1 == s2);

    }

    public static void string3() {
        /*
          s0和s1中的”hello”都是字符串常量，它们在编译期就被确定了，所以s0==s1为true；
          而”hel”和”lo”也都是字符串常量，当一个字符串由多个字符串常量连接而成时，它自己肯定也是字符串常量，
          所以s2也同样在编译期就被优化为一个字符串常量"hello"，所以s2也是常量池中"hello"的一个引用。
          所以我们得出s0==s1==s2；
         */

        String s0 = "hello";
        String s1 = "hello";
        String s2 = "hel" + "lo";
        System.out.println(s0 == s1); //true
        System.out.println(s0 == s2); //true
    }

    public static void string4() {
         /*
          用new String() 创建的字符串不是常量，不能在编译期就确定，所以new String() 创建的字符串对象有自己的地址空间。
          s0还是常量池 中"hello”的引用，
          s1因为无法在编译期确定，所以是运行时创建的新对象”hello”的引用，
          s2因为有后半部分 new String(”llo”)所以也无法在编译期确定，
          所以也是一个新创建对象”hello”的引用;
         */

        String s0 = "hello";
        String s1 = new String("hello");
        String s2 = "hel" + new String("llo");
        // false
        System.out.println(s0 == s1);
        // false
        System.out.println(s0 == s2);
        // false
        System.out.println(s1 == s2);
    }

    public static void string5() {
         /*
          JVM对于字符串常量的"+"号连接，将在程序编译期，JVM就将常量字符串的"+"连接优化为连接后的值，
          拿"a" + 1来说，经编译器优化后在class中就已经是"a1"。
          在编译期其字符串常量的值就确定下来，故上面程序最终的结果都为true
         */

        String a = "a1";
        String b = "a" + 1;
        System.out.println(a == b); // true

        String a1 = "atrue";
        String b1 = "a" + "true";
        System.out.println(a1 == b1); // true

        String a2 = "a3.4";
        String b2 = "a" + 3.4;
        System.out.println(a2 == b2); // true
    }

    public static void string6() {
         /*
          JVM对于字符串引用(注意是引用,而非常量)，由于在字符串的"+"连接中，
          有字符串引用存在，而引用的值在程序编译期是无法确定的，
          即"a" + bb无法被编译器优化，只有在程序运行期来动态分配并将连接后的新地址赋给b。
          所以上面程序的结果也就为false
         */

        String a = "ab";
        String bb = "b";
        String b = "a" + bb;
        // false
        System.out.println(a == b);
    }

    public static void string7() {
         /*
           没有出现"hello"字面量，所以不会在常量池里生成"hello"对象
           对比 string1() 方法
         */
        String str1 = new StringBuilder("hel").append("lo").toString();
        System.out.println(str1 == str1.intern());  //true
        // str2 与 str1 为同一个对象,堆中对象
        String str2 = str1.intern();
        System.out.println(str1 == str2);  //true
    }

    public static void string8() {
         /*
           "java"是关键字，在JVM初始化的相关类里肯定早就放进字符串常量池了
           对比 string7() 方法
         */
        // str1 在堆内存中
        String str1 = new StringBuilder("ja").append("va").toString();
        System.out.println(str1 == str1.intern());  //false
        // str2 在字符串常量池中
        String str2 = str1.intern();
        System.out.println(str1 == str2);  //false
    }


    /**
     * java中基本类型的包装类的大部分都实现了常量池技术(严格来说应该叫对象池，在堆上)，
     * 这些类是Byte,Short,Integer,Long,Character,Boolean,另外两种浮点数类型的包装类则没有实现。
     * 另外Byte,Short,Integer,Long,Character这5种整型的包装类也只是在对应值小于等于127时才可使用对象池，
     * 也即对象不负责创建和管理大于127的这些类的对象。因为一般这种比较小的数用到的概率相对较大。
     */
    public static void packingType() {
        //5种整形的包装类Byte,Short,Integer,Long,Character的对象，
        //在值小于127时可以使用对象池
        Integer i1 = 127;  //这种调用底层实际是执行的Integer.valueOf(127)，里面用到了IntegerCache对象池
        Integer i2 = 127;
        System.out.println(i1 == i2);//输出true

        //值大于127时，不会从对象池中取对象
        Integer i3 = 128;
        Integer i4 = 128;
        System.out.println(i3 == i4);//输出false

        //用new关键词新生成对象不会使用对象池
        Integer i5 = new Integer(127);
        Integer i6 = new Integer(127);
        System.out.println(i5 == i6);//输出false

        //Boolean类也实现了对象池技术
        Boolean bool1 = true;
        Boolean bool2 = true;
        System.out.println(bool1 == bool2);//输出true

        //浮点类型的包装类没有实现对象池技术
        Double d1 = 1.0;
        Double d2 = 1.0;
        System.out.println(d1 == d2);//输出false
    }
}
