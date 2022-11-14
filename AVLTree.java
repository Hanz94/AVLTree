import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.function.Supplier;

public class AVLTree extends Tree<AVLNode> {

    public AVLTree() {
        super(AVLNode::new);
    }

    public static AVLTree initialize(){
        return new AVLTree();
    }

    @Override
    public Stack<AVLNode> insert(int key) {
        return super.insert(key);
    }

//    @Override
//    public Stack<AVLNode> delete(int key) {
//        return super.delete(key);
//    }
}


abstract class Tree<T extends Node<T>> {

    private T root;
    private final Supplier<? extends T> ctor;

    public Stack<T> insert(int key){
        Stack<T> stk = new Stack<>();
        this.root = insert(key, this.root, stk);
        return stk;
    }

    public Stack<T> delete(int key) {
        Stack<T> stk = new Stack<>();
        delete(key, this.root, stk);
        return stk;
    }

    public int search(int key){
        return key;
    }

    public List<Integer> search(int smallKey, int bigKey){
        return null;
    }

    private T insert(int key, T root, Stack<T> stk){
        if(root == null){
            T newNode = ctor.get();
            newNode.setKey(key);
            stk.push(newNode);
            return newNode;
        }
        else if (key < root.getKey()){
            stk.push(root.getLeft());
            root.setLeft(insert(key, root.getLeft(), stk));
        }
        else {
            stk.push(root.getRight());
            root.setRight(insert(key, root.getRight(), stk));
        }
        return root;
    }

    private T delete(int key, T root, Stack<T> stk) {
        if (root == null) {
            return root;
        }
        else if (key < root.getKey()){
            stk.push(root.getLeft());
            root.setLeft(delete(key, root.getLeft(), stk));
        }
        else if (key > root.getKey()){
            stk.push(root.getRight());
            root.setRight(delete(key, root.getRight(), stk));
        }
        else {
            if (root.getLeft() == null) {
                return root.getRight();
            }
            else if (root.getRight() == null) {
                return root.getLeft();
            }
            else {
                root.setKey(getMax(root.getLeft()));
                root.setLeft(delete(root.getKey(), root.getLeft(), stk));
            }
        }
        return root;
    }

    private int getMax(T root)
    {
        int max = root.getKey();
        while (root.getRight() != null)
        {
            max = root.getRight().getKey();
            root = root.getRight();
        }
        return max;
    }

    protected Tree(Supplier<? extends T> ctor) {
        this.ctor = Objects.requireNonNull(ctor);
        this.root = null;
    }

    protected T getRoot() {
        return root;
    }

    private void setRoot(T root) {
        this.root = root;
    }

}
