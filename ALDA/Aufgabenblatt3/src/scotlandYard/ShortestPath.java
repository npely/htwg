package scotlandYard;// O. Bittel;
// 28.02.2019

import SYSimulation.src.sim.SYSimulation;
import scotlandYard.DirectedGraph;

import java.util.*;
// ...

/**
 * Kürzeste Wege in Graphen mit A*- und Dijkstra-Verfahren.
 * @author Oliver Bittel
 * @since 27.01.2015
 * @param <V> Knotentyp.
 */
public class ShortestPath<V> {
	
	SYSimulation sim = null;
	
	Map<V,Double> dist; // Distanz für jeden Knoten
	Map<V,V> pred; // Vorgänger für jeden Knoten
	DirectedGraph<V> graph;
	Heuristic<V> h;
	double inf = Double.MAX_VALUE;
	V start;
	V ziel;
	LinkedList <V> shortP = new LinkedList<>();

	/**
	 * Konstruiert ein Objekt, das im Graph g k&uuml;rzeste Wege 
	 * nach dem A*-Verfahren berechnen kann.
	 * Die Heuristik h schätzt die Kosten zwischen zwei Knoten ab.
	 * Wird h = null gewählt, dann ist das Verfahren identisch 
	 * mit dem Dijkstra-Verfahren.
	 * @param g Gerichteter Graph
	 * @param h Heuristik. Falls h == null, werden kürzeste Wege nach
	 * dem Dijkstra-Verfahren gesucht.
	 */
	public ShortestPath(DirectedGraph<V> g, Heuristic<V> h) {
		dist = new TreeMap<>();
		pred = new TreeMap<>();
		graph = g;
		this.h = h;
	}

	/**
	 * Diese Methode sollte nur verwendet werden, 
	 * wenn kürzeste Wege in Scotland-Yard-Plan gesucht werden.
	 * Es ist dann ein Objekt für die Scotland-Yard-Simulation zu übergeben.
	 * <p>
	 * Ein typische Aufruf für ein SYSimulation-Objekt sim sieht wie folgt aus:
	 * <p><blockquote><pre>
	 *    if (sim != null)
	 *       sim.visitStation((Integer) v, Color.blue);
	 * </pre></blockquote>
	 * @param sim SYSimulation-Objekt.
	 */
	public void setSimulator(SYSimulation sim) {
		this.sim = sim;
	}

	/**
	 * Sucht den kürzesten Weg von Starknoten s zum Zielknoten g.
	 * <p>
	 * Falls die Simulation mit setSimulator(sim) aktiviert wurde, wird der Knoten,
	 * der als nächstes aus der Kandidatenliste besucht wird, animiert.
	 * @param s Startknoten
	 * @param g Zielknoten
	 */
	public void searchShortestPath(V s, V g) {
		shortestPath(s, g, graph, dist, pred);
	}

	private boolean shortestPath(V s, V z, DirectedGraph<V> g, Map<V, Double> dist, Map<V, V> pred) {
		shortP.clear();
		LinkedList<V> kl = new LinkedList<>();

		start = s;
		ziel = z;

		for (V v: g.getVertexSet()) {
			dist.put(v, inf);
			pred.put(v, null);
		}

		dist.put(s, 0.0);
		kl.add(s);

		while (!kl.isEmpty()) {

			V minVertex = s;
			double minimalDist = inf;

			for (var v : kl) {
				if (h == null) {
					if (dist.get(v) < minimalDist) {
						minimalDist = dist.get(v);
						minVertex = v;
					}
				} else {
					if ((dist.get(v) + h.estimatedCost(v, z)) < minimalDist) {
						minimalDist = dist.get(v) + h.estimatedCost(v, z);
						minVertex = v;
					}
					if (v.equals(z)) {
						shortP.add(v);
						return true;
					}
				}
			}

			kl.remove(minVertex);
			V v = minVertex;

			System.out.println("Besuchter Knoten " + v + " mit d: " + dist.get(v));

			shortP.add(minVertex);

			for (V w : g.getSuccessorVertexSet(v)) {
				if (dist.get(w) == inf)
					kl.add(w);
				if (dist.get(v) + g.getWeight(v, w) < dist.get(w)) {
					pred.put(w, v);
					dist.put(w, dist.get(v) + g.getWeight(v, w));
				}
			}
		}
		return false;
	}

	/**
	 * Liefert einen kürzesten Weg von Startknoten s nach Zielknoten g.
	 * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
	 * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
	 * @return kürzester Weg als Liste von Knoten.
	 */
	public List<V> getShortestPath() {
		LinkedList<V> shortestPath = new LinkedList<>();
		V t = pred.get(ziel);
		shortestPath.add(ziel);
		while (t != start) {
			shortestPath.add(t);
			t = pred.get(t);
		}
		shortestPath.add(start);
		Collections.reverse(shortestPath);
		return shortestPath;
	}

	/**
	 * Liefert die Länge eines kürzesten Weges von Startknoten s nach Zielknoten g zurück.
	 * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
	 * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
	 * @return Länge eines kürzesten Weges.
	 */
	public double getDistance() {
		return dist.get(ziel);
	}

}
