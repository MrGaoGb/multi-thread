package com.mrgao.likou.array;

import java.util.*;

/**
 * @author Mr.Gao
 * @apiNote:O(1) 时间插入、删除和获取随机元素
 * @date 2025/1/6 10:56
 */
public class Day12 {
    private static Map<Integer, Integer> map = new HashMap<>();

    private static HashSet<Integer> set = new HashSet<>();

    private static RandomizedSet randomizedSet = new RandomizedSet();

    public static void main(String[] args) {
        Integer put = map.put(1, 1);
        int i = (int) (Math.random() * map.size());
        System.out.println(i);
        Integer put1 = map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);
        map.put(5, 5);
        int index2 = (int) (Math.random() * map.size());
        System.out.println(index2);
        System.out.println(put + "> >" + put1);

        System.out.println("========================");
        boolean add = set.add(1);
        boolean add1 = set.add(2);
        boolean add2 = set.add(1);
        System.out.println(add + "> >" + add1 + "> >" + add2);
        System.out.println("========================");
        boolean insert = randomizedSet.insert(1);
        boolean insert1 = randomizedSet.insert(2);
        boolean insert2 = randomizedSet.insert(1);
        boolean insert3 = randomizedSet.insert(3);
        boolean insert4 = randomizedSet.insert(4);
        boolean insert5 = randomizedSet.insert(5);
        System.out.println(insert + "> >" + insert1 + "> >" + insert2 + "> >" + insert3 + "> >" + insert4 + "> >" + insert5);

        boolean remove = randomizedSet.remove(1);
        System.out.println("移除元素(4):" + set.remove(4));
        System.out.println("移除元素(1):" + set.remove(1));

        //for (int j = 0; j < 5 * 2; j++) {
        //    System.out.println(randomizedSet.getRandom());
        //}
    }


    //public static class RandomizedSet {
    //
    //    //private static Map<Integer, Integer> map = new HashMap<>();
    //    private static final HashSet<Integer> set = new HashSet<>();
    //
    //    private static final List<Integer> list = new ArrayList<>();
    //
    //    private static final Random random = new Random();
    //
    //    public RandomizedSet() {
    //
    //    }
    //
    //    /**
    //     * 元素 val 不存在时，向集合中插入该项，并返回 true ；否则，返回 false 。
    //     *
    //     * @param val
    //     * @return
    //     */
    //    public boolean insert(int val) {
    //        //return map.putIfAbsent(val, val) != null;
    //        boolean add = set.add(val);
    //        if (add) {
    //            list.add(val);
    //        }
    //        return add;
    //    }
    //
    //    /**
    //     * 当元素 val 存在时，从集合中移除该项，并返回 true ；否则，返回 false 。
    //     *
    //     * @param val
    //     * @return
    //     */
    //    public boolean remove(int val) {
    //        if (!set.contains(val)) {
    //            return false;
    //        }
    //        boolean remove = set.remove(val);
    //        if (remove) {
    //            list.remove(val);
    //        }
    //        return remove;
    //    }
    //
    //    /**
    //     * 随机返回现有集合中的一项（测试用例保证调用此方法时集合中至少存在一个元素）。
    //     * 每个元素应该有 相同的概率 被返回。
    //     *
    //     * @return
    //     */
    //    public int getRandom() {
    //        //return map.get(map.keySet().toArray()[(int) (Math.random() * map.size())]);
    //        //return (int) set.toArray()[(int) (Math.random() * set.size())];
    //        int index = random.nextInt(set.size());
    //        //return (int) set.toArray()[index];
    //        return list.get(index);
    //    }
    //
    //    public int getSize() {
    //        return set.size();
    //    }
    //
    //}

    public static class RandomizedSet {
        private final List<Integer> list;
        private final Map<Integer, Integer> map;
        private final Random random;

        public RandomizedSet() {
            list = new ArrayList<>();
            map = new HashMap<>();
            random = new Random();
        }

        public boolean insert(int val) {
            if (map.containsKey(val)) {
                return false;
            }
            list.add(val);
            // 存储下标值
            map.put(val, list.size() - 1);
            return true;
        }

        public boolean remove(int val) {
            if (!map.containsKey(val)) {
                return false;
            }

            // 获取下标
            int index = map.get(val);

            // 获取最后一个元素
            int last = list.get(list.size() - 1);
            list.set(index, last);// 当前下标值设置为最后一个元素
            map.put(last, index);// 将最后一个元素的下标值更新为当前下标值

            list.remove(list.size() - 1);// 移除最后一个元素

            map.remove(val);// 移除当前元素

            return true;
        }

        public int getRandom() {
            int index = random.nextInt(list.size());
            return list.get(index);
        }
    }
}
