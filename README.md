# AVLTree
###### Java implementation of self-balancing AVL tree



AVL tree is a self-balancing Binary Search Tree (BST) where the difference between heights of left and right subtrees cannot be more than one for all nodes. This project implements a AVL Tree. The data is given in the form (key) with no duplicates, This implemetation supports following operations:

1. Initialize (): create a new AVL tree
2. Insert (key)
3. Delete (key)
4. Search (key): returns the key if present in the tree else NULL
5. Search (key1, key2): returns keys that are in the range key1 ≤key ≤key2


AVL tree is a self-balancing Binary Search Tree (BST) where the difference between heights of left and right subtrees cannot be more than one for all nodes. In this project, I have developed and tested an AVL Tree. Since the AVL tree is an extension of standard BST The code is structured to maximize inheritance from the standard Binary Search Tree. 




 This project is structured into three files and five classes as above :

AVLNode.java: contains the node structures.
Node: This is an abstract node to represent trees. 
AVLNode: This is the concrete representation of Node used in the implementation of AVL Tree.
AVLBST.java -: contains the AVL tree data structure.
BinarySearchTree: Standard binary search tree with insert, delete and search.
AVLBST: Concrete AVL tree implementation using BinarySearchTree as a superclass. In simple words, this class does the rebalancing using AVL properties on top of standard BST operations.
avltree: main program function with file operations
Makefile : to make the java classes.


#### To Compile and Run

`./makefile` or `javac greed.datastruct.Main.java`

and

`java avltree input_file_name`

 
The rest of the section presents the function prototypes.


# Documentation

#### `public class Main`

This program, contains a small <a href="https://en.wikipedia.org/wiki/AVL_tree">AVL Tree</a>. Main class is the entrypoint of the program, it handle input output file operations and decoding of the input file.

#### `public static void main(String[] args)`

This is the main method of the program.

 * **Parameters:** `args` — takes one command line argument for input file, defaults to "input.txt" if not specified.

     This method call decodeAndRunCommands() to decode the input file line by line run respective commands on AVL tree.

     The final output is written to "output_file.txt".

#### `public static AVLTree decodeAndRunCommands(String command, AVLTree avlBinaryTree, BufferedWriter writer) throws IOException`

Takes one command and run the respective operation on AVL tree. Supported commands are Initialize, Insert, Delete and Search.

 * **Parameters:**
   * `command` — Command to run as a string ( eg -: Initialize(), Insert (60), Delete(23), Search(23), Search(2,10)).
   * `avlBinaryTree` — The AVL tree before the operation (defaults to null at the start).
   * `writer` — BufferedWriter representing the output file.
 * **Returns:** Returns the AVL tree after the operation.
 * **Exceptions:** `IOException` — Signals that an I/O exception of some sort has occurred regarding output file (eg -: output file is not writable)

#### `public static void testAVLTree()`

This method contains test cases to evaluate the implementation. Not called by the main program by default.

#### `public class AVLTree extends BinarySearchTree<AVLNode>`

Data structure for AVL tree which inherits attributes and functions from its predecessor: Binary Search Tree (BST). The code is structured to maximize inheritance from standard Binary Search Tree. The Standard Binary Search Tree is in {@link BinarySearchTree BinarySerachTree.class}. {@link AVLNode greed.datastruct.AVLNode}(concrete implementation of abstract {@link Node greed.datastruct.common.Node}) is used to represent single node in the tree. Supports initialization, insertion, deletion and search on AVL tree while self-balancing after every inset and delete.

#### `private AVLTree()`

