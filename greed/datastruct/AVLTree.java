package greed.datastruct;

import java.util.*;
import java.util.function.Supplier;

/**
 * Data structure for AVL tree which inherits attributes and functions from its predecessor: Binary Search Tree (BST).
 * The code is structured to maximize inheritance from standard Binary Search Tree.
 * The Standard Binary Search Tree is in {@link BinarySearchTree BinarySerachTree.class}.
 * {@link AVLNode greed.datastruct.AVLNode}(concrete implementation of abstract {@link Node greed.datastruct.Node}) is used to represent single node in the tree.
 * Supports initialization, insertion, deletion and search on AVL tree while self-balancing after every inset and delete.
 */
public class AVLBST extends BinarySearchTree<AVLNode> {

    /**
     * private constructor, {@link #initialize() initialize()} is to initialize a binary tree.
     */
    private AVLBST() {
        super(AVLNode::new);
    }


    /**
     * Construct an empty AVL tree.
     * @return empty AVL tree.
     */
    public static AVLBST initialize(){
        return new AVLBST();
    }

    /**
     * Inserts a key to the existing AVL tree. This method overrides the functionality of Binary Search Tree insertion.
     * It uses standard Binary Search Tree insertion of it's super class() ({@link BinarySearchTree BinarySerachTree.class}) and perform re-balancing on the output of BST insertion.
     * @param key key of the new node to be inserted (Do not support duplicate keys).
     * @return always returns null although method signature has a stack due to overriding.
     */
    @Override
    public Stack<AVLNode> insert(int key) {
        // Doing standard insertion in BST
        Stack<AVLNode> nodeTrace = super.insert(key);

        // Re-balancing after insertion to maintain AVL properties.
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

    /**
     * Deletes a key in the existing AVL tree. This method overrides the functionality of Binary Search Tree deletion.
     * It uses standard binary tree deletion of it's super class and perform re-balancing on the output of BST insertion.
     * @param key key of the node to be deleted (Will do nothing if the key is not in the tree).
     * @return always returns null although method signature has a stack due to overriding.
     */
    @Override
    public Stack<AVLNode> delete(int key) {
        // Doing standard deletion in BST
        Stack<AVLNode> nodeTrace =  super.delete(key);

        // Re-balancing after deletion to maintain AVL properties.
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

    /**
     * Balances AVL tree after insertion. Handles 4 cases (LL, RR, LR and RL).
     * @param grandChild the grandchild of aNode, eventually the newly inserted node.
     * @param child the child of the aNode, eventually the parent of newly inserted node.
     * @param aNode the nearest ancestor of the newly inserted node whose balance factor becomes +2 or –2 following the insert.
     * @return root of the rebalanced subtree.
     */
    private AVLNode balanceTreeOnInsert(AVLNode grandChild, AVLNode child, AVLNode aNode){
        int balance = aNode.getBalance();

        // Case 1 : LL (newly inserted node is in the left subtree of left subtree of aNode)
        if (balance > 1 && grandChild.getKey() < child.getKey()){
            return rightRotate(aNode);
        }
        // Case 2 : RR (newly inserted node is in the right subtree of the right subtree of aNode)
        if (balance < -1 && grandChild.getKey() > child.getKey()){
            return leftRotate(aNode);
        }
        // Case 3 : LR (newly inserted node is in the right subtree of left subtree of aNode)
        if (balance > 1 && grandChild.getKey() > child.getKey()) {
            aNode.setLeft(leftRotate(child));
            return rightRotate(aNode);
        }

        // Case 4 : RL (newly inserted node is in the left subtree of right subtree of aNode)
        if (balance < -1 && grandChild.getKey() < child.getKey()) {
            aNode.setRight(rightRotate(child));
            return leftRotate(aNode);
        }
        return null;
    }

    /**
     * Balances AVL tree after insertion. Handles 4 cases (R0 and R1, L-1 and L0, R-1 and L1).
     * @param aNode ancestor of the deleted  node whose balance factor has become 2 or –2 following a deletion.
     * @return root of the rebalanced subtree.
     */
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
        // Case 4 : L1
        if (balance < -1 && aNode.getRight().getBalance() > 0) {
            aNode.setRight(rightRotate(aNode.getRight()));
            return leftRotate(aNode);
        }
        return null;
    }


    /**
     * Do a right(anti-clockwise) rotation  of the subtree.
     * @param root of the subtree that needed to be rotated.
     * @return root of the balanced subtree.
     */
    private AVLNode rightRotate(AVLNode root) {
        AVLNode child = root.getLeft();
        AVLNode childRSubTree = child.getRight();
        child.setRight(root);
        root.setLeft(childRSubTree);
        return child;
    }

    /**
     * Do a left(clockwise) rotation of the subtree.
     * @param root of the subtree that needed to be rotated.
     * @return root of the balanced subtree.
     */
    private AVLNode leftRotate(AVLNode root) {
        AVLNode child = root.getRight();
        AVLNode childLSubTree = child.getLeft();
        child.setLeft(root);
        root.setRight(childLSubTree);
        return child;
    }

}

/**
 * Data structure for Binary Search Tree. This class is presented as abstract class because actual instances of BST is not needed.
 * @param <T> generic parameter for any concrete node type that inherited from abstract {@link Node greed.datastruct.Node.class}. {@link AVLNode greed.datastruct.AVLNode}
 *           is an example for a value T can take.
 */
abstract class BinarySearchTree<T extends Node<T>> {

