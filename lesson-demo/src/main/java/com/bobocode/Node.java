package com.bobocode;

public class Node<T, V> {
    T key;
    V value;

    Node<T, V> next;

    public Node(T key, V value) {
        this.key = key;
        this.value = value;
    }
}
