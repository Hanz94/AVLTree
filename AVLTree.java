import java.util.*;
import java.util.function.Supplier;

public class AVLTree extends BinaryTree<AVLNode> {

    private AVLTree() {
        super(AVLNode::new);
    }

    public static AVLTree initialize(){
        return new AVLTree();
    }

    @Override
    public Stack<AVLNode> insert(int key) {
        Stack<AVLNode> nodeTrace = super.insert(key);

        if (nodeTrace.size() > 2) {
            AVLNode grandChild = nodeTrace.pop();
            AVLNode child = nodeTrace.pop();
            AVLNode aNode = nodeTrace.pop();
            AVLNode root = balanceTreeOnInsert(grandChild, child, aNode);
            while ( root == null && nodeTrace.size() > 0) {
                grandChild = child;
                child = aNode;
                aNode = nodeTrace.pop();
                root = balanceTreeOnInsert(grandChild, child, aNode);
            }
            if(root != null){
                try {
                    AVLNode parent = nodeTrace.pop();
                    if (parent.getKey() > root.getKey()) {
                        parent.setLeft(root);
                    } else {
                        parent.setRight(root);
                    }
                } catch (EmptyStackException e){
                    setRoot(root);
                }
            }
        }
        return null;
    }

    @Override
    public Stack<AVLNode> delete(int key) {
        Stack<AVLNode> nodeTrace =  super.delete(key);
        while ( nodeTrace.size() > 0) {
            AVLNode root = balanceTreeOnDelete(nodeTrace.pop());
            if(root != null){
                try {
                    AVLNode parent = nodeTrace.peek();
                    if (parent.getKey() > root.getKey()) {
                        parent.setLeft(root);
                    } else {
                        parent.setRight(root);
                    }
                } catch (EmptyStackException e){
                    setRoot(root);
                }
            }
        }
        return null;
    }

    private AVLNode balanceTreeOnInsert(AVLNode grandChild, AVLNode child, AVLNode aNode){
        int balance = aNode.getBalance();

        // Case 1 : LL
        if (balance > 1 && grandChild.getKey() < child.getKey()){
            return rightRotate(aNode);
        }
        // Case 2 : RR
        if (balance < -1 && grandChild.getKey() > child.getKey()){
            return leftRotate(aNode);
        }
        // Case 3 : LR
        if (balance > 1 && grandChild.getKey() > child.getKey()) {
            aNode.setLeft(leftRotate(child));
            return rightRotate(aNode);
        }

        // Case 4 : RL
        if (balance < -1 && grandChild.getKey() < child.getKey()) {
            aNode.setRight(rightRotate(child));
            return leftRotate(aNode);
        }
        return null;
    }

    private AVLNode balanceTreeOnDelete(AVLNode aNode){
        int balance = aNode.getBalance();

        // Case 1 : R0 and R1
        if ( balance > 1 && aNode.getLeft().getBalance() >= 0){
            return rightRotate(aNode);
        }
        // Case 2 : L-1 and L0
        if (balance < -1 && aNode.getRight().getBalance() <= 0){
            return leftRotate(aNode);
        }
        // Case 3 : R-1
        if (balance > 1 && aNode.getLeft().getBalance() < 0) {
            aNode.setLeft(leftRotate(aNode.getLeft()));
            return rightRotate(aNode);
        }
        // Case 4 : L
        if (balance < -1 && aNode.getRight().getBalance() > 0) {
            aNode.setRight(rightRotate(aNode.getRight()));
            return leftRotate(aNode);
        }
        return null;
    }


    private AVLNode rightRotate(AVLNode root) {
        AVLNode child = root.getLeft();
        AVLNode childRSubTree = child.getRight();
        child.setRight(root);
        root.setLeft(childRSubTree);
        return child;
    }

    private AVLNode leftRotate(AVLNode root) {
        AVLNode child = root.getRight();
        AVLNode childLSubTree = child.getLeft();
        child.setLeft(root);
        root.setRight(childLSubTree);
        return child;
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

    public Integer search(int key) {
        T searchedNode = search(key, this.root);
        return searchedNode != null ? searchedNode.getKey() : null;
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
        if (root != null) {
            stk.push(root);
        }
        if(root == null){
            root = ctor.get();
            root.setKey(key);
            stk.push(root);
            return root;
        }
        else if (key < root.getKey()){
            root.setLeft(insert(key, root.getLeft(), stk));
        }
        else {
            root.setRight(insert(key, root.getRight(), stk));
        }
        return root;
    }

    private T delete(int key, T root, Stack<T> stk) {

        if (root == null) {
            return null;
        }
        else {
            if (key < root.getKey()){
                stk.push(root);
                root.setLeft(delete(key, root.getLeft(), stk));
            }
            else if (key > root.getKey()){
                stk.push(root);
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

    protected void setRoot(T root) {
        this.root = root;
    }

}
