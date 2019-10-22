import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class HashDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {

    public LinkedList<Entry<K, V>>[] tab;
    private int size;
    private int load;

    public HashDictionary(int load) {
        this.tab = new LinkedList[load];
        this.load = load;
        this.size = 0;
    }

    private static boolean isPrime(int n) {
        for (int i = 2; i * i < n; i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    private int hash(K key) {
        int adr = key.hashCode();
        if (adr < 0)
            adr = -adr;
        return adr % (tab.length - 1);
    }

    private void ensureCapacity() {
        int newload = 2 * tab.length;

        while (!isPrime(newload))
            ++newload;

        HashDictionary<K, V> newtab = new HashDictionary<>(newload);

        for (LinkedList<Entry<K, V>> v : tab) {
            if (v == null)
                continue;
            for (Entry<K, V> e : v)
                newtab.insert(e.getKey(), e.getValue());
        }

        tab = new LinkedList[newload];

        for (LinkedList<Entry<K, V>> v : newtab.tab) {
            if (v == null)
                continue;
            for (Entry<K, V> e : v)
                this.insert(e.getKey(), e.getValue());
        }
    }


    @Override
    public V insert(K key, V value) {
        int adr = hash(key);

        if(search(key) != null) {
            for (Entry<K, V> v : tab[adr]) {
                if (v.getKey().equals(key)) {
                    V old = v.getValue();
                    v.setValue(value);
                    return old;
                }
            }
        }

        if (size + 1 == tab.length)
            ensureCapacity();

        if (tab[adr] == null) {

            tab[adr] = new LinkedList<Entry<K, V>>();
            tab[adr].add(new Entry<K, V>(key, value));
            ++size;
        }
        else {
            tab[adr].add(new Entry<K, V>(key, value));
            ++size;
        }
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
        return new Iterator<Entry<K, V>>() {

            int tabindex = -1;

            @Override
            public boolean hasNext() {
                while (++tabindex < tab.length) {
                    if (tab[tabindex] != null)
                        return tab[tabindex].listIterator().hasNext();
                }
                return false;
            }

            @Override
            public Entry<K, V> next() {
                return tab[tabindex].listIterator().next();
            }
        };
    }
}


