package greed.datastruct.common;

import greed.datastruct.AVLNode;

/**
 * Represents a BST node.
 *
 * @param <N> generic parameter for any concrete node type that inherited from abstract {@link Node greed.datastruct.Node.class}. {@link AVLNode greed.datastruct.AVLNode}
 *            *           is an example for a value N can take.
 */
public abstract class Node<N extends Node<N>> {
    private int key;
    protected N left, right;

    public Node() {

    }

    public Node(int key) {
        this.key = key;
    }

    public Node(int key, N left, N right) {
        this.key = key;
        this.left = left;
        this.right = right;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public N getLeft() {
        return left;
    }

    public void setLeft(N left) {
        this.left = left;
    }

    public N getRight() {
        return right;
    }

    public void setRight(N right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "greed.datastruct.Node{" +
                "key=" + key +
                '}';
    }
}
