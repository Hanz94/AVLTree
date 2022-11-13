
public class AVLNode extends Node<AVLNode>{

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
        calculateHeight();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBalance(){
        return this.left.getHeight() - this.right.getHeight();
    }

    @Override
    public void setLeft(AVLNode left) {
        super.setLeft(left);
        calculateHeight();
    }

    @Override
    public void setRight(AVLNode right) {
        super.setRight(right);
        calculateHeight();
    }

    private void calculateHeight(){
        this.height = Math.max(left != null? left.getHeight():0, right != null? right.getHeight():0);
    }
}


abstract class Node<N extends Node<N>> {
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
}
