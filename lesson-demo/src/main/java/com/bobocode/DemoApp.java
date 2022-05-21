package com.bobocode;

import com.bobocode.data.Accounts;
import com.bobocode.model.Account;

import java.util.Comparator;
import java.util.Stack;

public class DemoApp {
    public static void main2(String[] args) {
        var head = createLinkedList(4, 3, 9, 1);
        printReversedRecursively(head);
        printReversedUsingStack(head);
    }

    public static void main(String[] args) {
        Comparator<Account> accountComparator = new RandomFieldComparator<>(Account.class);
        System.out.println(accountComparator);
        Accounts.generateAccountList(10)
                .stream()
                .sorted(accountComparator)
                .forEach(System.out::println);
    }

    /**
     * Creates a list of linked {@link Node} objects based on the given array of elements and returns a head of the list.
     *
     * @param elements an array of elements that should be added to the list
     * @param <T>      elements type
     * @return head of the list
     */


    public static <T> Node<T> createLinkedList(T... elements) {
        Node head = null;
        Node current = null;

        for (var e: elements){
            if (head == null){
                head = new Node<>(e);
                current = head;
            }else{
                current.next = new Node<>(e);
                current = current.next;
            }
        }

        return head;
    }

    /**
     * Prints a list in a reserved order using a recursion technique. Please note that it should not change the list,
     * just print its elements.
     * <p>
     * Imagine you have a list of elements 4,3,9,1 and the current head is 4. Then the outcome should be the following:
     * 1 -> 9 -> 3 -> 4
     *
     * @param head the first node of the list
     * @param <T>  elements type
     */
    public static <T> void printReversedRecursively(Node<T> head) {
        if (head.next != null) {
            printReversedRecursively(head.next);
        }
        if (head.next != null){
            System.out.print("->");
        }
        System.out.print(head.element);
    }

    /**
     * Prints a list in a reserved order using a {@link java.util.Stack} instance. Please note that it should not change
     * the list, just print its elements.
     * <p>
     * Imagine you have a list of elements 4,3,9,1 and the current head is 4. Then the outcome should be the following:
     * 1 -> 9 -> 3 -> 4
     *
     * @param head the first node of the list
     * @param <T>  elements type
     */
    public static <T> void printReversedUsingStack(Node<T> head) {
        System.out.println();
        Stack<T> stack = LinkedListToStack(head);

        while(stack.size() > 1){
            System.out.print(stack.pop() + "->");
        }
        System.out.println(stack.pop());
    }

    public static <T> Stack<T> LinkedListToStack (Node<T> listHead){
        Stack<T> stackOfListEl = new Stack<>();
        Node<T> current = listHead;
        while(current.next != null){
            stackOfListEl.add(current.element);
            current = current.next;
        }
        stackOfListEl.add(current.element);
        return stackOfListEl;
    }

}