private constructor, {@link #initialize() initialize()} is to initialize a binary tree.

#### `public static AVLTree initialize()`

Construct an empty AVL tree.

 * **Returns:** empty AVL tree.

#### `@Override public Stack<AVLNode> insert(int key)`

Inserts a key to the existing AVL tree. This method overrides the functionality of Binary Search Tree insertion. It uses standard Binary Search Tree insertion of it's super class() ({@link BinarySearchTree BinarySerachTree.class}) and perform re-balancing on the output of BST insertion.

 * **Parameters:** `key` — key of the new node to be inserted (Do not support duplicate keys).
 * **Returns:** always returns null although method signature has a stack due to overriding.

#### `@Override public Stack<AVLNode> delete(int key)`

Deletes a key in the existing AVL tree. This method overrides the functionality of Binary Search Tree deletion. It uses standard binary tree deletion of it's super class and perform re-balancing on the output of BST insertion.

 * **Parameters:** `key` — key of the node to be deleted (Will do nothing if the key is not in the tree).
 * **Returns:** always returns null although method signature has a stack due to overriding.

#### `private AVLNode balanceTreeOnInsert(AVLNode grandChild, AVLNode child, AVLNode aNode)`

Balances AVL tree after insertion. Handles 4 cases (LL, RR, LR and RL).

 * **Parameters:**
   * `grandChild` — the grandchild of aNode, eventually the newly inserted node.
   * `child` — the child of the aNode, eventually the parent of newly inserted node.
   * `aNode` — the nearest ancestor of the newly inserted node whose balance factor becomes +2 or –2 following the insert.
 * **Returns:** root of the rebalanced subtree.

#### `private AVLNode balanceTreeOnDelete(AVLNode aNode)`

Balances AVL tree after insertion. Handles 4 cases (R0 and R1, L-1 and L0, R-1 and L1).

 * **Parameters:** `aNode` — ancestor of the deleted  node whose balance factor has become 2 or –2 following a deletion.
 * **Returns:** root of the rebalanced subtree.

#### `private AVLNode rightRotate(AVLNode root)`

Do a right(anti-clockwise) rotation of the subtree.

 * **Parameters:** `root` — of the subtree that needed to be rotated.
 * **Returns:** root of the balanced subtree.

#### `private AVLNode leftRotate(AVLNode root)`

Do a left(clockwise) rotation of the subtree.

 * **Parameters:** `root` — of the subtree that needed to be rotated.
 * **Returns:** root of the balanced subtree.

#### `public abstract class BinarySearchTree<T extends Node<T>>`

Data structure for Binary Search Tree. This class is presented as abstract class because actual instances of BST is not needed.

 * **Parameters:** `<T>` — generic parameter for any concrete node type that inherited from abstract {@link Node greed.datastruct.common.Node.class}. {@link AVLNode greed.datastruct.AVLNode}

     is an example for a value T can take.

#### `public Stack<T> insert(int key)`

Standard BST insertion operation. Implemented as recursive function in {@link #insert(int, Node, Stack)}.

 * **Parameters:** `key` — key of the new node to be inserted (Do not support duplicate keys).
 * **Returns:** stack that trace the path from root to the newly inserted node.

#### `public Stack<T> delete(int key)`

Standard BST deletion. Implemented as recursive function in {@link #delete(int, Node, Stack)}.

 * **Parameters:** `key` — key of the node to be deleted (Will do nothing if the key is not in the tree).
 * **Returns:** stack that trace the path from root to the deleted node.

#### `public Integer search(int key)`

Standard BST search. Implemented as recursive function in {@link #search(int, Node)} (int, greed.datastruct.common.Node, Stack)}.

 * **Parameters:** `key` — key of the node to be searched (Will return null if the node is not found)
 * **Returns:** returns the key if present in the tree else NULL.

#### `public List<Integer> searchRange(int smallKey, int bigKey)`

Standard BST range search. Implemented as recursive function in {@link #searchRange(int, int, Node, List)} (int, greed.datastruct.common.Node, Stack)}.

 * **Parameters:**
   * `smallKey` — lower bound of the range.
   * `bigKey` — upper bound of the key.
 * **Returns:** returns keys that are in the range smallKey ≤key ≤bigKey.

#### `@Override public String toString()`

To string is override to pre order traversal.

 * **Returns:** string containing tree visualization.

#### `private T insert(int key, T root, Stack<T> stk)`

Standard BST insert as a recursive function.

 * **Parameters:**
   * `key` — key of the new node to be inserted (Do not support duplicate keys).
   * `root` — root of the subtree.
   * `stk` — stack for retracing path.
 * **Returns:** root of the subtree after insertion.

#### `private T delete(int key, T root, Stack<T> stk)`

Standard BST delete as a recursive function.

 * **Parameters:**
   * `key` — key of the new node to be inserted (Do not support duplicate keys).
   * `root` — root of the subtree.
   * `stk` — stack for retracing path.
 * **Returns:** root of the subtree after deletion.

#### `private T search(int key, T root)`

Standard BST search as a recursive function.

 * **Parameters:**
   * `key` — key to be searched.
   * `root` — root of the subtree.
 * **Returns:** returns the key if present in the tree else NULL.

#### `private void searchRange(int smallKey, int bigKey, T root, List<Integer> keys)`

Standard BST range search as a recursive function.

 * **Parameters:**
   * `smallKey` — lower bound of the range.
   * `bigKey` — upper bound of the range.
   * `root` — root of the subtree.
   * `keys` — returns keys that are in the range smallKey ≤key ≤bigKey.

#### `private int getMax(T root)`

Get maximum of the given subtree

 * **Parameters:** `root` — root of the subtree.
 * **Returns:** node with maximum key value in the subtree.

#### `public String traversePreOrder(T root)`

Standard preorder traversal. ( Used for validation of the implementation)

 * **Parameters:** `root` — root of the subtree.
 * **Returns:** string visualization of the BST.

#### `private void traverseNodes(StringBuilder sb, String padding, String pointer, T node, boolean hasRightSibling)`

traverse nodes recursive function.

 * **Parameters:**
   * `sb` — string builder
   * `padding` — padding
   * `pointer` — pointer
   * `node` — root of subtree.
   * `hasRightSibling` — boolean

#### `protected BinarySearchTree(Supplier<? extends T> ctor)`

Construct a BST with given concrete node types

 * **Parameters:** `ctor` — factory for tree creation.

#### `public class AVLNode extends Node<AVLNode>`

AVL node inherits from abstract tree node. Has an additional parameter of height in each node.

#### `public int getBalance()`

get balance factor of the node.

 * **Returns:** balance factor.

#### `private void calculateHeight()`

calculate height of the node.

#### `public abstract class Node<N extends Node<N>>`

Represents a BST node.

 * **Parameters:** `<N>` — generic parameter for any concrete node type that inherited from abstract {@link Node greed.datastruct.Node.class}. {@link AVLNode greed.datastruct.AVLNode}

     *           is an example for a value N can take.
