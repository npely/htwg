import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HashDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {

    private LinkedList<Entry<K, V>>[] tab;
    private int size;
    private int n;

    public HashDictionary(int n) {
        this.tab = new LinkedList[n];
        this.n = n;
        this.size = 7;
    }

    private static int getPrime(int n) {
        for (int i = 2; i < n; i++) {
            if (n % i == 0)
                n++;
            else
                break;
        }
        return n;
    }

    private int hash(K key) {
        int adr = key.hashCode();
        if (adr < 0)
            adr = -adr;
        adr = adr % tab.length;
        return adr;
    }

    private void ensureCapacity() {

    }


    @Override
    public V insert(K key, V value) {
        int adr = hash(key);

        if(tab[adr] != null) {
            for (Entry<K, V> v : tab[adr]) {
                if (v.getKey().equals(key)) {
                    V old = v.getValue();
                    v.setValue(value);
                    return old;
                }
            }
        }
        tab[adr] = new LinkedList<Entry<K, V>>();
        tab[adr].add(new Entry<K, V>(key, value));
        size++;

        return null;
    }

    @Override
    public V search(K key) {
        int adr = hash(key);
            if (tab[adr] == null)
                return null;
            else
                for (Entry<K, V> v : tab[adr]) {
                    if (v.getKey().equals(key))
                        return v.getValue();
                }
        return null;
    }


    @Override
    public V remove(K key) {
        int adr = hash(key);

        for (Entry<K, V> v : tab[adr]) {
            if (v.getKey().equals(key)) {
                V old = v.getValue();
                tab[adr].remove(v);
                return old;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<Dictionary.Entry<K, V>>() {

            int index = -1;
            Iterator<Entry<K, V>> listIterator;

            @Override
            public boolean hasNext() {
                if (listIterator != null && listIterator.hasNext())
                    return true;
                while (++index < tab.length) {
                    if (tab[index] != null) {
                        listIterator = tab[index].iterator();
                        return listIterator.hasNext();
                    }
                }
                return false;
            }

            @Override
            public Entry<K, V> next() {
                return listIterator.next();
            }
        };
    }
}
