public class Main {

    public static void main(String[] args) {

        AVLTree testAvlTree = AVLTree.initialize();
        testAvlTree.insert(4);
        testAvlTree.insert(3);
        testAvlTree.insert(5);
        testAvlTree.insert(1);
        testAvlTree.insert(10);
        System.out.println(testAvlTree);

        testAvlTree.insert(20);
        System.out.println(testAvlTree);

        testAvlTree.insert(30);
        System.out.println(testAvlTree);

        testAvlTree.delete(3);
        System.out.println(testAvlTree);

        testAvlTree.delete(4);
        System.out.println(testAvlTree);

        testAvlTree.insert(4);
        System.out.println(testAvlTree);

        System.out.println(testAvlTree.search(10));
        System.out.println(testAvlTree.search(11));

        System.out.println(testAvlTree.searchRange(2, 6));
        System.out.println(testAvlTree.searchRange(10, 20));

    }

}
