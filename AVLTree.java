import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.function.Supplier;

public class AVLTree extends BinaryTree<AVLNode> {

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

    @Override
    public Stack<AVLNode> delete(int key) {
        return super.delete(key);
    }
}


abstract class BinaryTree<T extends Node<T>> {

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

    public T search(int key) {
        return search(key, this.root);
    }

    public List<Integer> searchRange(int smallKey, int bigKey){
        List<Integer> keys = new ArrayList<>();
        searchRange(smallKey, bigKey, this.root, keys);
        return keys;
    }

    @Override
    public String toString() {
        return traversePreOrder(this.root);
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

    private T search(int key, T root){
        if(root == null || key == root.getKey()){
            return root;
        }
        else if (key < root.getKey()){
            return search(key, root.getLeft());
        }
        else {
            return search(key, root.getRight());
        }
    }

    private void searchRange(int smallKey, int bigKey, T root, List<Integer> keys) {
        if (root == null) {
            return;
        }
        if (smallKey < root.getKey()) {
            searchRange( smallKey, bigKey, root.getLeft(), keys);
        }
        if (smallKey <= root.getKey() && bigKey >= root.getKey()) {
            keys.add(root.getKey());
        }
        searchRange(smallKey, bigKey, root.getRight(), keys);
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

    public String traversePreOrder(T root) {

        if (root == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(root.getKey());

        String pointerRight = "└──";
        String pointerLeft = (root.getRight() != null) ? "├──" : "└──";

        traverseNodes(sb, "", pointerLeft, root.getLeft(), root.getRight() != null);
        traverseNodes(sb, "", pointerRight, root.getRight(), false);

        return sb.toString();
    }

    private void traverseNodes(StringBuilder sb, String padding, String pointer, T node, boolean hasRightSibling) {
        if (node != null) {
            sb.append("\n");
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.getKey());

            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRightSibling) {
                paddingBuilder.append("│  ");
            } else {
                paddingBuilder.append("   ");
            }

            String paddingForBoth = paddingBuilder.toString();
            String pointerRight = "└──";
            String pointerLeft = (node.getRight() != null) ? "├──" : "└──";

            traverseNodes(sb, paddingForBoth, pointerLeft, node.getLeft(), node.getRight() != null);
            traverseNodes(sb, paddingForBoth, pointerRight, node.getRight(), false);
        }
    }

    protected BinaryTree(Supplier<? extends T> ctor) {
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
