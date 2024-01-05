import java.util.Iterator;

public interface IterableMultiKeySortedCollectionInterface<T extends Comparable<T>> extends SortedCollectionInterface<KeyListInterface<T>>, Iterable<T> {


    public boolean insertSingleKey(T key);

    public int numKeys();

    public Iterator<T> iterator();

    public void setIterationStartPoint(Comparable<T> startPoint);

}