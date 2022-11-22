package greed.datastruct;

import greed.datastruct.common.BinarySearchTree;
import greed.datastruct.common.Node;

import java.util.*;

/**
 * Data structure for AVL tree which inherits attributes and functions from its predecessor: Binary Search Tree (BST).
 * The code is structured to maximize inheritance from standard Binary Search Tree.
 * The Standard Binary Search Tree is in {@link BinarySearchTree BinarySerachTree.class}.
 * {@link AVLNode greed.datastruct.AVLNode}(concrete implementation of abstract {@link Node greed.datastruct.common.Node}) is used to represent single node in the tree.
 * Supports initialization, insertion, deletion and search on AVL tree while self-balancing after every inset and delete.
 */
public class AVLTree extends BinarySearchTree<AVLNode> {

    /**
     * private constructor, {@link #initialize() initialize()} is to initialize a binary tree.
     */
    private AVLTree() {
        super(AVLNode::new);
    }


    /**
     * Construct an empty AVL tree.
     * @return empty AVL tree.
     */
    public static AVLTree initialize(){
        return new AVLTree();
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

