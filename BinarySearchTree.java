import java.util.LinkedList;
import java.util.Stack;

public class BinarySearchTree<T extends Comparable<T>> implements SortedCollectionInterface<T> {

    protected static class Node<T> {
        public T data;

        public Node<T> up;

        @SuppressWarnings("unchecked")
        public Node<T>[] down = (Node<T>[]) new Node[2];

        public Node(T data) {
            this.data = data;
        }

        public boolean isRightChild() {
            return this.up != null && this.up.down[1] == this;
        }

    }

    protected Node<T> root; 
    protected int size = 0; 


    public boolean insert(T data) throws NullPointerException {
        if (data == null)
            throw new NullPointerException("Cannot insert data value null into the tree.");
        return this.insertHelper(new Node<>(data));
    }


    protected boolean insertHelper(Node<T> newNode) throws NullPointerException {
        if (newNode == null)
            throw new NullPointerException("new node cannot be null");

        if (this.root == null) {
            root = newNode;
            size++;
            return true;
        } else {
            Node<T> currentSearchNode = this.root;
            while (true) {
                int compare = newNode.data.compareTo(currentSearchNode.data);
                if (compare == 0) {
                    return false;
                } else if (compare < 0) {
                    if (currentSearchNode.down[0] == null) {
                        currentSearchNode.down[0] = newNode;
                        newNode.up = currentSearchNode;
                        this.size++;
                        return true;
                    } else {
                        currentSearchNode = currentSearchNode.down[0];
                    }
                } else {
                    if (currentSearchNode.down[1] == null) {
                        currentSearchNode.down[1] = newNode;
                        newNode.up = currentSearchNode;
                        this.size++;
                        return true;
                    } else {
                        currentSearchNode = currentSearchNode.down[1];
                    }
                }
            }
        }
    }

    protected void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
        if (child == null || parent == null) {
            throw new IllegalArgumentException("Null child or parent Node");
        }

        if (parent.down[1] == child) {
            child = parent.down[1];
            parent.down[1] = child.down[0];
            child.up = parent;
            child.up = parent.up;

            if (parent.up == null) {
                this.root = child;

            }

            else if (parent == parent.up.down[0]) {
                parent.up.down[0] = child;

            }

            else {
                parent.up.down[1] = child;
            }
            child.down[0] = parent;
            parent.up = child;
        }

        if (parent.down[0] == child) {
            child = parent.down[0];
            parent.down[0] = child.down[1];
            child.up = parent;
            child.up = parent.up;

            if (parent.up == null) {
                this.root = child;
            }

            else if (parent == parent.up.down[1]) {
                parent.up.down[1] = child;

            }

            else {
                parent.up.down[0] = child;
            }
            child.down[1] = parent;
            parent.up = child;
        }

    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }


    public boolean contains(Comparable<T> data) {
        if (data == null) {
            throw new NullPointerException("This tree cannot store null references.");
        } else {
            Node<T> nodeWithData = this.findNode(data);
            return (nodeWithData != null);
        }
    }
    
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    protected Node<T> findNode(Comparable<T> data) {
        Node<T> currentSearchNode = this.root;
        while (currentSearchNode != null) {
            int compare = data.compareTo(currentSearchNode.data);
            if (compare == 0) {
                return currentSearchNode;
            } else if (compare < 0) {
                if (currentSearchNode.down[0] == null) {
                    return null;
                }
                currentSearchNode = currentSearchNode.down[0];
            } else {
                if (currentSearchNode.down[1] == null) {
                    return null;
                }
                currentSearchNode = currentSearchNode.down[1];
            }
        }
        return null;
    }

    public String toInOrderString() {

        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            Stack<Node<T>> nodeStack = new Stack<>();
            Node<T> currentSearchNode = this.root;
            while (!nodeStack.isEmpty() || currentSearchNode != null) {
                if (currentSearchNode == null) {
                    Node<T> popped = nodeStack.pop();
                    sb.append(popped.data.toString());
                    if (!nodeStack.isEmpty() || popped.down[1] != null)
                        sb.append(", ");
                    currentSearchNode = popped.down[1];
                } else {
                    nodeStack.add(currentSearchNode);
                    currentSearchNode = currentSearchNode.down[0];
                }
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    public String toLevelOrderString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this.root);
            while (!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                if (next.down[0] != null)
                    q.add(next.down[0]);
                if (next.down[1] != null)
                    q.add(next.down[1]);
                sb.append(next.data.toString());
                if (!q.isEmpty())
                    sb.append(", ");
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    public String toString() {
        return "level order: " + this.toLevelOrderString() + "\nin order: "
                + this.toInOrderString();
    }

    public static boolean test1() {
        try {
            BinarySearchTree<Integer> tree = new BinarySearchTree<>();
            tree.rotate(null, tree.root);
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    public static boolean test2() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.insert(2);
        tree.insert(1);
        tree.rotate(tree.root.down[0], tree.root);

        if (!tree.toLevelOrderString().equals("[ 1, 2 ]")) {
            System.out.println(tree.toLevelOrderString());
            return false;
        }
        return true;
    }

    public static boolean test3() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        Node<Integer> one = tree.root;
        Node<Integer> two = one.down[1];
        Node<Integer> three = two.down[1];
        tree.rotate(two, one);

        if (!tree.toLevelOrderString().equals("[ 2, 1, 3 ]")) {
            System.out.println(tree.toLevelOrderString());
            return false;
        }

        return true;
    }

    public static boolean test4() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.insert(3);
        tree.insert(2);
        tree.insert(1);
        Node<Integer> three = tree.root;
        Node<Integer> two = three.down[0];
        Node<Integer> one = two.down[0];
        tree.rotate(two, three);

        if (!tree.toLevelOrderString().equals("[ 2, 1, 3 ]")) {
            System.out.println(tree.toLevelOrderString());
            return false;
        }

        return true;
    }
    public static void main(String[] args) {
        System.out.println("Test 1 passed: " + test1());
        System.out.println("Test 2 passed: " + test2());
        System.out.println("Test 3 passed: " + test3());
        System.out.println("Test 4 passed: " + test4());

    }

}
