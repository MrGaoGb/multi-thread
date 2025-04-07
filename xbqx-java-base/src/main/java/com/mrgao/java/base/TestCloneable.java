package com.mrgao.java.base;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description Steam流的相关API
 * @Author Mr.Gao
 * @Date 2025/4/6 21:45
 */
public class TestCloneable {

    public static void main(String[] args) throws CloneNotSupportedException {
        Person person = new Person();
        person.setName("Mr.Gao");
        List<String> arr = new ArrayList<>();
        arr.add("1");
        arr.add("2");

        person.list = arr;
//        person.list = Arrays.asList("1", "2");
        person.age = 27;
        Address address = new Address();
        address.setCity("NJ");
        person.setAddress(address);

        System.out.println(person);
        // 调用clone方法
        Person copy1 = person.clone();
        copy1.address.setCity("NJ2");
        copy1.name = "Mr.Gao2";
        copy1.age = 28;
        System.out.println(copy1);

//        List<String> arr2 = new ArrayList<>();
//        arr2.add("3");
//        arr2.add("4");
//        arr.add("3");
//        arr.add("4");
//        copy1.list = arr;
        copy1.getList().add("3");
        copy1.getList().add("4");
        System.out.println("copyPerson：" + copy1.getName());
        System.out.println("person：" + person.getName());
    }


    @Data
    public static class Person implements Cloneable {
        private String name;

        private Integer age;

        private List<String> list;

        private Address address;


        @Override
        protected Person clone() throws CloneNotSupportedException {
            // 浅克隆
//            return (Person) super.clone();

            // 深克隆
            Person person = (Person) super.clone();
            if (address != null) {
                person.address = (Address) address.clone();
            }
//            if (list != null && list.size() > 0) {
//                // 深克隆
//                person.list = new ArrayList<>(list);
//            }

            return person;
        }

//        @Override
//        public String toString() {
//            return "Person{" +
//                    "name='" + name + '\'' +
//                    ", age=" + age +
//                    ", list=" + list +
//                    ", address=" + address +
//                    '}';
//        }
    }


    @Data
    public static class Address implements Cloneable {
        private String city;

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

}
