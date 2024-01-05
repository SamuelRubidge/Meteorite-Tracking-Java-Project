import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    protected static class RBTNode<T> extends Node<T> {
        public int blackHeight = 0;

        public RBTNode(T data) {
            super(data);
        }

        public RBTNode<T> getUp() {
            return (RBTNode<T>) this.up;
        }

        public RBTNode<T> getDownLeft() {
            return (RBTNode<T>) this.down[0];
        }

        public RBTNode<T> getDownRight() {
            return (RBTNode<T>) this.down[1];
        }
    }

    protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> node) {
        while (node.getUp() != null && node.getUp().blackHeight == 0) {

            if(node.getUp().getUp() == null) {
                node.getUp().blackHeight = 1; 
                continue;
            }

            if (node.getUp() == node.getUp().getUp().getDownLeft()) {
                RBTNode<T> rightUncleNode = node.getUp().getUp().getDownRight();

                if (rightUncleNode != null && rightUncleNode.blackHeight == 0) {
                    node.getUp().blackHeight = 1;
                    rightUncleNode.blackHeight = 1;
                    node.getUp().getUp().blackHeight = 0;
                    node = node.getUp().getUp();
                    continue;
                }

                else {
                    if (node == node.getUp().getDownRight()) {
                        node = node.getUp();
                        rotate(node.getDownRight(), node);
                        continue;
                    }

                    if (node == node.getUp().getDownLeft()) {
                        node.getUp().blackHeight = 1;
                        node.getUp().getUp().blackHeight = 0;
                        rotate(node.getUp(), node.getUp().getUp());
                        continue;

                    }
                }

            }

            if (node.getUp() == node.getUp().getUp().getDownRight()) {
                RBTNode<T> leftUncleNode = node.getUp().getUp().getDownLeft();

                if (leftUncleNode != null && leftUncleNode.blackHeight == 0) {
                    node.getUp().blackHeight = 1;
                    leftUncleNode.blackHeight = 1;
                    node.getUp().getUp().blackHeight = 0;
                    node = node.getUp().getUp();
                    continue;


                }
                else {
                    if (node == node.getUp().getDownLeft()) {
                        node = node.getUp();
                        rotate(node.getDownLeft(), node);
                        continue;
                    }

                    if (node == node.getUp().getDownRight()) {
                        node.getUp().blackHeight = 1;
                        node.getUp().getUp().blackHeight = 0;
                        rotate(node.getUp(), node.getUp().getUp());
                        continue;
                    }
                }
            }

        }

        if (node.getUp() == null) {
            node.blackHeight = 1;
        }
    }

    @Override
    public boolean insert(T data) throws NullPointerException {
        if (data == null)
            throw new NullPointerException("Cannot insert data value null into the tree.");
        RBTNode<T> node = new RBTNode<>(data);
        boolean insertionWorked = insertHelper(node);
        enforceRBTreePropertiesAfterInsert(node);
        return insertionWorked;

    }


    @Test
    public void redRootTest() {
        RBTNode<Integer> root = new RBTNode<>(1);
        root.blackHeight = 0;
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.root = root;
        tree.insert(3);
        Assertions.assertEquals(1, root.blackHeight);
        Assertions.assertEquals(0, root.getDownRight().blackHeight);

    }

    @Test
    public void blackUncleTest() {
        RBTNode<Integer> root = new RBTNode<>(20);
        root.blackHeight = 1;
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.root = root;
        tree.insert(15);
        tree.insert(5);
        Assertions.assertEquals("[ 15, 5, 20 ]", tree.toLevelOrderString());
        Assertions.assertEquals(1, root.getUp().blackHeight);
        Assertions.assertEquals(0, root.getUp().getDownLeft().blackHeight);
        Assertions.assertEquals(0, root.blackHeight);

    }

    @Test
    public void redUncleTest() {
        RBTNode<Integer> root = new RBTNode<>(5);
        root.blackHeight = 1;
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.root = root;
        tree.insert(1);
        tree.insert(10);
        tree.insert(11);
        Assertions.assertEquals(1, root.getDownRight().blackHeight);
        Assertions.assertEquals(1, root.getDownLeft().blackHeight);
        Assertions.assertEquals(1, root.blackHeight);

    }
}
