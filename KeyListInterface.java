public interface KeyListInterface<T extends Comparable<T>> extends Comparable<KeyListInterface<T>>, Iterable<T> {

    public void addKey(T newKey);

    public boolean containsKey(T key);

}