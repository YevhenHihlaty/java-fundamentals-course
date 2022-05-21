package com.bobocode;

import java.util.Arrays;
import java.util.Objects;

public class HashTable<T, V> {
    Node<T, V>[] table;
    int size;

    static final private int DEFAULT_TABLE_SIZE = 16;

    public HashTable() {
        initTable();
    }


    //TODO copy()
    //TODO hash()
    private int calculateIndex (int arrayLength, T value){
        return Math.abs(value.hashCode()%this.table.length);
    };


    //TODO put()

    public V put (T key, V value){
        var newNode = new Node<>(Objects.requireNonNull(key), Objects.requireNonNull(value));
        var index = calculateIndex(this.table.length, key);

        if (this.size >= this.table.length){
            resize();
        }

        if (this.table[index] == null){
            this.table[index] = newNode;
            this.size++;
            return null;
        }else{
            return put(this.table[index], newNode);
        }
    }

    private V put(Node<T, V> head, Node<T, V> newNode){
        Node<T, V> current = head;

        while (current.next != null){
            if(current.key.equals(newNode.key)) {
                V tmp = current.value;
                current.value = newNode.value;
                return tmp;
            }
            current = current.next;
        }

        current.next = newNode;
        this.size++;
        return null;
    }
    //TODO initTable()
    private void initTable(){
        this.table = new Node[DEFAULT_TABLE_SIZE];
        size = 0;
    }

    private void initTable(int size){
        this.table = new Node[size];
    }

    //TODO resize()
    private void resize(){
        copyArray();
    }

    private void copyArray(){
        int size = this.table.length*2;
        Node<T, V>[] newTable = this.table;
        this.table = new Node[size];
        this.size = 0;
        for(Node node: newTable){
            if(node != null) {
                copyList(node);
            }
        }
    }

    private void copyList(Node<T, V> head){
        Node<T, V> current = head;
        while(current.next != null){
            put(current.key, current.value);
            current = current.next;
        }
    }

    public void printTable() {
        for (int i = 0; i < this.table.length; ++i) {
            System.out.print(i + ": ");
            Node<T, V> node = this.table[i];
            while (node != null) {
                System.out.print(node.key + ":" + node.value + " ");
                node = node.next;
            }
            System.out.println();
        }
    }

    //TODO print()

}