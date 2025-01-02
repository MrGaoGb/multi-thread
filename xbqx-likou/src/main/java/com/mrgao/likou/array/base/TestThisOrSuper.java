package com.mrgao.likou.array.base;

import lombok.ToString;

/**
 * @Description TODO
 * @Author Mr.Gao
 * @Date 2025/1/2 23:15
 */
public class TestThisOrSuper {

    public static class Parent {
        public Parent() {
            System.out.println("父类 无参构造器");
        }

        public Parent(String name) {
            System.out.println("父类 带参构造器: " + name);
        }
    }

    @ToString
    public static class Children extends Parent {

        private String name;

        public Children() {
            // 默认会调用 super()，即父类的无参构造器
            System.out.println("子类 无参构造器");
            this.name = "子类";
        }

        public Children(String name) {
            //super(name); // 显式调用父类的带参构造器
            this();
            System.out.println("子类 带参构造器: " + name);
        }
    }

    public static void main(String[] args) {
        // 调用无参构造
        //Children noArgsChildren = new Children();
        // 调用带参构造
        Children argsChildren = new Children("子类");
    }

}
