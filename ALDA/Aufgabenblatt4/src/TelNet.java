import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TelNet {

    int lbg;
    Map<TelKnoten, Integer> telMap;
    int size = 0;
    List<TelVerbindung> minTree = new LinkedList<>();

    public TelNet(int lbg) {
        telMap = new HashMap<>();
        this.lbg = lbg;
    }

    public boolean addTelKnoten(int x, int y) {
        TelKnoten tk = new TelKnoten(x, y);
        if (!telMap.containsKey(tk)) {
            telMap.put(tk, size++);
            return true;
        }
        else
            return false;
    }

    public boolean computeOptTelNet() {
        UnionFind forest = new UnionFind(size);
        PriorityQueue<TelVerbindung> edges = new PriorityQueue<>(size, Comparator.comparing(x -> x.c));

        fillPrioQueue(edges);

        while (forest.size() != 1 && ! edges.isEmpty()) {
            TelVerbindung tel = edges.poll();
            int t1 = forest.find(telMap.get(tel.anfang));
            int t2 = forest.find(telMap.get(tel.ende));
            if (t1 != t2) {
                forest.union(t1,t2);
                minTree.add(tel);
            }
        }
        if (edges.isEmpty() && forest.size() != 1)
            return false;
        else
            return true;
    }

    private void fillPrioQueue(PriorityQueue<TelVerbindung> edges) {
        for (var v : telMap.entrySet()) {
            for (var w : telMap.entrySet()) {
                if (v.equals(w))
                    continue;

                int cost = calcCost(v.getKey(), w.getKey());
                if (cost <= lbg) {
                    edges.add(new TelVerbindung(v.getKey(), w.getKey(), cost));
                }
            }
        }
    }

    private int calcCost(TelKnoten u, TelKnoten v) {
        return (Math.abs(u.x - v.x) + Math.abs(u.y - v.y));
    }

    public void generateRandomTelNet(int n, int xMax, int yMax) {

        int i = 0;

        while (i < n) {
            int px = (int)( Math.random() * xMax);
            int py = (int)( Math.random() * yMax);
            if (this.addTelKnoten(px, py))
                i++;
        }
    }

    public void drawOptTelNet(int xMax, int yMax) {
        StdDraw.setCanvasSize(512, 512);
        StdDraw.setXscale(0, xMax + 1);
        StdDraw.setYscale(0, yMax + 1);

        for (int i = 0; i < yMax; i++) {
            StdDraw.line(0.5, i + 0.5, yMax + 0.5, i + 0.5);
        }
        for (int i = 0; i < xMax; i++) {
            StdDraw.line(i + 0.5, 0.5, i + 0.5, xMax + 0.5);
        }
        StdDraw.line(0.5, yMax + 0.5, xMax + 0.5, yMax + 0.5);
        StdDraw.line(xMax + 0.5, 0.5, xMax + 0.5, yMax + 0.5);
        StdDraw.setPenColor(StdDraw.RED);

        for(var v : minTree) {
            double x = v.anfang.x;
            double y = v.ende.y;
            StdDraw.line(v.anfang.x, v.anfang.y, x, y);
            StdDraw.line(x, y, v.ende.x, v.ende.y);
            StdDraw.setPenColor(Color.BLUE);
            StdDraw.filledSquare(v.anfang.x, v.anfang.y, 0.5);
            StdDraw.filledSquare(v.ende.x, v.ende.y, 0.5);
            StdDraw.setPenColor(Color.RED);
        }
        StdDraw.show(0);
    }

    public List<TelVerbindung> getOptTelNet() {
        return minTree;
    }

    public int getOptTelNetKosten() {
        int cost = 0;
        for(var v : minTree) {
            cost += v.c;
        }
        return cost;
    }

    public int size() {
        return this.size;
    }

    public static void main(String[] args) {
        TelNet tn = new TelNet(5);
        tn.addTelKnoten(1, 1);
        tn.addTelKnoten(3, 1);
        tn.addTelKnoten(4, 2);
        tn.addTelKnoten(3, 4);
        tn.addTelKnoten(7, 5);
        tn.addTelKnoten(2, 6);
        tn.addTelKnoten(4, 7);

        System.out.println(tn.computeOptTelNet());

        tn.drawOptTelNet(7, 7);

        /*int max = 1000;
        TelNet tn2 = new TelNet(100);

        tn2.generateRandomTelNet(max, max, max);

        System.out.println(tn2.computeOptTelNet());

        tn2.drawOptTelNet(max, max);*/
    }
}
