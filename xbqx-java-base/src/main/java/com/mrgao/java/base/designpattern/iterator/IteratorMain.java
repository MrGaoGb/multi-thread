package com.mrgao.java.base.designpattern.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Mr.Gao
 * @apiNote: 迭代器模式
 * @date 2025/4/17 20:14
 */
public class IteratorMain {


    // ArrayList、HashMap、HashSet、LinkedList、Vector

    public static void main(String[] args) {
        // 创建用户集合
        List<User> userList = new ArrayList<>();

        // 向集合中添加用户信息
        userList.add(new User("张三", 18));
        userList.add(new User("李四", 18));

        // 遍历用户数据
        //for (User user : userList) {
        //    if (user.getAge() == 18) {
        //        userList.add(new User("王五", 23));
        //    }
        //}


        // 迭代器
        Iterator<User> iterator = userList.iterator();
        while (iterator.hasNext()) {
            // 获取一个元素
            User user = iterator.next();
            if (user.getAge() == 18) {
                // 移除一个元素
                userList.add(new User("赵六", 24));
            }
        }

        System.out.println(userList);

    }


}

/**
 * 用户信息
 */
class User {

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}