    // root of the BST.
    private T root;
    //factory to create concrete classes.
    private final Supplier<? extends T> ctor;

    /**
     * Standard BST insertion operation. Implemented as recursive function in {@link #insert(int, Node, Stack)}.
     * @param key key of the new node to be inserted (Do not support duplicate keys).
     * @return stack that trace the path from root to the newly inserted node.
     */
    public Stack<T> insert(int key){
        Stack<T> stk = new Stack<>();
        this.root = insert(key, this.root, stk);
        return stk;
    }

    /**
     * Standard BST deletion. Implemented as recursive function in {@link #delete(int, Node, Stack)}.
     * @param key key of the node to be deleted (Will do nothing if the key is not in the tree).
     * @return stack that trace the path from root to the deleted node.
     */
    public Stack<T> delete(int key) {
        Stack<T> stk = new Stack<>();
        delete(key, this.root, stk);
        return stk;
    }

    /**
     * Standard BST search. Implemented as recursive function in {@link #search(int, Node)} (int, greed.datastruct.Node, Stack)}.
     * @param key key of the node to be searched (Will return null if the node is not found)
     * @return returns the key if present in the tree else NULL.
     */
    public Integer search(int key) {
        T searchedNode = search(key, this.root);
        return searchedNode != null ? searchedNode.getKey() : null;
    }

    /**
     * Standard BST range search. Implemented as recursive function in {@link #searchRange(int, int, Node, List)} (int, greed.datastruct.Node, Stack)}.
     * @param smallKey lower bound of the range.
     * @param bigKey upper bound of the key.
     * @return returns keys that are in the range smallKey ≤key ≤bigKey.
     */
    public List<Integer> searchRange(int smallKey, int bigKey){
        List<Integer> keys = new ArrayList<>();
        searchRange(smallKey, bigKey, this.root, keys);
        return keys;
    }

    /**
     * To string is override to pre order traversal.
     * @return string containing tree visualization.
     */
    @Override
    public String toString() {
        return traversePreOrder(this.root);
    }

    /**
     * Standard BST insert as a recursive function.
     * @param key key of the new node to be inserted (Do not support duplicate keys).
     * @param root root of the subtree.
     * @param stk stack for retracing path.
     * @return root of the subtree after insertion.
     */
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

    /**
     * Standard BST delete as a recursive function.
     * @param key key of the new node to be inserted (Do not support duplicate keys).
     * @param root root of the subtree.
     * @param stk stack for retracing path.
     * @return root of the subtree after deletion.
     */
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

    /**
     * Standard BST search as a recursive function.
     * @param key key to be searched.
     * @param root root of the subtree.
     * @return returns the key if present in the tree else NULL.
     */
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

    /**
     * Standard BST range search as a recursive function.
     * @param smallKey lower bound of the range.
     * @param bigKey upper bound of the range.
     * @param root root of the subtree.
     * @param keys returns keys that are in the range smallKey ≤key ≤bigKey.
     */
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

    /**
     * Get maximum of the given subtree
     * @param root root of the subtree.
     * @return node with maximum key value in the subtree.
     */
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

    /**
     * Standard preorder traversal. ( Used for validation of the implementation)
     * @param root root of the subtree.
     * @return string visualization of the BST.
     */
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

    /**
     * traverse nodes recursive function.
     * @param sb string builder
     * @param padding padding
     * @param pointer pointer
     * @param node root of subtree.
     * @param hasRightSibling boolean
     */
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

    /**
     * Construct a BST with given concrete node types
     * @param ctor factory for tree creation.
     */
    protected BinarySearchTree(Supplier<? extends T> ctor) {
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
