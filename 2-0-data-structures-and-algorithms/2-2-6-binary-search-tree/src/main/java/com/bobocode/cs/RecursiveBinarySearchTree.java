package com.bobocode.cs;

import com.bobocode.util.ExerciseNotCompletedException;

import java.util.Stack;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * {@link RecursiveBinarySearchTree} is an implementation of a {@link BinarySearchTree} that is based on a linked nodes
 * and recursion. A tree node is represented as a nested class {@link Node}. It holds an element (a value) and
 * two references to the left and right child nodes.
 *
 * @param <T> a type of elements that are stored in the tree
 * @author Taras Boychuk
 * @author Maksym Stasiuk
 */
public class RecursiveBinarySearchTree<T extends Comparable<T>> implements BinarySearchTree<T> {
    private int size = 0;

    private static class Node<T> {
        Node<T> right;
        Node<T> left;
        T element;

        public Node(T element) {
            this.element = element;
        }
    }

    private Node<T> root;

    public static <T extends Comparable<T>> RecursiveBinarySearchTree<T> of(T... elements) {
        RecursiveBinarySearchTree recursiveBinarySearchTree = new RecursiveBinarySearchTree();
        Stream.of(elements)
                .forEach(recursiveBinarySearchTree::insert);
        return recursiveBinarySearchTree;
    }

    @Override
    public boolean insert(T element) {
        if (root == null) {
            root = new Node<>(element);
            size++;
            return true;
        } else {
            return insert(root, element);
        }
    }

    private boolean insert(Node<T> currentNode, T element) {
        if (currentNode.element.compareTo(element) > 0) {
            if (currentNode.left == null) {
                currentNode.left = new Node<>(element);
                size++;
                return true;
            } else {
                return insert(currentNode.left, element);
            }
        } else if (currentNode.element.compareTo(element) < 0) {
            if (currentNode.right == null) {
                currentNode.right = new Node<>(element);
                size++;
                return true;
            } else {
                return insert(currentNode.right, element);
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean contains(T element) {
        if (element == null) {
            throw new NullPointerException();
        }
        return contains(root, element);
    }

    private boolean contains(Node<T> currentNode, T element) {
        if (currentNode == null) {
            return false;
        } else if (currentNode.element.compareTo(element) > 0) {
            return contains(currentNode.left, element);
        } else if (currentNode.element.compareTo(element) < 0) {
            return contains(currentNode.right, element);
        } else {
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int depth() {
        if (root == null) {
            return 0;
        } else {
            return depth(root) - 1;
        }
    }

    private int depth(Node<T> currentNode) {
        if (currentNode == null) {
            return 0;
        } else {
            return 1 + Math.max(depth(currentNode.left), depth(currentNode.right));
        }
    }

    @Override
    public void inOrderTraversal(Consumer<T> consumer) {
        inOrderTraversal(root, consumer);
    }

    private void inOrderTraversal(Node<T> currentNode, Consumer<T> consumer) {
        if (currentNode != null) {
            inOrderTraversal(currentNode.left, consumer);
            consumer.accept(currentNode.element);
            inOrderTraversal(currentNode.right, consumer);
        }
    }

    public void inOrderTraversalUsingStack(Consumer<T> consumer) {
        Node<T> currentNode = root;
        Stack<Node<T>> stack = new Stack<>();

        while (currentNode != null && !stack.isEmpty()) {
            while (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.left;
            }
            currentNode = stack.pop();
            consumer.accept(currentNode.element);
            currentNode = currentNode.right;
        }
    }

}
