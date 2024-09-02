package com.mrgao.thread.base;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2024/9/2 11:48
 */
public class ClassDemo {

    public static void main(String[] args) {
        // 自身
        System.out.println(Person.class.isAssignableFrom(Person.class));

        // 子类
        System.out.println(Person.class.isAssignableFrom(Student.class));

        // 普通类
        System.out.println(Person.class.isAssignableFrom(Teacher.class));
    }

    public static interface Person {
    }


    public static class Student implements Person {
    }

    public static class Teacher {
    }
}
