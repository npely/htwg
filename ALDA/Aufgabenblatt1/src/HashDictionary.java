import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HashDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {

    private LinkedList<Entry<K, V>>[] tab;
    private int size = 7;

    public HashDictionary() {
        this.tab = new LinkedList[];
    }

    private static boolean isPrime() {

    }

    private int hash(K key) {
        int adr = key.hashCode();
        if (adr < 0)
            adr = -adr;
        adr = adr % size;
        return adr;
    }

    //private static boolean isPrime


    @Override
    public V insert(K key, V value) {
        int adr = hash(key);
            for (var v : tab[adr]) {
                if (v.getKey().equals(key)) {
                    V old = v.getValue();
                    v.setValue(value);
                    return old;
                }
                else {
                    tab[adr] = new LinkedList<>(key, value);
                }
            }
    }

    @Override
    public V search(K key) {
        int adr = hash(key);
            if (tab[adr] == null)
                return null;
            else
                for (var v : tab[adr]) {
                    if (v.getKey().equals(key))
                        return v.getValue();
                }
        return null;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return null;
    }
}
