public class Main {

    public static void main(String[] args) {

        AVLTree testAvlTree = AVLTree.initialize();
        testAvlTree.insert(4);
        testAvlTree.insert(3);
        testAvlTree.insert(5);
        testAvlTree.insert(1);
        testAvlTree.insert(10);

        System.out.println("Height at root: " + testAvlTree);
//        System.out.println("Balance at root: " + rootNode.getBalance());
//
//        rootNode.setLeft(new AVLNode(2));
//
//        System.out.println("Height at root: " + rootNode.getHeight());
//        System.out.println("Balance at root: " + rootNode.getBalance());
//
//        rootNode.getLeft().setLeft(new AVLNode(3));
//
//        System.out.println("Height at root: " + rootNode.getHeight());
//        System.out.println("Balance at root: " + rootNode.getBalance());

    }

}
