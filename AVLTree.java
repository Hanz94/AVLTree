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

    public Stack<T> insert(int key){
        Stack<T> stk = new Stack<>();
        this.root = insert(key, this.root, stk);
        return stk;
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

    public boolean delete(){

        return false;
    }

    public int search(int key){
        return key;
    }

    public List<Integer> search(int smallKey, int bigKey){
        return null;
    }

}
