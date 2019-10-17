import java.util.Iterator;

public class HashDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {

    private Entry<K, V> tab;
    private int size;
    private static final int DEF_CAPACITY = 32;


    private int hash(K key) {
        int adr = key.hashCode();
        if (adr < 0)
            adr = -adr;
        adr = adr % size;
        return adr;
    }


    @Override
    public V insert(K key, V value) {

    }

    @Override
    public V search(K key) {
        for (int i = 0; i < size; i++) {
            if ()
        }
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return null;
    }
}
