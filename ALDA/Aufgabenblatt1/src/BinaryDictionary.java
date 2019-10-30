import com.sun.source.tree.BinaryTree;

import java.util.Iterator;

public class BinaryDictionary <K extends Comparable<? super K>, V> implements Dictionary<K, V> {

    private Node<K, V> root = null;

    static private class Node<K extends Comparable<? super K>, V> {
        Node<K, V> left;
        Node<K, V> right;
        Entry<K, V> data;
        int height;

        public Node(Entry<K, V> data) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.height = height;
        }
    }

    @Override
    public V insert(K key, V value) {
        return null;
    }

    @Override
    public V search(K key) {
        return searchR(key, root);
    }

    private V searchR(K key, Node<K, V> p) {
        if (p == null)
            return null;
        else if (key.compareTo(p.data.getKey()) < 0)
            return searchR(key, p.left);
        else if (key.compareTo(p.data.getKey()) > 0)
            return searchR(key, p.right);
        else
            return p.data.getValue();
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
