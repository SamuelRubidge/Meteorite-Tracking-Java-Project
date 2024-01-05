import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import static org.junit.Assert.assertEquals;

public class IterableMultiKeyRBT<T extends Comparable<T>>
        extends BinarySearchTree<KeyListInterface<T>>
        implements IterableMultiKeySortedCollectionInterface<T> {

    private Comparable<T> iterationStartPoint;
    private int numKeys;

    public boolean insertSingleKey(T key) {
        KeyList<T> keyList = new KeyList<>(key);
        Node<KeyListInterface<T>> node = this.findNode(keyList);
        if (node != null) {
            node.data.addKey(key);
            this.numKeys++;
            return false;
        }

        
        
        else {
            this.insertHelper(new Node<>(keyList));
            this.numKeys++;
            return true;
        }
    }

    public int numKeys() {
        return numKeys;
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Stack<Node<KeyListInterface<T>>> stack = getStartStack();
            Iterator<T> keyIterator;
            Iterator<Node<KeyListInterface<T>>> rbtIterator;
            public void inOrder(ArrayList<Node<KeyListInterface<T>>> tree,
                    Node<KeyListInterface<T>> node) {
                if (node == null)
                    return;
                inOrder(tree, node.down[0]);
                tree.add(node);
                inOrder(tree, node.down[1]);
            }
            @Override
            public boolean hasNext() {
                if (this.keyIterator != null && this.keyIterator.hasNext())
                    return true;
                else if (this.rbtIterator != null && this.rbtIterator.hasNext())
                    return true;
                else if (this.stack.isEmpty())
                    return false;
                else
                    return true;
            }
            @Override
            public T next() {
                if (this.keyIterator != null && this.keyIterator.hasNext())
                    return this.keyIterator.next();
                else if (this.rbtIterator != null && this.rbtIterator.hasNext()) {
                    this.keyIterator = this.rbtIterator.next().data.iterator();
                    return this.keyIterator.next();
                } else {
                    Node<KeyListInterface<T>> node = this.stack.pop();
                    this.keyIterator = node.data.iterator();
                    if (node.down[1] != null) {
                        ArrayList<Node<KeyListInterface<T>>> tree = new ArrayList<>();
                        this.inOrder(tree, node.down[1]);
                        this.rbtIterator = tree.iterator();
                    }
                    return this.keyIterator.next();
                }
            }
        };

    }

    protected Stack<Node<KeyListInterface<T>>> getStartStack() {
        Stack<Node<KeyListInterface<T>>> stack = new Stack<>();

        if (iterationStartPoint == null) {
            Node<KeyListInterface<T>> currentNode = root;
            while (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.down[0];
            }

        } else {
            Node<KeyListInterface<T>> currentNode = root;
            while (currentNode != null) {
                int compareVal = iterationStartPoint.compareTo(currentNode.data.iterator().next());
                if (compareVal <= 0) {

                    stack.push(currentNode);
                    currentNode = currentNode.down[0];
                } else {

                    currentNode = currentNode.down[1];
                }
            }
        }

        return stack;
    }

    @Override
    public void clear() {
        super.clear();
        numKeys = 0;
    }


    public void setIterationStartPoint(Comparable<T> startPoint) {
        this.iterationStartPoint = startPoint;
    }

    @Test
    public void testKeyInsertion() {
        IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>();
        tree.insertSingleKey(555);
        tree.insertSingleKey(555);
        assertEquals(2, tree.numKeys());
    }


    @Test
    public void testIterator() {
        IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>();

        tree.insertSingleKey(1);
        tree.insertSingleKey(2);
        tree.insertSingleKey(3);
        tree.insertSingleKey(4);
        Iterator<Integer> iter = tree.iterator();
     
        assertEquals(Integer.valueOf(1), iter.next());
        assertEquals(Integer.valueOf(2), iter.next());
        assertEquals(Integer.valueOf(3), iter.next());
        assertEquals(Integer.valueOf(4), iter.next());

        assertEquals(false, iter.hasNext());
    }


    @Test
    public void testSetIterationStartPoint() {
        IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>();
        tree.insertSingleKey(1);
        tree.insertSingleKey(2);
        tree.insertSingleKey(3);
        tree.insertSingleKey(4);
        tree.setIterationStartPoint(2);
        Iterator<Integer> iter = tree.iterator();
        assertEquals(Integer.valueOf(2), iter.next());
        assertEquals(Integer.valueOf(3), iter.next());
        assertEquals(Integer.valueOf(4), iter.next());
        assertEquals(false, iter.hasNext());
    }
}