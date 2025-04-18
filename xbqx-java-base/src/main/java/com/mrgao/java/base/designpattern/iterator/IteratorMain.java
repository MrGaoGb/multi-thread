package com.mrgao.java.base.designpattern.iterator;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

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
        //// 遍历用户数据
        //for (User user : userList) {
        //    System.out.println(user);
        //}

        // 自定义实现迭代器
        //User user = new User("张三", 18);
        //for (String s : user) {
        //    System.out.println(s);
        //}

        //Iterator<String> iterator = user.iterator();
        //while (iterator.hasNext()) {
        //    String next = iterator.next();
        //    System.out.println(next);
        //}

        // 向集合中添加用户信息
        userList.add(new User("张三", 18));
        userList.add(new User("李四", 18));

        // 遍历用户数据
        for (User user : userList) {
            if (user.getAge() == 18) {
                userList.add(new User("王五", 23));
                break;
            }
        }


        // 迭代器
        //Iterator<User> iterator = userList.iterator();
        //while (iterator.hasNext()) {
        //    // 获取一个元素
        //    User user = iterator.next();
        //    System.out.println(user);
        //    //if (user.getAge() == 18) {
        //    //    // 移除一个元素
        //    //    userList.add(new User("赵六", 24));
        //    //}
        //}

        System.out.println(userList);

    }


}

/**
 * 用户信息
 */
@Getter
@Setter
class User implements Iterable<String> {

    @Override
    public Iterator<String> iterator() {
        return new UserIte();
    }

    private String name;
    private Integer age;

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

    class UserIte implements Iterator<String> {

        private int count = 2;

        @Override
        public boolean hasNext() {
            return count > 0;
        }

        @Override
        public String next() {
            count--;
            //System.out.println("count:" + count);
            if (count == 1) {
                return age + "";
            }
            if (count == 0) {
                return name;
            }
            // 没有元素之后抛出异常NoSuchElementException
            throw new NoSuchElementException();
        }
    }
}