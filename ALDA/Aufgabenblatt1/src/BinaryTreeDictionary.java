import java.util.Iterator;

public class BinaryTreeDictionary <K extends Comparable<? super K>, V> implements Dictionary<K, V> {

    private Node<K, V> root = null;

    static private class Node<K extends Comparable<? super K>, V> {
        K key;
        V value;
        Node<K, V> parent;
        Node<K, V> left;
        Node<K, V> right;
        int height;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            this.height = 0;
            this.parent = null;
        }
    }

    private Node<K, V> leftMostDescendant(Node<K, V> p) {
        assert p != null;
        while (p.left != null)
            p = p.left;
        return p;
    }

    private Node<K, V> parentOfLeftMostAncestor(Node<K, V> p) {
        assert p != null;
        while (p.parent != null && p.parent.right == p)
            p = p.parent;
        return p.parent;
    }

    private int getHeight(Node<K, V> p) {
        if (p == null)
            return -1;
        else
            return p.height;
    }

    private int getBalance(Node<K, V> p) {
        if (p == null)
            return 0;
        else
            return getHeight(p.right) - getHeight(p.left);
    }

    private Node<K, V> balance(Node<K, V> p) {
        if (p == null)
            return null;
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        if (getBalance(p) == -2) {
            if (getBalance(p.left) <= 0)
                p = rotateRight(p);
            else
                p = rotateLeftRight(p);
        } else if (getBalance(p) == 2) {
            if (getBalance(p.right) >= 0)
                p = rotateLeft(p);
            else
                p = rotateRightLeft(p);
        }
        return p;
    }

    private Node<K, V> rotateRight(Node<K, V> p) {
        assert p.left != null;
        Node<K, V> q = p.left;
        p.left = q.right;

        if (p.left != null)
            p.left.parent = p;

        q.right = p;

        if (q.right != null)
            q.right.parent = q;

        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        q.height = Math.max(getHeight(q.left), getHeight(q.right)) + 1;
        return q;
    }

    private Node<K, V> rotateLeft(Node<K, V> p) {
        assert p.right != null;
        Node<K, V> q = p.right;
        p.right = q.left;

        if (p.right != null)
            p.right.parent = p;

        q.left = p;

        if (q.left != null)
            q.left.parent = q;

        p.height = Math.max(getHeight(p.right), getHeight(p.left)) + 1;
        q.height = Math.max(getHeight(q.right), getHeight(q.left)) + 1;
        return q;
    }

    private Node<K, V> rotateLeftRight(Node<K, V> p) {
        assert p.left != null;
        p.left = rotateLeft(p.left);
        return rotateRight(p);
    }

    private Node<K, V> rotateRightLeft(Node<K, V> p) {
        assert p.right != null;
        p.right = rotateRight(p.right);
        return rotateLeft(p);
    }

    private V oldValue;

    @Override
    public V insert(K key, V value) {
        root = insertR(key, value, root);
        if (root != null)
            root.parent = null;
        return oldValue;
    }

    private Node<K, V> insertR(K key, V value, Node<K, V> p) {
        if (p == null) {
            p = new Node(key, value);
            oldValue = null;
        } else if (key.compareTo(p.key) < 0) {
            p.left = insertR(key, value, p.left);
            if (p.left != null)
                p.left.parent = p;
        } else if (key.compareTo(p.key) > 0) {
            p.right = insertR(key, value, p.right);
            if (p.right != null)
                p.right.parent = p;
        } else {
            oldValue = p.value;
            p.value = value;
        }
        p = balance(p);
        return p;

    }

    @Override
    public V search(K key) {
        return searchR(key, root);
    }

    private V searchR(K key, Node<K, V> p) {
        if (p == null)
            return null;
        else if (key.compareTo(p.key) < 0)
            return searchR(key, p.left);
        else if (key.compareTo(p.key) > 0)
            return searchR(key, p.right);
        else
            return p.value;
    }


    @Override
    public V remove(K key) {
        root = removeR(key, root);
        return oldValue;
    }

    private Node<K, V> removeR(K key, Node<K, V> p) {
        if (p == null)
            oldValue = null;
        else if (key.compareTo(p.key) < 0)
            p.left = removeR(key, p.left);
        else if (key.compareTo(p.key) > 0)
            p.right = removeR(key, p.right);
        else if (p.left == null || p.right == null) {
            oldValue = p.value;
            p = (p.left != null) ? p.left : p.right;
        } else {
            MinEntry<K, V> min = new MinEntry<K, V>();
            p.right = getRemMinR(p.right, min);
            oldValue = p.value;
            p.key = min.key;
            p.value = min.value;
        }
        p = balance(p);
        return p;
    }

    private Node<K, V> getRemMinR(Node<K, V> p, MinEntry<K, V> min) {
        assert p != null;
        if (p.left == null) {
            min.key = p.key;
            min.value = p.value;
            p = p.right;
        } else
            p.left = getRemMinR(p.left, min);
        p = balance(p);
        return p;
    }

    private static class MinEntry<K, V> {
        private K key;
        private V value;
    }

    public void prettyPrint() {
        prettyPrintR(root, 0);
    }

    private void prettyPrintR(Node<K, V> p, int d) {

        for (int i = 0; i < d; i++)
            System.out.print("  ");

        if (p == null) {
            System.out.println("#");
            return;
        }
        System.out.println(p.key.toString());
        prettyPrintR(p.left, d + 1);
        prettyPrintR(p.right, d + 1);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {

        return new Iterator<Dictionary.Entry<K, V>>() {

            private Node<K, V> currentNode = null;

            @Override
            public boolean hasNext() {
                if (this.currentNode == null)
                    this.currentNode = leftMostDescendant(root);

                else if (this.currentNode.right != null)
                    this.currentNode = leftMostDescendant(this.currentNode.right);
                else
                    this.currentNode = parentOfLeftMostAncestor(this.currentNode);

                return this.currentNode == null ? false : true;
            }

            @Override
            public Entry<K, V> next() {
                Entry<K, V> e = new Entry<>(currentNode.key, currentNode.value);
                return e;
            }
        };

    }
}
