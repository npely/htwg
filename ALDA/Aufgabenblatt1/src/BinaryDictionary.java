import com.sun.source.tree.BinaryTree;

import java.util.Iterator;

public class BinaryDictionary <K extends Comparable<? super K>, V> implements Dictionary<K, V> {

    private Node<K, V> root;

    static private class Node<K extends Comparable<? super K>, V> {
        Node left;
        Node right;
        Entry<K, V> data;

        public Node() {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    @Override
    public V insert(K key, V value) {
        return null;
    }

    @Override
    public V search(K key) {
        return null;
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
