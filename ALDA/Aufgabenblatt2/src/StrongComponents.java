// O. Bittel;
// 05-09-2018


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Klasse für Bestimmung aller strengen Komponenten.
 * Kosaraju-Sharir Algorithmus.
 * @author Oliver Bittel
 * @since 22.02.2017
 * @param <V> Knotentyp.
 */
public class StrongComponents<V> {
	// comp speichert fuer jede Komponente die zughörigen Knoten.
    // Die Komponenten sind numeriert: 0, 1, 2, ...
    // Fuer Beispielgraph in Aufgabenblatt 2, Abb3:
    // Component 0: 5, 6, 7,
    // Component 1: 8,
    // Component 2: 1, 2, 3,
    // Component 3: 4,

	private final Map<Integer,Set<V>> comp = new TreeMap<>();
	private Set<V> besucht = new TreeSet<>();
	private int counter_teilbaum = 0;
	
	/**
	 * Ermittelt alle strengen Komponenten mit
	 * dem Kosaraju-Sharir Algorithmus.
	 * @param g gerichteter Graph.
	 */
	public StrongComponents(DirectedGraph<V> g) {
		ksa(g);
	}


	/**
	 *
	 * @return Anzahl der strengen Komponeneten.
	 */
	public int numberOfComp() {
		return comp.size();
	}

	private List<V> reversePostOrder(DirectedGraph<V> g) {
		DepthFirstOrder<V> dfo = new DepthFirstOrder<>(g);
		List<V> postOrderList = new LinkedList<>(dfo.postOrder());
		Collections.reverse(postOrderList);
		return postOrderList;
	}

	public void ksa(DirectedGraph<V> g) {
		List<V> pi = reversePostOrder(g);
		DirectedGraph<V> gi = g.invert();

		for (V v : pi) {
			if (!besucht.contains(v)) {
				comp.put(counter_teilbaum, new TreeSet<>());
				comp.get(counter_teilbaum).add(v);
				besucht.add(v);
				ksaR(v, gi, counter_teilbaum);
				counter_teilbaum++;
			}
		}
	}

	public void ksaR(V v, DirectedGraph<V> g, int counter_teilbaum) {
		besucht.add(v);

		for (var p : g.getSuccessorVertexSet(v)) {
			if (!besucht.contains(p)) {
				comp.get(counter_teilbaum).add(p);
				besucht.add(p);
				ksaR(p, g, counter_teilbaum);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (var v : comp.entrySet()) {
			sb.append("Component ").append(v.getKey()).append(" : ").append(v.getValue()).append(", ");
			sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * Liest einen gerichteten Graphen von einer Datei ein. 
	 * @param fn Dateiname.
	 * @return gerichteter Graph.
	 * @throws FileNotFoundException
	 */
	public static DirectedGraph<Integer> readDirectedGraph(File fn) throws FileNotFoundException {
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		Scanner sc = new Scanner(fn);
		sc.nextInt();
		sc.nextInt();
		while (sc.hasNextInt()) {
			int v = sc.nextInt();
			int w = sc.nextInt();
			g.addEdge(v, w);
		}
		return g;
	}
	
	private static void test1() {
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		g.addEdge(1,2);
		g.addEdge(1,3);
		g.addEdge(2,1);
		g.addEdge(2,3);
		g.addEdge(3,1);
		
		g.addEdge(1,4);
		g.addEdge(5,4);
		
		g.addEdge(5,7);
		g.addEdge(6,5);
		g.addEdge(7,6);
		
		g.addEdge(7,8);
		g.addEdge(8,2);
		
		StrongComponents<Integer> sc = new StrongComponents<>(g);
		
		System.out.println(sc.numberOfComp());  // 4
		
		System.out.println(sc);
			// Component 0: 5, 6, 7, 
        	// Component 1: 8, 
            // Component 2: 1, 2, 3, 
            // Component 3: 4,
	}
	
	private static void test2() throws FileNotFoundException {
		DirectedGraph<Integer> g = readDirectedGraph(new File("mediumDG.txt"));
		System.out.println(g.getNumberOfVertexes());
		System.out.println(g.getNumberOfEdges());
		System.out.println(g);
		
		System.out.println("");
		
		StrongComponents<Integer> sc = new StrongComponents<>(g);
		System.out.println(sc.numberOfComp());  // 10
		System.out.println(sc);
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		test1();
		test2();
	}
}
