package com.taotao.tao_oat_lib.knowledgepoint;

import java.util.ArrayList;
import java.util.List;

/**
 * @package com.taotao.tao_oat_lib.knowledgepoint
 * @file FanxingThink
 * @date 2018/8/25  下午4:49
 * @autor wangxiongfeng
 */
public class FanxingThink {

    //范型

    public class Box<T> {
        private T t;
    }

    class Demo {
    }


    class Pair<K, T> extends Demo {
        private K key;
        private T value;
    }

    public static <K, T> void compare(Pair<K, T> pair_No1, Pair<K, T> pair_No2) {


    }

    public static <T> int findNum(T[] arr, T value) {
        return 1;
    }

    //通配符

    public interface Comparable<T> {
        public int compareTo(T o);
    }

    public static <T extends Comparable<T>> int countGreaterThan(T[] anArray, T elem) {
        int count = 0;
        for (T e : anArray)
            if (e.compareTo(elem) > 0)
                ++count;
        return count;
    }

    public class GenericsAndCovariance {
        public void main(String[] args) {
            // Wildcards allow covariance:
            List<? extends Pair> flist = new ArrayList<Pair>(); // ? extends classname 限定参数的上限 返回的参数最小边界是 pair 但是不能传入参数
            // Compile Error: can't add any type of object:
            flist.add(null); // Legal but uninteresting
            Pair pair = flist.get(1);   // extends 返回的是具体的类型 添加只能是 null 集合中元素都是继承  pair 集合可以是 继承 pair的某一个类 不确定 当添加 pair的子类类时出现奇异

            List<? super Demo> pList = new ArrayList<>(); // ? super classname 限定了参数的 下限 入参 为pair 返回为 object
            Object object = pList.get(1);  //super 返回的是 object 可添加具体的参数类型。 得到最终的 超累 避免奇异。
            pList.add(new Pair());
            pList.add(new Demo());
        }
    }
    //避免类型类型擦除

    // 使用 <? extends ClassName>  使编译器在编译时寻找超类时  用className 代替 object


    /**
     * 闭包
     * 闭包是引用了自由变量的函数，这个被引用的变量将和这个函数一同存在。
     * 比方在局部内部类中引用了 方法中的变量  变量将使用finall修饰  实现  变量和内部类周期的同步
     * 内部类引用非内部类的变量 。闭包回导致资源不被回收。
     */

}
