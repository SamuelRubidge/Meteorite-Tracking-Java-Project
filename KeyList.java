import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;

public class KeyList<T extends Comparable<T>> implements KeyListInterface<T> {

    protected List<T> keyList;

    public KeyList(T firstKey) {
        if (firstKey == null) throw new NullPointerException("keys cannot be null");
        keyList = new LinkedList<>();
        keyList.add(firstKey);
    }

    public void addKey(T newKey) {
        if (keyList.get(0).compareTo(newKey) != 0) {
            throw new IllegalArgumentException("compareTo for keys in same list must return 0");
        }
        keyList.add(newKey);
    }


	@Override
    public boolean containsKey(T key) {
        return this.keyList.contains(key);
    }

    @Override
    public int compareTo(KeyListInterface<T> o) {
        return keyList.get(0).compareTo(o.iterator().next());
    }


    @Override
    public Iterator<T> iterator() {
        return keyList.iterator();
    }

}
