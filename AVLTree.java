
public class AVLTree extends Tree<AVLNode>{


    public AVLTree(AVLNode root) {
        super(root);
    }
}


abstract class Tree<N extends Node<N>> {

     private N root;

    public Tree(N root) {
        this.root = root;
    }

    public N getRoot() {
        return root;
    }

    public void setRoot(N root) {
        this.root = root;
    }
}
