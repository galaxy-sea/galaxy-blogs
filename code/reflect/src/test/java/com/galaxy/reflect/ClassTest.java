package com.galaxy.reflect;

import com.galaxy.reflect.bean.Father;
import com.galaxy.reflect.bean.Panda;
import com.galaxy.reflect.bean.Runing;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class ClassTest {

    /**
     * class对象的三种实例化方法
     */
    @Test
    public void LoadClass() throws ClassNotFoundException {
        // 方法1
        Panda panda = new Panda();
        Class<? extends Panda> pandaClass1 = panda.getClass();

        // 方法2
        Class<Panda> pandaClass2 = Panda.class;

        // 方法3  这个方式会抛出异常哦
        Class<?> pandaClass3 = Class.forName("com.galaxy.reflect.bean.Panda");

        System.out.println(pandaClass1);
        System.out.println(pandaClass1);
        System.out.println(pandaClass3);
    }

    @Test
    public void classDemo() throws IllegalAccessException, InstantiationException {

        /** 通过“newInstance”方法实例化一个对象 */
        Panda panda = Panda.class.newInstance();
        System.out.println(panda);


        /** 获取class的全路径名 */
        String name = Panda.class.getName();
        System.out.println("name = " + name);


        /** 获取包路径名 */
        Package packagePath = Panda.class.getPackage();
        System.out.println("package = " + packagePath);


        /** 获取class名 */
        String simpleName = Panda.class.getSimpleName();
        System.out.println("simpleName = " + simpleName);


        /** 检查class是否是传入class的之类 */
        // Class<? extends Panda> FatherClass = Father.class.asSubclass(Panda.class);
        Class<? extends Father> PandaClass1 = Panda.class.asSubclass(Father.class);
        Class<? extends Runing> PandaClass2 = Panda.class.asSubclass(Runing.class);
        System.out.println("PandaClass1 = " + PandaClass1);
        System.out.println("PandaClass2 = " + PandaClass2);


        /** 建传入的对象转换为该class */
        // Panda panda1 = Panda.class.cast(new Father());
        Father father = Father.class.cast(panda);
        Runing runing = Runing.class.cast(panda);
        System.out.println("father = " + father);
        System.out.println("runing = " + runing);


        /** 获取类加载器 */
        ClassLoader classLoader = Panda.class.getClassLoader();

        /** 获取puclic的内部类 */
        Class<?>[] classes = Panda.class.getClasses();
        System.out.println("classes = " + Arrays.toString(classes));

        /** 获取已声明的内部类 */
        Class<?>[] declaredClasses = Panda.class.getDeclaredClasses();
        System.out.println("declared Classes =" + Arrays.toString(declaredClasses));

        /** 获取父类的class */
        Class<? super Panda> superclass = Panda.class.getSuperclass();
        System.out.println("superclass = " + superclass);

        /** 获取实现类的的class */
        Class<?>[] interfaces = Panda.class.getInterfaces();
        System.out.println("interfaces = " + Arrays.toString(interfaces));

    }

    @Test
    public void constructorDemo() {

        /** 获取全部的构造方法 */
        Constructor<?>[] constructors = Panda.class.getConstructors();

        /** 因为构造方式是重载的，只能通过参数类型来区分 */
        Panda.class.getConstructor()


    }


}
