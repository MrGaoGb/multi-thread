package com.mrgao.thread.problem;

/**
 * @Description 幕后黑手-原子性问题
 * @Author Mr.Gao
 * @Date 2024/8/23 23:50
 * <p>
 * 线程切换导致的原子性问题
 * <p>
 * 原子性是指一个或多个操作在CPU中执行不被中断的特性
 * <p>
 * 在并发编程中，往往Java中的一条简单的语句，对应着CPU中的多条指令。例如，我们编写的TestThread类中的如下代码。
 * count++
 * 看似简单的一条count自增的代码，实际上对应着CPU中的多条指令。我们将CPU的指令简化成如下三步操作。
 * 指令1：把变量count从内存加载的CPU寄存器。
 * 指令2：在寄存器中执行count++操作。
 * 指令3：将结果写入缓存（可能是CPU缓存，也可能是内存）。
 * <p>
 * 在上图中，线程A将count=0加载到CPU的寄存器后，发生了线程切换。此时内存中的count值仍然为0，线程B将count=0加载到寄
 * 存器，执行count++操作，并将count=1写到内存。此时，CPU切换到线程A，执行线程A中的count++操作后，线程A中的count值
 * 为1，线程A将count=1写入内存，此时内存中的count值最终为1。
 * 综上：CPU能够保证的原子性是CPU指令级别的，而不是编程语言级别的。我们在编写高并发程序时，需要在编程语言级别保证程序
 * 的原子性。
 * </p>
 */
public class ThreadAtomicityDemo {

}
