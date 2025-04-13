package com.mrgao.java.base.aqs;

/**
 * @Description 链表的操作，包含单链表和双链表，其中包含单/双链表的断链和
 * @Author Mr.Gao
 * @Date 2025/4/13 23:37
 */
public class ListNodeDemo {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * 双向链表
     */
    static class DListNode {
        int val;
        DListNode next;
        DListNode prev;

        DListNode(int val) {
            this.val = val;
            this.next = null;
            this.prev = null;
        }
    }

    public static void main(String[] args) {
//        singleListNode();

        doubleListNode();
    }

    private static void doubleListNode() {
        // 构建链表: 1 → 2 → 3 → 4
        DListNode head = new DListNode(0);
        DListNode tail = head;
        DListNode A = new DListNode(1);
        DListNode B = new DListNode(2);
        DListNode C = new DListNode(3);
        DListNode D = new DListNode(4);

        tail.next = A;
        A.prev = tail;
        A.next = B;
        B.prev = A;
        B.next = C;
        C.prev = B;
        C.next = D;
        D.prev = C;

        // 断链2
//        A.next = B.next;
//        C.prev = A;

        // 断链head
        if (head.next == A && A.prev == head) {
            System.out.println("断链head");
            head = A; // 将头结点指向A
            A.prev.next = null;
            A.prev = null;
        }

        printForward(head);

        printBackward(D);

    }

    /**
     * 正向遍历双链表（从头到尾）
     *
     * @param head 头节点
     */
    public static void printForward(DListNode head) {
        DListNode current = head;
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) {
                System.out.print(" ↔ ");
            }
            current = current.next;
        }
        System.out.println();
    }

    /**
     * 反向遍历双链表（从尾到头）
     *
     * @param tail 尾节点
     */
    public static void printBackward(DListNode tail) {
        DListNode current = tail;
        while (current != null) {
            System.out.print(current.val);
            if (current.prev != null) {
                System.out.print(" ↔ ");
            }
            current = current.prev;
        }
        System.out.println();
    }

    private static void singleListNode() {
        // 单链表实现
        // 构建链表: 1 → 2 → 3 → 4
        ListNode A = new ListNode(1);
        ListNode B = new ListNode(2);
        ListNode C = new ListNode(3);
        ListNode D = new ListNode(4);

        // 单链表实现 (1->2->3->4)
        A.next = B;
        B.next = C;
        C.next = D;
        // 断链2
        if (A.next == B) {
            System.out.println("断链2");
            A.next = B.next;
        }

        // 验证结果：遍历链表应输出1 → 3 → 4
        ListNode curr = A;
        while (curr != null) {
            System.out.print(curr.val + " → ");
            curr = curr.next;
        }
    }

}
