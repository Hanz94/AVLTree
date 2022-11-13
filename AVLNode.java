
public class AVLNode extends Node{

    private int height;

    public AVLNode(int key, int height) {
        super(key);
        this.height = height;
    }

    public AVLNode(int key, Node left, Node right, int height) {
        super(key, left, right);
        this.height = height;
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
}


abstract class Node {
    private int key;
    protected Node left, right;

    public Node(int key) {
        this.key = key;
    }

    public Node(int key, Node left, Node right) {
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

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
