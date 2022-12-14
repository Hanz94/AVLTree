package greed.datastruct;

import greed.datastruct.common.Node;

/**
 * AVL node inherits from abstract tree node. Has an additional parameter of height in each node.
 */
public class AVLNode extends Node<AVLNode> {

    private int height;

    public AVLNode() {
        super();
        this.height = 1;
    }

    public AVLNode(int key) {
        super(key);
        this.height = 1;
    }

    public AVLNode(int key, AVLNode left, AVLNode right) {
        super(key, left, right);
        // calculate height
        calculateHeight();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * get balance factor of the node.
     * @return balance factor.
     */
    public int getBalance(){
        return (this.left != null? this.left.getHeight():0) - (this.right != null? this.right.getHeight(): 0);
    }

    @Override
    public void setLeft(AVLNode left) {
        super.setLeft(left);
        //recalculate height after change in child nodes
        calculateHeight();
    }

    @Override
    public void setRight(AVLNode right) {
        super.setRight(right);
        //recalculate height after change in child nodes
        calculateHeight();
    }

    /**
     * calculate height of the node.
     */
    private void calculateHeight(){
        this.height = 1 + Math.max(this.left != null? this.left.getHeight():0, this.right != null? this.right.getHeight():0);
    }
}

