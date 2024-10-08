package com.mrgao.thread.problem;

/**
 * @Description 幕后黑手之有序性
 * @Author Mr.Gao
 * @Date 2024/8/25 17:34
 *
 * <p>
 *     有序性：指的是按照代码既定的顺序执行，通俗一些就是会按照编写代码的顺序从上往下执行。
 *
 *     指令重排序：编译器或解释器为了优化程序的执行性能，有时会改变程序的执行顺序，但是编译器或解释器对程序的执行顺序进行修改，可能会导致意想不到的问题。
 *
 *     1、在单线程情况下，指令重排序可以保证最终的执行结果和程序顺序执行的结果一致，但是多线程下就会存在问题。
 * </p>
 */
public class ThreadOrderDemo {
}
