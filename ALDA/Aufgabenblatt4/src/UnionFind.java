public class UnionFind {

    int[] p;
    int size;

    public UnionFind(int n) {
        p = new int[n];
        for (int i = 0; i < n; i++) {
            p[i] = -1;
        }
        size = n;
    }

    public int find(int e) {
        while (p[e] >= 0)
            e = p[e];
        return e;
    }

    public void union(int s1, int s2) {
        if (p[s1] >= 0 || p[s2] >= 0)
            return;
        if (s1 == s2)
            return;

        if (-p[s1] < -p[s2])
            p[s1] = s2;
        else {
            if (-p[s1] == -p[s2])
                p[s1]--;
            p[s2] = s1;
        }
        --size;
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {
        UnionFind u = new UnionFind(20);

        System.out.println("Anzahl der Partionierungen " + u.size);
        u.union(0, 1);
        System.out.println("Anzahl der Partionierungen " + u.size);
        u.union(0, 13);
        System.out.println("Anzahl der Partionierungen " + u.size);
        u.union(2, 3);
        System.out.println("Anzahl der Partionierungen " + u.size);
        u.union(2, 14);
        System.out.println("Anzahl der Partionierungen " + u.size);
        u.union(4, 5);
        System.out.println("Anzahl der Partionierungen " + u.size);
        u.union(4, 6);
        System.out.println("Anzahl der Partionierungen " + u.size);
        u.union(4, 10);
        System.out.println("Anzahl der Partionierungen " + u.size);
        u.union(5, 7);
        System.out.println("Anzahl der Partionierungen " + u.size);
        u.union(15, 8);
        System.out.println("Anzahl der Partionierungen " + u.size);
        u.union(15, 9);
        System.out.println("Anzahl der Partionierungen " + u.size);
        u.union(12, 11);
        System.out.println("Anzahl der Partionierungen " + u.size);
        System.out.println();

        System.out.println("8 is contained in " + u.find(8));

        System.out.println("1 is contained in " + u.find(1));

        System.out.println("3 is contained in " + u.find(3));

        System.out.println("5 is contained in " + u.find(5));

        System.out.println("11 is contained in " + u.find(11));

        System.out.println("6 is contained in " + u.find(6));
        System.out.println();

        u.union(2, 15);
        System.out.println("Merging 15 with 2");
        System.out.println("8 is contained in " + u.find(8));
        System.out.println("Anzahl der Partionierungen " + u.size);
        System.out.println();

        u.union(0, 4);
        System.out.println("Merging 4 with 0");
        System.out.println("5 is contained in " + u.find(5));
        System.out.println("Anzahl der Partionierungen " + u.size);
    }
}
