public class Main {

    public static void main(String[] args) {

        AVLNode rootNode = new AVLNode(1);
        AVLTree testAvlTree = AVLTree.initialize();

        System.out.println("Height at root: " + rootNode.getHeight());
        System.out.println("Balance at root: " + rootNode.getBalance());

        rootNode.setLeft(new AVLNode(2));

        System.out.println("Height at root: " + rootNode.getHeight());
        System.out.println("Balance at root: " + rootNode.getBalance());

        rootNode.getLeft().setLeft(new AVLNode(3));

        System.out.println("Height at root: " + rootNode.getHeight());
        System.out.println("Balance at root: " + rootNode.getBalance());

    }

}
