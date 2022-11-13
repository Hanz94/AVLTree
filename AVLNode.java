
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
        this.height = calculateHeight(left, right);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBalance(){
        return ((AVLNode)this.left).getHeight() - ((AVLNode)this.right).getHeight();
    }

    @Override
    public void setLeft(AVLNode left) {
        super.setLeft(left);
        calculateHeight(left, this.right);
    }

    @Override
    public void setRight(AVLNode right) {
        super.setRight(right);
        calculateHeight(this.left, right);
    }

    private int calculateHeight(AVLNode left, AVLNode right){
        return 1 + Math.max(left != null? left.getHeight():0, right != null? right.getHeight():0);
    }
}


abstract class Node<T extends Node> {
    private int key;
    protected T left, right;

    public Node() {

    }

    public Node(int key) {
        this.key = key;
    }

    public Node(int key, T left, T right) {
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

    public T getLeft() {
        return left;
    }

    public void setLeft(T left) {
        this.left = left;
    }

    public T getRight() {
        return right;
    }

    public void setRight(T right) {
        this.right = right;
    }
}
